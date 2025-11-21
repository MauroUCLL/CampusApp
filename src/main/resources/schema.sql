DROP TABLE IF EXISTS lokaal_reservatie;
DROP TABLE IF EXISTS user_reservatie;
DROP TABLE IF EXISTS reservatie;
DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS lokaal;
DROP TABLE IF EXISTS campus;

CREATE TABLE campus
(
    name            VARCHAR(255) NOT NULL,
    adres           VARCHAR(255),
    parkeerplaatsen INT,
    PRIMARY KEY (name)
);

CREATE TABLE lokaal
(
    id             BIGINT NOT NULL AUTO_INCREMENT,
    name           VARCHAR(255),
    type_lokaal    VARCHAR(255),
    aantal_personen INT,
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
    id          BIGINT NOT NULL AUTO_INCREMENT,
    user_id     BIGINT NOT NULL,
    start_date  DATE,
    end_date    DATE,
    commentaar  VARCHAR(500),
    PRIMARY KEY (id)
);

CREATE TABLE user
(
    id             BIGINT NOT NULL AUTO_INCREMENT,
    voor_naam      VARCHAR(255),
    naam           VARCHAR(255),
    geboorte_datum DATE,
    mail           VARCHAR(255),
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
    CONSTRAINT fk_pc_reserv FOREIGN KEY (reservatie_id)
        REFERENCES reservatie (id)
        ON DELETE CASCADE
);

CREATE TABLE lokaal_reservatie
(
    lokaal_id     BIGINT NOT NULL,
    reservatie_id BIGINT NOT NULL,
    PRIMARY KEY (lokaal_id, reservatie_id),

    CONSTRAINT fk_lr_lokaal FOREIGN KEY (lokaal_id)
        REFERENCES lokaal (id)
        ON DELETE CASCADE,

    CONSTRAINT fk_lr_reserv FOREIGN KEY (reservatie_id)
        REFERENCES reservatie (id)
        ON DELETE CASCADE
);
