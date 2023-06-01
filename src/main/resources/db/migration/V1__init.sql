create table users
(
    id          UUID not null,
    nick_name   varchar(255) not null,
    first_name  varchar(255),
    second_name varchar(255),
    primary key (id),
    unique (nick_name)
)