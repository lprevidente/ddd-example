create table if not exists users
(
    id         uuid not null primary key,
    email      character varying(255) unique,
    first_name character varying(255),
    last_name  character varying(255),
    password   character varying(255)
);

create table if not exists teams
(
    id         uuid not null primary key,
    created_at timestamp,
    name       varchar(255)
);

create table if not exists team_members
(
    user_id   uuid not null,
    team_id   uuid not null,
    joined_at timestamp,
    constraint pk_team_members primary key (user_id, team_id),
    constraint fk_user_team foreign key (team_id) references teams (id) on delete cascade,
    constraint fk_team_user foreign key (user_id) references users (id) on delete cascade
);

create table if not exists offices
(
    id      uuid not null primary key,
    name    varchar(255),
    address varchar(255),
    city    varchar(255),
    country varchar(255)
);

create table if not exists office_assignments
(
    id            uuid      not null primary key,
    office_id     uuid      not null,
    user_id       uuid      not null,
    assigned_at   timestamp not null,
    unassigned_at timestamp,
    constraint fk_office_assignment_office foreign key (office_id) references offices (id) on delete cascade
);

create table if not exists event_publication
(
    completion_date  timestamp(6) with time zone,
    publication_date timestamp(6) with time zone,
    id               uuid not null,
    event_type       varchar(255),
    listener_id      varchar(255),
    serialized_event varchar(255),
    primary key (id)
);