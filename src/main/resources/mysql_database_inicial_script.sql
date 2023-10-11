DROP SCHEMA IF EXISTS password_meter;

CREATE SCHEMA password_meter;

use password_meter;

DROP TABLE IF EXISTS Users;

CREATE TABLE Users (
    id int NOT NULL AUTO_INCREMENT,
    user_name varchar(255) NOT NULL,
    user_password varchar(255),
    strength int,
    status varchar(20),
    parent_user_id int,
    PRIMARY KEY (id),
    FOREIGN KEY (parent_user_id) REFERENCES Users(id)
);

SELECT * FROM Users;