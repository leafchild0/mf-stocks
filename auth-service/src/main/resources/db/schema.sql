drop table IF EXISTS user_roles;
drop table IF EXISTS users;
drop table IF EXISTS roles;

CREATE TABLE IF NOT EXISTS users(
    id bigint(20) NOT NULL AUTO_INCREMENT primary key,
    username varchar(50) not null,
    password varchar not null,
    email varchar (100) not null,
    enabled boolean not null,
    UNIQUE KEY uk_users_username (username)
);

CREATE TABLE IF NOT EXISTS roles(
    id bigint(20) NOT NULL AUTO_INCREMENT primary key,
    name varchar(50) not null,
    UNIQUE KEY uk_roles_name (name)
);

CREATE TABLE IF NOT EXISTS user_roles (
  user_id bigint(20) NOT NULL,
  role_id bigint(20) NOT NULL,
  PRIMARY KEY (user_id, role_id),
  CONSTRAINT fk_user_roles_role_id FOREIGN KEY (role_id) REFERENCES roles (id),
  CONSTRAINT fk_user_roles_user_id FOREIGN KEY (user_id) REFERENCES users (id)
);
