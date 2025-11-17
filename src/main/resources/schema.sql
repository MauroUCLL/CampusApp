-- Drop tables if they exist to start fresh
DROP TABLE IF EXISTS lokaal;
DROP TABLE IF EXISTS campus;
DROP TABLE IF EXISTS user_reservatie;
DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS reservatie;

-- Table for Campus
CREATE TABLE campus
(
    name            VARCHAR(255) NOT NULL,
    adres           VARCHAR(255),
    parkeerplaatsen INT,
    PRIMARY KEY (name)
);

-- Table for Lokaal
CREATE TABLE lokaal
(
    id             BIGINT NOT NULL AUTO_INCREMENT,
    name           VARCHAR(255),
    typeLokaal     VARCHAR(255),
    aantalPersonen INT,
    verdieping     INT,
    campus_name    VARCHAR(255),
    PRIMARY KEY (id),
    CONSTRAINT fk_lokaal_campus
        FOREIGN KEY (campus_name)
            REFERENCES campus (name)
            ON DELETE SET NULL
            ON UPDATE CASCADE
);

CREATE TABLE reservatie
(
    id         BIGINT NOT NULL AUTO_INCREMENT,
    user_id    BIGINT NOT NULL,
    startDate  DATE,
    endDate    DATE,
    commentaar VARCHAR(500),
    PRIMARY KEY (id)
);

CREATE TABLE user
(
    id            BIGINT NOT NULL AUTO_INCREMENT,
    voorNaam      VARCHAR(255),
    naam          VARCHAR(255),
    geboorteDatum DATE,
    mail          VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE user_reservatie
(
    user_id       BIGINT NOT NULL,
    reservatie_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, reservatie_id),
    CONSTRAINT fk_pc_person FOREIGN KEY (user_id)
        REFERENCES user (id)
        ON DELETE CASCADE,
    CONSTRAINT fk_pc_course FOREIGN KEY (reservatie_id)
        REFERENCES reservatie (id)
        ON DELETE CASCADE
);
