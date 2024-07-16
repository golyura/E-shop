--liquibase formatted sql

--changeset golyura:1
CREATE TABLE IF NOT EXISTS revision
(
    id        SERIAL PRIMARY KEY,
    timestamp BIGINT NOT NULL
);

--changeset golyura:2
CREATE TABLE IF NOT EXISTS customer_aud
(
    id         BIGINT,
    rev        INT REFERENCES revision (id),
    revtype    SMALLINT,
    birth_date DATE,
    email      VARCHAR(64) NOT NULL UNIQUE,
    firstname  VARCHAR(64),
    role       VARCHAR(32)
);
