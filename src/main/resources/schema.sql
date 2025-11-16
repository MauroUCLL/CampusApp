-- Drop tables if they exist to start fresh
DROP TABLE IF EXISTS lokaal;
DROP TABLE IF EXISTS campus;

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