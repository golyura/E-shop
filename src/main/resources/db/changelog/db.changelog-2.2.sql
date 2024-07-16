--liquibase formatted sql

-- ALTER TABLE customer_aud
-- DROP CONSTRAINT customer_aud users_aud_username_key;

--changeset golyura:1
ALTER TABLE customer_aud
ALTER COLUMN email DROP NOT NULL;