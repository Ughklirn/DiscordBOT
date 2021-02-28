DO
$$ DECLARE
r RECORD;
BEGIN
FOR r IN (SELECT tablename FROM pg_tables WHERE schemaname = current_schema()) LOOP
    EXECUTE 'DROP TABLE ' || quote_ident(r.tablename) || ' CASCADE';
END LOOP;
END $$;


CREATE TABLE initialize
(
    BOT   text NOT NULL PRIMARY KEY,
    TOKEN text NOT NULL
);

CREATE TABLE settings
(
    ID           text NOT NULL PRIMARY KEY,
    PREFIX       text,
    MUSIC_VOLUME integer
);

CREATE TABLE channels
(
    ID                text NOT NULL PRIMARY KEY,
    BOT_LOG_ID        text,
    COMMANDS_ADMIN_ID text,
    COMMANDS_ID       text,
    ROLES_ID          text,
    MUSIC_ID          text,
    MUSIC_TEAM_ID     text,
    MUSIC_USER_ID     text
);

CREATE TABLE commands
(
    ID               text NOT NULL PRIMARY KEY,
    TEST             text,
    SHUTDOWN         text,
    CONFIG_SAVE      text,
    CONFIG_LOAD      text,
    CONFIG_CLEAR     text,
    ROLE_JOIN        text,
    ROLE_LEAVE       text,
    MUSIC_PLAY       text,
    MUSIC_STOP       text,
    MUSIC_VOLUME     text,
    MUSIC_REPEAT     text,
    MUSIC_REPEATS    text,
    MUSIC_REPEAT_ALL text,
    MUSIC_PAUSE      text,
    MUSIC_SKIP       text
);

CREATE TABLE reactions
(
    ID               text NOT NULL PRIMARY KEY,
    OK               text,
    YES              text,
    NO               text,
    ACCEPT           text,
    ERROR            text,
    MUSIC_REPEAT_ALL text,
    MUSIC_REPEAT_ONE text,
    MUSIC_PLAY       text,
    MUSIC_STOP       text,
    MUSIC_PAUSE      text,
    MUSIC_FORWARD    text,
    MUSIC_NEXT       text,
    MUSIC_VOLUME     text
);

CREATE TABLE ROLES
(
    ROLE_ID    integer NOT NULL PRIMARY KEY,
    SERVER_ID  text,
    NAME       text,
    UNIQUENESS boolean,
    STAFF      boolean
);