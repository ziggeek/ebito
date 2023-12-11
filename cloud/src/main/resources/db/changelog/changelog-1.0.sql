--liquibase formatted sql

--changeset pro100:1
create table if not exists documents
(
    id        bigserial primary key not null,
    client_id varchar(255)          not null,
    name      varchar(255)          not null,
    type      varchar(255)          not null
);