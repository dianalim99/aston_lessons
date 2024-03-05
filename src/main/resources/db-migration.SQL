DROP DATABASE IF EXISTS test;
CREATE DATABASE test;

DROP TABLE IF EXISTS student CASCADE;
CREATE TABLE student
(
    id serial PRIMARY KEY,
    first_name       VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL
);

INSERT INTO student (first_name, last_name)
VALUES ('Иван', 'Иванов'),
       ('Петр', 'Петров'),
       ('Александр', 'Александров'),
       ('Дмитрий', 'Дмитриев');

DROP TABLE IF EXISTS teacher CASCADE;
CREATE TABLE teacher
(
    id serial PRIMARY KEY,
    first_name       VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL

);

INSERT INTO teacher (first_name, last_name, email, password)
VALUES ('Иван', 'Иванов', 'ivan@test.com', 'ivan'),
       ('Петр', 'Петров', 'petr@test.com', 'petr'),
       ('Александр', 'Александров', 'alex@test.com', 'alex'),
       ('Дмитрий', 'Дмитриев', 'dima@test.com', 'dima');

DROP TABLE IF EXISTS lesson CASCADE;
CREATE TABLE lesson (
                        id SERIAL PRIMARY KEY,
                        id_teacher BIGINT REFERENCES teacher(id),
                        "date" TIMESTAMP
);

INSERT INTO lesson (id_teacher, "date")
VALUES (1, '2024-03-03 01:18:08'),
       (2, '2024-02-01 01:18:08');

DROP TABLE IF EXISTS lesson_to_student CASCADE;
CREATE TABLE lesson_to_student (
                                   id_lesson BIGINT,
                                   id_student BIGINT,
                                   PRIMARY KEY (id_lesson, id_student),
                                   FOREIGN KEY (id_lesson) REFERENCES lesson(id),
                                   FOREIGN KEY (id_student) REFERENCES student(id)
);

INSERT INTO lesson_to_student (id_lesson, id_student)
VALUES (1, 1),
       (1, 2),
       (2, 3);



