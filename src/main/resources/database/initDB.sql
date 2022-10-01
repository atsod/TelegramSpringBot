DROP TABLE IF EXISTS users;
DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq START WITH 100000;

CREATE TABLE IF NOT EXISTS users
(
    id INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    name varchar(200) NOT NULL,
    email varchar(254) NOT NULL,
    phone varchar(20) NOT NULL
)