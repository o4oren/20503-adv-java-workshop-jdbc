-- CREATE DATABASE IF NOT EXISTS shield;

-- USE shield;

drop table if exists hero_power;
drop table if exists hero;
drop table if exists power;
drop table if exists day_job;


create table day_job
(
    id INTEGER not null primary key autoincrement,
    profession VARCHAR(50)
);

create table hero
(
    id INTEGER not null primary key autoincrement,
    hero_name VARCHAR(50) not null,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    day_job_id INTEGER,
    FOREIGN KEY(day_job_id) REFERENCES day_job(id)
);

create table power
(
    id INTEGER not null primary key autoincrement,
    power VARCHAR(50),
    description VARCHAR(200)
);

create table hero_power
(
    hero_id INTEGER,
    power_id INTEGER,
    PRIMARY KEY (hero_id, power_id),
    FOREIGN KEY (hero_id) REFERENCES hero(id),
    FOREIGN KEY (power_id) REFERENCES power(id)
);




