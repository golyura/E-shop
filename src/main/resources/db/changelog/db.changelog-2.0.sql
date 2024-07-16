--liquibase formatted sql

--changeset golyura:1
ALTER TABLE customer
ADD COLUMN created_at TIMESTAMP;

ALTER TABLE customer
ADD COLUMN modified_at TIMESTAMP;

ALTER TABLE customer
ADD COLUMN created_by VARCHAR(32);

ALTER TABLE customer
ADD COLUMN modified_by VARCHAR(32);