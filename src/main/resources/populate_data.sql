use shield;

-- create day jobs
INSERT INTO day_job (profession) VALUES ('Millionaire, Entrepreneur, Playboy');
INSERT INTO day_job (profession) VALUES ('Scientist');
INSERT INTO day_job (profession) VALUES ('Spy');
INSERT INTO day_job (profession) VALUES ('Assassin');
INSERT INTO day_job (profession) VALUES ('Agent');
INSERT INTO day_job (profession) VALUES ('Student');
INSERT INTO day_job (profession) VALUES ('Pilot');
INSERT INTO day_job (profession) VALUES ('Soldier');
INSERT INTO day_job (profession) VALUES ('Norse god');


-- create powers
INSERT INTO power (power, description) VALUES ('Flight', 'The hero can fly');
INSERT INTO power (power, description) VALUES ('Super strength', 'The hero is very strong');
INSERT INTO power (power, description) VALUES ('Armored Suit', 'The hero wears an armored suit');
INSERT INTO power (power, description) VALUES ('Fire weapons', 'The hero can fire weapons such as missile, laser, etc');
INSERT INTO power (power, description) VALUES ('Jump high', 'The hero can jump high and far');
INSERT INTO power (power, description) VALUES ('Climb walls', 'The hero can climb walls');
INSERT INTO power (power, description) VALUES ('Use spider webs', 'The hero can use spider webs to attach to surfaces');
INSERT INTO power (power, description) VALUES ('Change size', 'The hero can become smaller or larger');
INSERT INTO power (power, description) VALUES ('Run fast', 'The hero runs very fast');
INSERT INTO power (power, description) VALUES ('Vibranium Shield', 'The hero can use a vibranium shield');
INSERT INTO power (power, description) VALUES ('Use Mjolnir', 'The hero is a worthy man and can lift Mjolnir');
INSERT INTO power (power, description) VALUES ('Calm Hulk', 'The hero can calm the Hulk when he is angry');

-- create heroes and assign jobs and powers
INSERT INTO hero (hero_name, first_name, last_name, day_job_id) VALUES ('Iron man', 'Tony', 'Strak', 1);
INSERT INTO hero_power (hero_id, power_id) VALUES (1, 1);
INSERT INTO hero_power (hero_id, power_id) VALUES (1, 3);
INSERT INTO hero_power (hero_id, power_id) VALUES (1, 4);

INSERT INTO hero (hero_name, first_name, last_name, day_job_id) VALUES ('Spider man', 'Peter', 'Parker', 6);
INSERT INTO hero_power (hero_id, power_id) VALUES (2, 6);
INSERT INTO hero_power (hero_id, power_id) VALUES (2, 7);

INSERT INTO hero (hero_name, first_name, last_name, day_job_id) VALUES ('The Hulk', 'Bruce', 'Banner', 2);
INSERT INTO hero_power (hero_id, power_id) VALUES (3, 2);
INSERT INTO hero_power (hero_id, power_id) VALUES (3, 5);
INSERT INTO hero_power (hero_id, power_id) VALUES (3, 6);

INSERT INTO hero (hero_name, first_name, last_name, day_job_id) VALUES ('Capt. America', 'Steve', 'Rogers', 8);
INSERT INTO hero_power (hero_id, power_id) VALUES (4, 2);
INSERT INTO hero_power (hero_id, power_id) VALUES (4, 9);
INSERT INTO hero_power (hero_id, power_id) VALUES (4, 10);

INSERT INTO hero (hero_name, first_name, last_name, day_job_id) VALUES ('Thor', 'Thor', 'Odinson', 9);
INSERT INTO hero_power (hero_id, power_id) VALUES (5, 1);
INSERT INTO hero_power (hero_id, power_id) VALUES (5, 2);
INSERT INTO hero_power (hero_id, power_id) VALUES (5, 11);