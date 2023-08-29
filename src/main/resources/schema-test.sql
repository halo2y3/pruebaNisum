CREATE TABLE users (id_user uuid NOT NULL, name varchar(80) NOT NULL, email varchar(100) NOT NULL UNIQUE, password varchar(100) NOT NULL, created timestamp NOT NULL, modified timestamp NOT NULL, PRIMARY KEY (id_user));
CREATE TABLE sessions (id_session uuid NOT NULL, token text(500) NOT NULL, is_active bit DEFAULT 'true' NOT NULL, last_login timestamp NOT NULL, id_user uuid NOT NULL, PRIMARY KEY (id_session));
CREATE TABLE phones (id_phone uuid NOT NULL, number varchar(15) NOT NULL, city_code varchar(5), country_code varchar(5), id_user uuid NOT NULL, PRIMARY KEY (id_phone));
ALTER TABLE sessions ADD CONSTRAINT FK_sessions_user FOREIGN KEY (id_user) REFERENCES users (id_user);
ALTER TABLE phones ADD CONSTRAINT FK_phones_user FOREIGN KEY (id_user) REFERENCES users (id_user);