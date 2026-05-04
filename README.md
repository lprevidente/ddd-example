# DDD Example Application

[![Run Tests](https://github.com/lprevidente/ddd-example/actions/workflows/run-tests.yml/badge.svg)](https://github.com/lprevidente/ddd-example/actions/workflows/run-tests.yml)

A Spring Boot application exploring Domain-Driven Design (DDD) and CQRS with a toy team-management
domain. The point is
the **shape of the code**, not the feature set.

> **Note**: This is a learning project. Feedback and suggestions are welcome.

## Stack

| Layer              | Technology                                                          |
|--------------------|---------------------------------------------------------------------|
| Language           | Java 25                                                             |
| Framework          | Spring Boot 4.0.5                                                   |
| Persistence        | Spring Data JPA, Hibernate 7, PostgreSQL                            |
| Modularity         | Spring Modulith 2.0.5                                               |
| DDD semantics      | jMolecules (DDD + CQRS + events annotations, BOM `2025.0.2`)        |
| JPA translation    | `jmolecules-jpa` + `jmolecules-spring` via ByteBuddy (compile-time) |
| Architecture tests | `jmolecules-archunit` + ArchUnit                                    |
| Utilities          | Lombok, JSpecify, Jackson 3                                         |
| Dev DB             | PostgreSQL via Docker Compose                                       |
| Test DB            | H2 in-memory                                                        |

---

## Package Structure

The codebase is organized around **bounded contexts**. Each context is a top-level package and
follows the same internal
layout:

```
com.lprevidente.ddd_example/
├── Application.java
├── config/                          # Cross-cutting framework config (security, error handling)
├── common/                          # Shared kernel (base types, identifier abstraction)
└── <bounded-context>/
    ├── api/                         # Named interface exposed to other modules (optional)
    ├── application/
    │   ├── command/                 # Command records (write side)
    │   ├── handler/                 # Command handlers (one per command)
    │   ├── query/                   # Query services + read repositories
    │   └── projection/              # *View projection interfaces
    ├── domain/                      # Aggregates, value objects, repositories, events, exceptions
    └── infrastructure/              # Framework adapters (REST controllers, etc.)
```

### Key structural rules

- **`domain/`** is persistence-ignorant. No `@Entity`, `@Embeddable`, or `@Id` are hand-written —
  `jmolecules-jpa`
  translates `@AggregateRoot` → `@Entity`, `@ValueObject` → `@Embeddable`, `@Identity` →
  `@EmbeddedId`/`@Id` at
  compile time via ByteBuddy.
- **`application/`** owns orchestration only, split by CQRS concern: `command/` + `handler/` for the
  write side,
  `query/` (query services + read repositories) + `projection/` (`*View` interfaces) for the read
  side. No domain logic
  lives here.
- **`infrastructure/`** contains only framework adapters (`@RestController`). Controllers are
  package-private and inject
  handlers directly — no mediator or dispatcher.
- **Cross-module access** goes exclusively through a context's `api/` package (a Modulith named
  interface). Other
  contexts never reach into another's `domain/`.

---

## CQRS Write Side

Commands are plain records in `application/command/` annotated with `@Command`. Each command has
exactly one handler in
`application/handler/` annotated with `@Service`:

```
Controller  →  Handler.handle(command)  →  Domain aggregate  →  Repository
```

No mediator. Controllers inject the specific handler they need. The handler's `handle()` method is
annotated
`@CommandHandler` (jMolecules).

**Example flow — create a team:**

```
POST /api/v1/teams
  └─ TeamController.create(CreateTeam)
       └─ CreateTeamHandler.handle(CreateTeam)
            └─ new Team(name)           // domain constructor, registers TeamCreated event
                 └─ teams.save(team)    // Spring Data JPA
```

## CQRS Read Side

Reads never go through a command handler. The read side is fully separated from the write side:

- **Domain repositories** (`domain/`) have no projection methods — write operations only
- **Read repositories** (`application/query/`) extend Spring Data's `Repository<T,ID>` marker and
  expose only
  projection queries
- **Query services** (`application/query/`) return `*View` projection interfaces — no aggregates
  leak out

```
Controller  →  QueryService  →  ReadRepository.findXxx(Class<T>)  →  *View projection
```

Cross-context read enrichment: `TeamMemberQueryService` loads memberships, then calls `UserApi.findAllById(ids,
MemberView.class)` to hydrate user data from the user module.

---

## Domain Conventions

### Aggregates

- Annotated `@AggregateRoot` (jMolecules); `@Entity` added by transformer
- Protected no-arg constructor for Hibernate
- **Invariants are enforced in the constructor** — repositories and cross-module APIs are passed in
  so the aggregate can
  validate itself at creation time (e.g. checking uniqueness of a natural key, or verifying that a
  referenced entity in
  another context actually exists before establishing the relationship).

### Value Objects

- Records implementing `Identifier<UUID>` for typed IDs
- Annotated `@ValueObject` (jMolecules); `@Embeddable` added by transformer
- Compact constructors enforce invariants (e.g. format validation, strength or range rules)

### Domain Events

- Aggregates extend `AbstractAggregateRoot<T>` and register events from their constructor or domain
  methods
- Published automatically by Spring Data on `save()`

### Modulith boundaries

- `user/api/` is declared as a named interface — the only package other modules may import from
  `user`
- `common/identifier/` is a named interface shared across all modules
- `VeryModulithTest` verifies boundaries at test time via
  `ApplicationModules.of(Application.class).verify()`
- `DddRulesTest` runs `JMoleculesDddRules.all()` (ArchUnit) to verify aggregate/entity/value-object
  rules

---

## JPA Without JPA Annotations

The domain model is kept free of persistence annotations. The `byte-buddy-maven-plugin` runs
`JMoleculesPlugin` at
`process-classes` and rewrites bytecode:

| Source annotation                     | Added by transformer                  |
|---------------------------------------|---------------------------------------|
| `@AggregateRoot`                      | `@Entity`                             |
| `@ValueObject`                        | `@Embeddable`                         |
| `@Identity` on a value-object field   | `@EmbeddedId`                         |
| `@Identity` on a primitive/UUID field | `@Id`                                 |
| Non-null aggregate fields             | `@PostLoad`/`@PrePersist` null checks |
| `@Repository` (jMolecules)            | `@Repository` (Spring)                |
| `@Service` (jMolecules)               | `@Service` (Spring)                   |

Source classes only carry what the transformer can't derive: `@Table`, `@AttributeOverride`,
`@Column`.

To inspect the result: `javap -v -p target/classes/.../User.class`

---

## Running

**Prerequisites:** Java 25, Maven, Docker

```bash
./mvnw spring-boot:run          # starts app + Postgres via Docker Compose
./mvnw test                     # run all tests (H2)
```

---

## Learning & Contributions

If you have suggestions or see opportunities for better applying DDD concepts, feel free to open an
issue or a pull
request.
