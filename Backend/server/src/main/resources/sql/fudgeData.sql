INSERT IGNORE INTO users VALUES (1, 'DragonSlayer21', 'letmein', 'daddysgirl@yahoo.com', NULL );
INSERT IGNORE INTO users VALUES (2, 'DragonSlayerSlayer', 'letmein', 'fupa@yahoo.com', NULL );
INSERT IGNORE INTO users VALUES (3, 'DragonLov3r', 'letmein', 'dupa@yahoo.com', NULL );

INSERT IGNORE INTO games VALUES (1, 'Space Shooter', NULL );
INSERT IGNORE INTO games VALUES (2, 'Space Shooter+', NULL );
INSERT IGNORE INTO games VALUES (3, 'Space Shooter++', NULL );
INSERT IGNORE INTO games VALUES (4, 'Snake', NULL );
INSERT IGNORE INTO games VALUES (5, 'Snake+', NULL );
INSERT IGNORE INTO games VALUES (6, 'Snake++', NULL );

INSERT IGNORE INTO scores VALUES (1, 1, 1, 5, CURRENT_TIME());
INSERT IGNORE INTO scores VALUES (2, 1, 2, 5, CURRENT_TIME());
INSERT IGNORE INTO scores VALUES (3, 1, 2, 10, CURRENT_TIME());
INSERT IGNORE INTO scores VALUES (4, 2, 1, 11, CURRENT_TIME());
INSERT IGNORE INTO scores VALUES (5, 2, 1, 10, CURRENT_TIME());
INSERT IGNORE INTO scores VALUES (6, 2, 3, 1, CURRENT_TIME());
INSERT IGNORE INTO scores VALUES (7, 5, 3, 1, CURRENT_TIME());

INSERT IGNORE INTO role_types VALUES (1, 'Free User', 'Free users are served ads after playing a game');
INSERT IGNORE INTO role_types VALUES (2, 'Premium User', 'Premium users arent served ads after playing a game');
INSERT IGNORE INTO role_types VALUES (3, 'Admin', 'Admins do what they want, when they want');

UPDATE role_types set role_types.description = 'Admins do what they want, when they want' where roleid = 3;


INSERT IGNORE INTO roles VALUES(1,3,1);
INSERT IGNORE INTO roles VALUES(2,3,2);
INSERT IGNORE INTO roles VALUES(3,3,3);
INSERT IGNORE INTO roles VALUES(4,3,4);