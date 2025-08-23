CREATE TABLE user_roles
(
    user_id UUID        NOT NULL,
    roles   VARCHAR(13) NOT NULL
);

CREATE TABLE users
(
    id              UUID         NOT NULL,
    nome            VARCHAR(255) NOT NULL,
    email           VARCHAR(255) NOT NULL UNIQUE,
    senha           VARCHAR(60)  NOT NULL,
    nome_completo   VARCHAR(255) NOT NULL,
    data_nascimento date         NOT NULL,
    apelido         VARCHAR(255) NOT NULL UNIQUE,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

ALTER TABLE user_roles
    ADD CONSTRAINT fk_user_roles_on_user FOREIGN KEY (user_id) REFERENCES users (id);