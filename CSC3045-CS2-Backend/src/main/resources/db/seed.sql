INSERT INTO ROLES (DEVELOPER, SCRUM_MASTER, PRODUCT_OWNER) VALUES (true, true, true);
INSERT INTO ROLES (DEVELOPER, SCRUM_MASTER, PRODUCT_OWNER) VALUES (true, false, false);
INSERT INTO ROLES (DEVELOPER, SCRUM_MASTER, PRODUCT_OWNER) VALUES (true, true, false);
INSERT INTO ROLES (DEVELOPER, SCRUM_MASTER, PRODUCT_OWNER) VALUES (false, false, true);

INSERT INTO USER (FORENAME, SURNAME, EMAIL, ROLES_ID) VALUES ('Daenerys', 'Targaryen', 'dany.targaryen@got.wes', 1);
INSERT INTO USER (FORENAME, SURNAME, EMAIL, ROLES_ID) VALUES ('Jon', 'Snow', 'jon.snow@got.wes', 2);
INSERT INTO USER (FORENAME, SURNAME, EMAIL, ROLES_ID) VALUES ('Tyrion', 'Lannister', 'tyrion.lannister@got.wes', 3);
INSERT INTO USER (FORENAME, SURNAME, EMAIL, ROLES_ID) VALUES ('Eddard', 'Stark', 'ned.stark@got.wes', 4);

INSERT INTO ACCOUNT (USERNAME, PASSWORD, USER_ID) VALUES ('dany.targaryen', 'drakaris', 1);
INSERT INTO ACCOUNT (USERNAME, PASSWORD, USER_ID) VALUES ('jon.snow', 'iknownothing', 2);
INSERT INTO ACCOUNT (USERNAME, PASSWORD, USER_ID) VALUES ('tyrion.lannister', 'idrinkandiknowthings', 3);
INSERT INTO ACCOUNT (USERNAME, PASSWORD, USER_ID) VALUES ('ned.stark', 'winteriscoming', 4);

INSERT INTO PROJECT (DESCRIPTION, NAME, MANAGER_ID, PRODUCT_OWNER_ID) VALUES ('Conquer Westeros using dragons', 'Aegons Landing', 1, 1);
INSERT INTO PROJECT (DESCRIPTION, NAME, MANAGER_ID, PRODUCT_OWNER_ID) VALUES ('Defend Westeros from the White Walkers', 'Winter is Coming', 2, 2);

-- users to projects goes here

INSERT INTO USER_STORY (DESCRIPTION, INDEX, MARKET_VALUE, NAME, POINTS, PROJECT_ID) VALUES ('The wall must be defended', 1, 15, 'Defend the Nights Watch', 40, 2);
INSERT INTO USER_STORY (DESCRIPTION, INDEX, MARKET_VALUE, NAME, POINTS, PROJECT_ID) VALUES ('Wights must be burned', 2, 10, 'Kill wights', 21, 2);
INSERT INTO USER_STORY (DESCRIPTION, INDEX, MARKET_VALUE, NAME, POINTS, PROJECT_ID) VALUES ('Walkers must be killed with dragonglass', 3, 5, 'Kill White Walkers', 30, 2);