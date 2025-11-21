# noinspection SqlWithoutWhereForFile
DELETE FROM lokaal_reservatie;
DELETE FROM user_reservatie;
DELETE FROM reservatie;
DELETE FROM user;
DELETE FROM lokaal;
DELETE FROM campus;

INSERT INTO campus (name, adres, parkeerplaatsen)
VALUES ('PROXIMUS', 'Proximuslaan 1, 3001 Heverlee', 300),
       ('GASTHUISBERG', 'Herestraat 49, 3000 Leuven', 250),
       ('HERTOGSTRAAT', 'Hertogstraat 178, 3001 Heverlee', 200);

INSERT INTO lokaal (id, name, type_lokaal, aantal_personen, verdieping, campus_name)
VALUES (1, 'A-101', 'LESLOKAAL', 25, 1, 'PROXIMUS'),
       (2, 'A-201', 'COMPUTERLOKAAL', 30, 2, 'PROXIMUS'),
       (3, 'B-105', 'LESLOKAAL', 40, 1, 'GASTHUISBERG'),
       (4, 'B-301', 'AUDITORIUM', 120, 3, 'GASTHUISBERG'),
       (5, 'C-010', 'COMPUTERLOKAAL', 20, 0, 'HERTOGSTRAAT'),
       (6, 'C-220', 'VERGADERZAAL', 12, 2, 'HERTOGSTRAAT');

INSERT INTO user (id, voor_naam, naam, geboorte_datum, mail)
VALUES (1, 'Alice', 'Johnson', '1993-05-12', 'alice@ucll.be'),
       (2, 'Bob', 'Smith', '1990-11-03', 'bob@ucll.be'),
       (3, 'Charlie', 'Adams', '1997-07-21', 'charlie@ucll.be'),
       (4, 'Diana', 'Brown', '1988-02-17', 'diana@ucll.be'),
       (5, 'Ethan', 'Baker', '1995-01-30', 'ethan@ucll.be');

INSERT INTO reservatie (id, user_id, start_date, end_date, commentaar)
VALUES (1, 1, '2025-03-20', '2025-03-21', 'Teamvergadering'),
       (2, 2, '2025-04-01', '2025-04-02', 'Projectbespreking'),
       (3, 3, '2025-04-05', '2025-04-05', 'Examinoefening'),
       (4, 4, '2025-04-10', '2025-04-11', 'IT workshop'),
       (5, 5, '2025-04-15', '2025-04-15', 'Interviewruimte nodig');

INSERT INTO user_reservatie (user_id, reservatie_id)
VALUES (1, 1),
       (1, 2),
       (2, 2),
       (3, 3),
       (4, 4),
       (5, 5);

INSERT INTO lokaal_reservatie (lokaal_id, reservatie_id)
VALUES (1, 1),
       (3, 1),
       (2, 2),
       (4, 2),
       (5, 3),
       (6, 4),
       (1, 5),
       (6, 5);
