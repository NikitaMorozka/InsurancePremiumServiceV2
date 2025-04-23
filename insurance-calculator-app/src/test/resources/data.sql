INSERT INTO classifiers (title, description)
VALUES('RISK_TYPE', 'Risk type classifier');

-- Получаем id классификатора 'RISK_TYPE'
SET @classifiers_id_risk_type = (SELECT id FROM classifiers WHERE title = 'RISK_TYPE');

-- Вставляем значения для классификатора 'RISK_TYPE'
INSERT INTO classifier_values(classifier_id, ic, description)
VALUES
    (@classifiers_id_risk_type, 'TRAVEL_MEDICAL', 'Medical expenses coverage.'),
    (@classifiers_id_risk_type, 'TRAVEL_CANCELLATION', 'Trip cancellation coverage.'),
    (@classifiers_id_risk_type, 'TRAVEL_LOSS_BAGGAGE', 'Lost baggage coverage.'),
    (@classifiers_id_risk_type, 'TRAVEL_THIRD_PARTY_LIABILITY', 'Third-party liability coverage.'),
    (@classifiers_id_risk_type, 'TRAVEL_EVACUATION', 'Emergency evacuation coverage.'),
    (@classifiers_id_risk_type, 'TRAVEL_SPORT_ACTIVITIES', 'Sports activities coverage.');

-- Вставляем классификатор 'COUNTRY'
INSERT INTO classifiers (title, description)
VALUES('COUNTRY', 'Country classifier');

-- Получаем id классификатора 'COUNTRY'
SET @classifiers_id_country = (SELECT id FROM classifiers WHERE title = 'COUNTRY');

-- Вставляем значения для классификатора 'COUNTRY'
INSERT INTO classifier_values(classifier_id, ic, description)
VALUES
    (@classifiers_id_country, 'LATVIA', 'Country Latvia.'),
    (@classifiers_id_country, 'SPAIN', 'Country Spain.'),
    (@classifiers_id_country, 'JAPAN', 'Country Japan.');

-- Вставляем ставки для стран
INSERT INTO country_default_day_rate (country_ic, default_day_rate)
VALUES
    ('LATVIA', 1.00),
    ('SPAIN', 2.50),
    ('JAPAN', 3.50);

-- Вставляем ставки для возраста
INSERT INTO travel_medical_age_coefficient(age_from, age_to, coefficient)
VALUES (0, 5, 1.1),
       (6, 10, 0.7),
       (11, 17, 1.0),
       (18, 40, 1.1),
       (41, 65, 1.2),
       (66, 150, 1.5);

-- Вставляем ставки для лимита выплат
INSERT INTO classifiers (title, description)
VALUES ('MEDICAL_RISK_LIMIT_LEVEL', 'Medical risk limit level classifier');

SET@medical_risk_limit_level = (SELECT id FROM classifiers WHERE title = 'MEDICAL_RISK_LIMIT_LEVEL');

INSERT INTO classifier_values(classifier_id, ic, description)
VALUES (@medical_risk_limit_level, 'LEVEL_10000', 'Medical Risk 10000 euro limit level'),
       (@medical_risk_limit_level, 'LEVEL_15000', 'Medical Risk 15000 euro limit level'),
       (@medical_risk_limit_level, 'LEVEL_20000', 'Medical Risk 20000 euro limit level'),
       (@medical_risk_limit_level, 'LEVEL_50000', 'Medical Risk 50000 euro limit level');

INSERT INTO medical_risk_limit_level(medical_risk_limit_level_ic, coefficient)
VALUES('LEVEL_10000', 1.0),
      ('LEVEL_15000', 1.2),
      ('LEVEL_20000', 1.5),
      ('LEVEL_50000', 2.0);

INSERT INTO travel_cost_coefficient(travel_cost_from, travel_cost_to, coefficient)
VALUES (0, 4999.99, 10.0),
       (5000, 9999.99, 30.0),
       (10000, 19999.99, 100.0),
       (20000, 1000000, 500.0);

INSERT INTO travel_cancellation_age_coefficient(age_from, age_to, coefficient)
VALUES (0, 9, 5.0),
       (10, 17, 10.0),
       (18, 39, 20.0),
       (40, 64, 30.0),
       (65, 150, 50.0);

INSERT INTO travel_cancellation_country_safety_rating(country_ic, coefficient)
VALUES ('LATVIA', 5.0),
       ('SPAIN', 8.0),
       ('JAPAN', 9.0);