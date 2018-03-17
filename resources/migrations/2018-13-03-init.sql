-- Holds all data about authenticated users
-- see https://github.com/luminus-framework/luminus-template/blob/master/resources/leiningen/new/luminus/db/migrations/add-users-table.up.sql
CREATE TABLE IF NOT EXISTS users
(id SERIAL PRIMARY KEY,
first_name VARCHAR(64),
last_name VARCHAR(64),
email VARCHAR(64),
admin BOOLEAN,
last_login TIMESTAMP,
is_active BOOLEAN,
pass VARCHAR(300));


-- Blog posts data:
CREATE TABLE IF NOT EXISTS posts
(id SERIAL PRIMARY KEY,
user_id INT NOT NULL REFERENCES users(id),
title VARCHAR(256),
text TEXT,
created_date TIMESTAMP,
updated_date TIMESTAMP);
