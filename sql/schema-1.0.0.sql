DROP SCHEMA IF EXISTS `survey-app-db`;
CREATE SCHEMA `survey-app-db` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

CREATE TABLE `survey-app-db`.`users` (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nickname VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    salt VARCHAR(255) NOT NULL,
    
    PRIMARY KEY (id)
) engine=InnoDB;

CREATE TABLE `survey-app-db`.`surveys` (
    id BIGINT NOT NULL AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    total_votes INT UNSIGNED DEFAULT 0,
    
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
) engine=InnoDB;

CREATE TABLE `survey-app-db`.`options` (
    id BIGINT NOT NULL AUTO_INCREMENT,
    survey_id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    total_votes INT UNSIGNED DEFAULT 0,
    
    PRIMARY KEY (id),
    FOREIGN KEY (survey_id) REFERENCES surveys(id) ON DELETE CASCADE
) engine=InnoDB;

CREATE TABLE `survey-app-db`.`options_users` (
    user_id BIGINT NOT NULL,
    option_id BIGINT NOT NULL,

    PRIMARY KEY (user_id, option_id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (option_id) REFERENCES options(id) ON DELETE CASCADE
) engine=InnoDB;