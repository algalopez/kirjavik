CREATE DATABASE IF NOT EXISTS backoffice;

USE backoffice;

CREATE TABLE IF NOT EXISTS book (
    id CHAR(36) PRIMARY KEY,
    isbn VARCHAR(20) NOT NULL,
    title VARCHAR(255) NOT NULL,
    authors VARCHAR(255) NOT NULL,
    page_count INT,
    year INT
);

