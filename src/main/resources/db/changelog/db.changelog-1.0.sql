--liquibase formatted sql

--changeset golyura:1
CREATE TABLE IF NOT EXISTS product
(
    id       BIGSERIAL PRIMARY KEY,
    name     VARCHAR(64) NOT NULL UNIQUE,
    price    INT,
    quantity INT
);
--rollback DROP TABLE product;

--changeset golyura:2
CREATE TABLE IF NOT EXISTS product_locales
(
    product_id  BIGINT REFERENCES product (id),
    lang        VARCHAR(2),
    description VARCHAR(255) NOT NULL,
    PRIMARY KEY (product_id, lang)
);

--changeset golyura:3
CREATE TABLE IF NOT EXISTS customer
(
    id        BIGSERIAL PRIMARY KEY,
    email     VARCHAR(64) NOT NULL UNIQUE,
    firstname VARCHAR(64),
    role VARCHAR(32),
    birth_date DATE
--     lastname  VARCHAR(64),
--     address   VARCHAR(64),
--     password  VARCHAR(32),
--     telephone VARCHAR(32)
);

--changeset golyura:4
CREATE TABLE IF NOT EXISTS purchase
(
    id                    BIGSERIAL PRIMARY KEY,
    purchase_date         DATE,
    purchase_closing_date DATE,
    customer_id           BIGINT REFERENCES customer (id),
    status                VARCHAR(32)
);

--changeset golyura:5
CREATE TABLE IF NOT EXISTS purchase_item
(
    id            BIGSERIAL PRIMARY KEY,
    purchase_id   BIGINT NOT NULL REFERENCES purchase (id),
    product_id    BIGINT NOT NULL REFERENCES product (id),
    product_count INT,
    UNIQUE (purchase_id, product_id)
);

--changeset golyura:6
CREATE TABLE IF NOT EXISTS chat
(
    id SERIAL PRIMARY KEY ,
    name VARCHAR(64) NOT NULL UNIQUE
);

--changeset golyura:7
CREATE TABLE IF NOT EXISTS customer_chat
(
    id SERIAL PRIMARY KEY ,
    customer_id BIGINT NOT NULL REFERENCES customer (id),
    chat_id INT NOT NULL REFERENCES chat (id),
    UNIQUE (customer_id, chat_id)
);