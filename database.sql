/* Setting up PAYMYBUDDY DB */

DROP DATABASE IF EXISTS paymybuddy;

CREATE DATABASE paymybuddy;
USE paymybuddy;

CREATE TABLE user
(
    user_id   INTEGER      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    firstname VARCHAR(255) NOT NULL,
    lastname  VARCHAR(255) NOT NULL,
    birthdate DATE         NOT NULL,
    email     VARCHAR(255) UNIQUE,
    address   VARCHAR(255),
    wallet    INTEGER,
    password  VARCHAR(255)
);

CREATE TABLE transaction
(
    transaction_id     INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    sender_user_id     INTEGER NOT NULL REFERENCES user (user_Id),
    receiving_user_id  INTEGER NOT NULL REFERENCES user (user_Id),
    transaction_date   DATE    NOT NULL,
    transaction_amount INTEGER NOT NULL
);

CREATE TABLE user_user
(
    main_user_id    INTEGER NOT NULL,
    contact_user_id INTEGER NOT NULL,
    PRIMARY KEY (main_user_id, contact_user_id),
    FOREIGN KEY (main_user_id) REFERENCES user (user_Id),
    FOREIGN KEY (contact_user_id) REFERENCES user (user_Id)
);


INSERT INTO user (firstName, lastName, birthdate, email, address, wallet, password)
    VALUE ('Bertrand', 'NOEL', '1992-03-10', 'bertrandnoel@gmail.com', 'Toulouse', 50, 'password'),
    ('Paul', 'Dupon', '2000/01-01', 'pauldupon@gmail.com', 'Lille', 100, 'AZERTY'),
    ('Louis', 'Dupuis', '1980-06-30', 'louisdupuis@yopmail.com', 'Paris', 250, '1234')
;

INSERT INTO transaction(sender_user_id, receiving_user_id, transaction_date, transaction_amount)
    VALUE (1, 2, '2022-01-01', 25),
    (1, 3, '2022-02-05', 50),
    (3, 1, '2022-12-04', 75)
;

INSERT INTO user_user (main_user_id, contact_user_id)
    VALUE (1, 2),
    (1, 3),
    (2, 1),
    (3, 1);


