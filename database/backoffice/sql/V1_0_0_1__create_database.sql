CREATE DATABASE IF NOT EXISTS backoffice;

USE backoffice;

CREATE TABLE IF NOT EXISTS `book` (
    `id` CHAR(36) PRIMARY KEY,
    `isbn` VARCHAR(20) NOT NULL,
    `title` VARCHAR(255) NOT NULL,
    `author` VARCHAR(255) NOT NULL,
    `page_count` INT,
    `year` INT
);

CREATE TABLE IF NOT EXISTS `user` (
    `id` CHAR(36) PRIMARY KEY,
    `name` VARCHAR(255) NOT NULL,
    `email` VARCHAR(255) NOT NULL UNIQUE
);
