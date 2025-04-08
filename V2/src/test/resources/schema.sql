CREATE TABLE classifiers
(
    id          BIGINT       NOT NULL AUTO_INCREMENT,
    title       VARCHAR(200) NOT NULL,
    description VARCHAR(100) NOT NULL,
    PRIMARY KEY (id)
);

CREATE UNIQUE INDEX ix_classifiers_title ON classifiers (title);

CREATE TABLE classifier_values
(
    id            BIGINT       NOT NULL AUTO_INCREMENT,
    classifier_id BIGINT       NOT NULL,
    ic            VARCHAR(200) NOT NULL,
    description   VARCHAR(500) NOT NULL,
    PRIMARY KEY (id)
);

ALTER TABLE classifier_values
    ADD FOREIGN KEY (classifier_id) REFERENCES classifiers (id);

CREATE UNIQUE INDEX ix_classifier_values_ic
    ON classifier_values (ic);

CREATE TABLE IF NOT EXISTS country_default_day_rate
(
    id               BIGINT         NOT NULL AUTO_INCREMENT,
    country_ic       VARCHAR(200)   NOT NULL,
    default_day_rate DECIMAL(10, 2) NOT NULL,
    PRIMARY KEY (id)
);
CREATE UNIQUE INDEX ix_country_default_day_rate_country_ic ON country_default_day_rate (country_ic);

CREATE TABLE IF NOT EXISTS travel_medical_age_coefficient
(
    id          BIGINT         NOT NULL AUTO_INCREMENT,
    age_from    INTEGER         NOT NULL,
    age_to      INTEGER         NOT NULL,
    coefficient DECIMAL(10, 2) NOT NULL,
    PRIMARY KEY (id)
);

CREATE UNIQUE INDEX `ix_travel_medical_age_coefficient` ON travel_medical_age_coefficient (age_from,age_to);

CREATE TABLE IF NOT EXISTS medical_risk_limit_level(
    id BIGINT NOT NULL AUTO_INCREMENT,
    medical_risk_limit_level_ic VARCHAR(200) NOT NULL,
    coefficient DECIMAL (10, 2) NOT NULL,
    PRIMARY KEY(id)
);
CREATE UNIQUE INDEX `ix_medical_risk_limit_level` ON medical_risk_limit_level (medical_risk_limit_level_ic);

-- Создание таблицы persons
CREATE TABLE persons (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         first_name VARCHAR(200) NOT NULL,
                         last_name VARCHAR(200) NOT NULL,
                         person_code VARCHAR(100) NOT NULL,
                         birth_date DATE NOT NULL
);

-- Создание уникального индекса на first_name, last_name, person_code
CREATE UNIQUE INDEX ix_unique_persons
    ON persons (first_name, last_name, person_code);

CREATE TABLE agreements (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            uuid UUID NOT NULL UNIQUE,
                            date_from DATE NOT NULL,
                            date_to DATE NOT NULL,
                            country VARCHAR(100) NOT NULL,
                            premium DECIMAL(10,2) NOT NULL
);

CREATE TABLE selected_risks (
                                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                agreement_id BIGINT NOT NULL,
                                risk_ic VARCHAR(100) NOT NULL
);

-- Создание уникального индекса на agreement_id и risk_ic
CREATE UNIQUE INDEX ix_selected_risks_agreement_id_risk_ic
    ON selected_risks (agreement_id, risk_ic);

-- Создание таблицы agreement_persons
CREATE TABLE agreement_persons (
                                   id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                   agreement_id BIGINT NOT NULL,
                                   person_id BIGINT NOT NULL,
                                   medical_risk_limit_level VARCHAR(100) NOT NULL
);

-- Создание уникального индекса на agreement_id и person_id
CREATE UNIQUE INDEX ix_agreement_person_agreement_id_person_id
    ON agreement_persons (agreement_id, person_id);

CREATE TABLE person_risks (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              person_risks_id BIGINT NOT NULL,
                              risk_ic VARCHAR(100) NOT NULL,
                              premium DECIMAL(10,2) NOT NULL
);

-- Создание уникального индекса на person_risks_id и risk_ic
CREATE UNIQUE INDEX ix_person_risks
    ON person_risks (person_risks_id, risk_ic);

-- Добавление внешнего ключа, связывающего person_risks с agreement_persons
ALTER TABLE person_risks
    ADD CONSTRAINT fk_agreement_person_risks_agreement_person_id
        FOREIGN KEY (person_risks_id)
            REFERENCES agreement_persons (id) ON DELETE CASCADE;

CREATE TABLE travel_cost_coefficient (
                                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                    travel_cost_from DECIMAL(10,2) NOT NULL,
                                    travel_cost_to DECIMAL(10,2) NOT NULL,
                                    coefficient DECIMAL(10,2) NOT NULL
);

CREATE UNIQUE INDEX ix_travel_cost_coefficient
    ON travel_cost_coefficient (travel_cost_from, travel_cost_to);

CREATE TABLE travel_cancellation_age_coefficient (
                                    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                    age_from INTEGER NOT NULL,
                                    age_to INTEGER NOT NULL,
                                    coefficient DECIMAL(10, 2) NOT NULL
);

CREATE TABLE travel_cancellation_country_safety_rating (
                                    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                    country_ic VARCHAR(100) NOT NULL,
                                    coefficient DECIMAL(10, 2) NOT NULL
);
