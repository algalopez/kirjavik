CREATE DATABASE IF NOT EXISTS havn;

USE havn;

CREATE TABLE IF NOT EXISTS `book_review` (
    `id` INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    `book_id` CHAR(36) NOT NULL,
    `user_id` CHAR(36) NOT NULL,
    `rating` DECIMAL(3,1),
    `comment` TEXT,
    CONSTRAINT book_id_user_id UNIQUE (`book_id`, `user_id`)
);

CREATE TABLE IF NOT EXISTS `book_projection` (
    `id` CHAR(36) PRIMARY KEY,
    `isbn` VARCHAR(20) NOT NULL,
    `title` VARCHAR(255) NOT NULL,
    `author` VARCHAR(255) NOT NULL,
    `page_count` INT,
    `year` INT
);

CREATE TABLE IF NOT EXISTS `user_projection` (
    `id` CHAR(36) PRIMARY KEY,
    `name` VARCHAR(255) NOT NULL,
    `email` VARCHAR(255) NOT NULL UNIQUE
);
