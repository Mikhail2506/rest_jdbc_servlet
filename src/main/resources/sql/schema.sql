DROP TABLE IF EXISTS persons CASCADE;
DROP TABLE IF EXISTS persons_sections CASCADE;
DROP TABLE IF EXISTS sections CASCADE;
DROP TABLE IF EXISTS phone_numbers CASCADE;

-- Создание таблицы person
CREATE TABLE IF NOT EXISTS persons
(
    id      SERIAL PRIMARY KEY,
    name    VARCHAR(255) NOT NULL,
    surname VARCHAR(255) NOT NULL,
    age INT NOT NULL
);

-- Создание таблицы section
CREATE TABLE IF NOT EXISTS sections
(
    id   SERIAL PRIMARY KEY,
    section_name VARCHAR(255) NOT NULL
);

-- Создание таблицы phone_number
CREATE TABLE IF NOT EXISTS phone_numbers
(
    id        SERIAL PRIMARY KEY,
    phone     VARCHAR(255) NOT NULL,
    person_id INT         NOT NULL,
    FOREIGN KEY (person_id) REFERENCES persons (id)
);

-- Создание таблицы связи personDTO-section (Many-to-Many)
CREATE TABLE IF NOT EXISTS person_section
(
    persons_sections_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    person_id           INT NOT NULL,
    section_id          INT NOT NULL,
    FOREIGN KEY (person_id) REFERENCES persons (id),
    FOREIGN KEY (section_id) REFERENCES sections (id)
);

INSERT INTO sections (section_name)
VALUES ('Борьба'), -- 1
       ('Бокс'),   -- 2
       ('Теннис'), -- 3
       ('Хоккей'); -- 4

INSERT INTO persons (name, surname, age)
VALUES ('Сергей', 'Субботин', 34), -- 1
       ('Иван', 'Петров', 45),     -- 2
       ('Дмитрий', 'Белый', 31),   -- 3
       ('Евгений', 'Бутько', 55); -- 4

INSERT INTO person_section (person_id, section_id)
VALUES (1, 1), -- 1
       (2, 1), -- 2
       (3, 2), -- 3
       (4, 2), -- 4
       (1, 2), -- 5
       (4, 1), -- 6
       (3, 3), -- 6
       (2, 4); -- 7

INSERT INTO phone_numbers (phone, person_id)
VALUES ('+375(29)123 1111', 1), -- 1
       ('+375(29)123 2222', 1), -- 2
       ('+375(29)123 3333', 2), -- 3
       ('+375(29)123 4444', 2), -- 4
       ('+375(29)123 5555', 3), -- 5
       ('+375(29)123 6666', 3), -- 6
       ('+375(29)123 7777', 4), -- 7
       ('+375(29)123 8888', 4); -- 8

