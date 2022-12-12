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
    wallet    FLOAT,
    password  VARCHAR(255)

);

CREATE TABLE transaction
(
    transaction_id     INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    sender_user_id     INTEGER NOT NULL REFERENCES user (user_Id),
    receiving_user_id  INTEGER NOT NULL REFERENCES user (user_Id),
    transaction_date   DATE    NOT NULL,
    transaction_comment VARCHAR(255) NOT NULL,
    transaction_amount FLOAT NOT NULL
);

CREATE TABLE user_user
(
    main_user_id    INTEGER NOT NULL,
    contact_user_id INTEGER NOT NULL,
    PRIMARY KEY (main_user_id, contact_user_id),
    FOREIGN KEY (main_user_id) REFERENCES user (user_Id),
    FOREIGN KEY (contact_user_id) REFERENCES user (user_Id)
);

CREATE TABLE role
(
    role_id   INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    authorisation VARCHAR(255)

);

CREATE TABLE user_role
(
    user_id INTEGER NOT NULL,
    role_id INTEGER NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES user (user_id),
    FOREIGN KEY (role_id) REFERENCES role (role_id)
);


INSERT INTO user (firstName, lastName, birthdate, email, address, wallet, password)
    VALUE ('Bertrand', 'NOEL', '1992-03-10', 'bertrandnoel@gmail.com', 'Toulouse', 50,
           '$2y$10$9Qw2gl8TBJXQtMLIjkG1/enQbDsPrMS0EGzaEvtuYRPCcMiFq8cKG'),
    ('Paul', 'Dupon', '2000/01-01', 'pauldupon@gmail.com', 'Lille', 100,
     '$2y$10$syavJCs0C7s.EVlCnBjWxuRwxBTC4Yj8FFUDnfQekcOoMgvMcdbnS'),
    ('Louis', 'Dupuis', '1980-06-30', 'louisdupuis@yopmail.com', 'Paris', 250,
     '$2y$10$syavJCs0C7s.EVlCnBjWxuRwxBTC4Yj8FFUDnfQekcOoMgvMcdbnS')
;

INSERT INTO transaction(sender_user_id, receiving_user_id, transaction_date,transaction_comment, transaction_amount)
    VALUE (1, 2, '2022-01-01','Ciné', 25),
    (1, 3, '2022-02-05','Restaurant', 50),
    (3, 1, '2022-12-04','Covoit', 75)
;

INSERT INTO user_user (main_user_id, contact_user_id)
    VALUE (1, 2),
    (1, 3),
    (2, 1),
    (3, 1);

INSERT INTO role (authorisation) VALUE ('USER'),('ADMIN');

INSERT INTO user_role (user_id, role_id) VALUE (1, 1), (1, 2), (2, 1), (3, 1);