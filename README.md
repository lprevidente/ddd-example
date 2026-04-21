# DDD Example Application

[![Run Tests](https://github.com/lprevidente/ddd-example/actions/workflows/run-tests.yml/badge.svg)](https://github.com/lprevidente/ddd-example/actions/workflows/run-tests.yml)

A Spring Boot application exploring Domain-Driven Design (DDD) and CQRS with a toy team-management domain. The point is
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

```
com.lprevidente.ddd_example/
├── Application.java
├── config/                          # SecurityConfig + GlobalExceptionHandler (RFC 7807)
├── common/
│   ├── exception/                   # DomainException base (named interface)
│   └── identifier/                  # Identifier<UUID> + Jackson/Spring converters (named interface)
├── user/                            # Bounded context: User
│   ├── api/                         # Named interface exposed to other modules
│   │   ├── UserApi.java
│   │   └── UserIdDto.java
│   ├── application/
│   │   ├── command/                 # AddUser, UpdateUser, DeleteUser
│   │   ├── handler/                 # One handler per command
│   │   ├── dto/                     # Spring Data projection interfaces (read models)
│   │   └── UserQueryService.java
│   ├── domain/
│   │   ├── User.java                # Aggregate root
│   │   ├── UserId.java              # Value object
│   │   ├── Email.java               # Value object
│   │   ├── Password.java            # Value object
│   │   ├── Users.java               # Repository interface
│   │   └── exception/
│   └── infrastructure/rest/
│       └── UserController.java
└── team/                            # Bounded context: Team
    ├── application/
    │   ├── command/                 # CreateTeam, DeleteTeam, AddUserToTeam, RemoveUserFromTeam
    │   ├── handler/                 # One handler per command
    │   ├── dto/                     # Spring Data projection interfaces (read models)
    │   ├── TeamQueryService.java
    │   └── TeamMemberQueryService.java
    ├── domain/
    │   ├── Team.java                # Aggregate root
    │   ├── TeamId.java              # Value object
    │   ├── TeamMember.java          # Aggregate root
    │   ├── TeamMemberId.java        # Value object (composite: TeamId + UserId)
    │   ├── UserId.java              # Local copy — intentionally separate from user.domain.UserId
    │   ├── Teams.java               # Repository interface
    │   ├── TeamMembers.java         # Repository interface
    │   ├── event/
    │   │   └── TeamCreated.java     # Domain event
    │   └── exception/
    └── infrastructure/rest/
        ├── TeamController.java
        └── TeamMemberController.java
```

### Key structural rules

- **`domain/`** is persistence-ignorant. No `@Entity`, `@Embeddable`, or `@Id` are hand-written — `jmolecules-jpa`
  translates `@AggregateRoot` → `@Entity`, `@ValueObject` → `@Embeddable`, `@Identity` → `@EmbeddedId`/`@Id` at
  compile time via ByteBuddy.
- **`application/`** owns orchestration only: commands (records), handlers (one per command), projection DTOs, and query
  services. No domain logic lives here.
- **`infrastructure/`** contains only framework adapters (`@RestController`). Controllers are package-private and inject
  handlers directly — no mediator or dispatcher.
- **Cross-module access** goes exclusively through `user/api/` (the Modulith named interface). The `team` context never
  touches `user.domain`.

---

## CQRS Write Side

Commands are plain records in `application/command/` annotated with `@Command`. Each command has exactly one handler in
`application/handler/` annotated with `@Service`:

```
Controller  →  Handler.handle(command)  →  Domain aggregate  →  Repository
```

No mediator. Controllers inject the specific handler they need. The handler's `handle()` method is annotated
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

Reads never go through a command handler. Each context has a `*QueryService` that returns **Spring Data projection
interfaces** (`@QueryModel`) — no aggregate leaks out of the repository:

```
Controller  →  QueryService  →  Repository.findXxx(Class<T>)  →  Projection DTO
```

Cross-context read enrichment: `TeamMemberQueryService` loads memberships, then calls `UserApi.findAllById(ids,
MemberDto.class)` to hydrate user data from the user module.

---

## Domain Conventions

### Aggregates

- Annotated `@AggregateRoot` (jMolecules); `@Entity` added by transformer
- Protected no-arg constructor for Hibernate
- **Invariants are enforced in the constructor** — repositories and cross-module APIs are passed in so the aggregate can
  validate itself at creation time:
    - `new User(firstName, lastName, password, email, users)` — checks email uniqueness
    - `new TeamMember(teamId, userId, teams, users, teamMembers)` — checks team/user existence + no duplicate membership

### Value Objects

- Records implementing `Identifier<UUID>` for IDs (`UserId`, `TeamId`, `TeamMemberId`)
- Annotated `@ValueObject` (jMolecules); `@Embeddable` added by transformer
- Compact constructors enforce invariants (`Email` validates format, `Password` enforces strength rules)

### Domain Events

- `Team` extends `AbstractAggregateRoot<Team>` and registers `TeamCreated` on construction
- Published automatically by Spring Data on `save()`

### Modulith boundaries

- `user/api/` is declared as a named interface — the only package other modules may import from `user`
- `common/identifier/` is a named interface shared across all modules
- `VeryModulithTest` verifies boundaries at test time via `ApplicationModules.of(Application.class).verify()`
- `DddRulesTest` runs `JMoleculesDddRules.all()` (ArchUnit) to verify aggregate/entity/value-object rules

---

## JPA Without JPA Annotations

The domain model is kept free of persistence annotations. The `byte-buddy-maven-plugin` runs `JMoleculesPlugin` at
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

Source classes only carry what the transformer can't derive: `@Table`, `@AttributeOverride`, `@Column`.

To inspect the result: `javap -v -p target/classes/.../User.class`

---

## API Endpoints

- **Users** `POST /api/v1/users` — register; `GET /api/v1/users[/{id}]` — list/get; `PUT /{id}` — update;
  `DELETE /{id}` — delete
- **Teams** `POST /api/v1/teams` — create; `GET /api/v1/teams[/{id}]` — list/get; `DELETE /{id}` — delete
- **Members** `POST /api/teams/{teamId}/members` — add user; `GET /members` — list;
  `DELETE /members/{userId}` — remove

---

## Running

**Prerequisites:** Java 25, Maven, Docker

```bash
./mvnw spring-boot:run          # starts app + Postgres via Docker Compose
./mvnw test                     # run all tests (H2)
./mvnw test -Dtest=TeamIntegrationTest
```

---

## Learning & Contributions

If you have suggestions or see opportunities for better applying DDD concepts, feel free to open an issue or a pull
request.
