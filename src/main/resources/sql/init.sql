CREATE DATABASE IF NOT EXISTS spotify_visualization_system;
USE spotify_visualization_system;

DROP TABLE IF EXISTS user;
CREATE TABLE IF NOT EXISTS user (
    id                           bigint auto_increment  primary key comment 'id',
    username                     varchar(32)            not null    comment 'username',
    password                     varchar(512)           not null    comment 'password',

    spotify_username             varchar(128)                       comment 'spotify username',
    spotify_email                varchar(128)                       comment 'spotify email',
    spotify_country              varchar(16)                        comment 'spotify country',
    spotify_created_from_facebook  bool                               comment 'spotify createdFromFacebook',
    spotify_facebook_uid          varchar(128)                       comment 'spotify facebookUid',
    spotify_gender                varchar(16)                        comment 'spotify gender',
    spotify_postal_code           varchar(16)                        comment 'spotify postalCode',
    spotify_mobile_number         varchar(64)                        comment 'spotify mobileNumber',
    spotify_mobile_operator       varchar(64)                        comment 'spotify mobileOperator',
    spotify_mobile_brand          varchar(64)                        comment 'spotify mobileBrand',
    spotify_birthdate             date                           comment 'spotify birthdate',
    spotify_create_time           date                           comment 'spotify creationTime',

    spotify_addresses            TEXT                               comment 'spotify address',
    spotify_payments             TEXT                               comment 'spotify payments',
    spotify_inferences           TEXT                               comment 'spotify payments',

    playlists_ready              BOOL     default false             comment 'playlists table is ready',
    streaming_history_ready      BOOL     default false             comment 'streaming history table is ready',
    index idx_username(username)
) comment 'User' collate  = utf8mb4_unicode_ci;


DROP TABLE IF EXISTS song_features;
CREATE TABLE IF NOT EXISTS song_features (
    track_uri        VARCHAR(64) PRIMARY KEY  comment 'track uri',
    danceability     DOUBLE                   comment 'danceability',
    energy           DOUBLE                   comment 'energy',
    loudness         DOUBLE                   comment 'loudness',
    liveness         DOUBLE                   comment 'liveness',
    valence          DOUBLE                   comment 'valence',
    tempo            DOUBLE                   comment 'tempo'
) comment 'Song features obtained from spotify api' collate = utf8mb4_unicode_ci;