INSERT INTO public.roles(role_id, role_name, server_id, uniqueness, staff, role_type)
VALUES (1, 'Clan1', '809702674756665414', true, false, 'clan'),
       (2, 'Clan2', '809702674756665414', true, false, 'clan'),
       (3, 'Game1', '809702674756665414', true, false, 'game'),
       (4, 'Game2', '809702674756665414', true, false, 'game');
--------------------------------------------------------------------------------------------------------
INSERT INTO public.roles(id, clans, games, developments, staffs)
VALUES ('433740544229769227', 'roles_clans', 'roles_games', 'roles_developments', 'roles_staffs'),
       ('809702674756665414', 'roles_clans', 'roles_games', 'roles_developments', 'roles_staffs');

INSERT INTO public.roles_clans(role_name, id, type)
VALUES ('Clan1', '809702674756665414', 'clan'),
       ('Clan2', '809702674756665414', 'clan');

INSERT INTO public.roles_games(role_name, id, type)
VALUES ('Game1', '809702674756665414', 'clan'),
       ('Game2', '809702674756665414', 'clan');