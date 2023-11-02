CREATE DATABASE IF NOT EXISTS spotify_visualization_system;
USE spotify_visualization_system;

CREATE TABLE IF NOT EXISTS user (
    id       bigint auto_increment  primary key comment 'id',
    username varchar(32)            not null    comment 'username',
    password varchar(512)           not null    comment 'password',
    index idx_username(username)
) comment 'User' collate  = utf8mb4_unicode_ci;
