INSERT INTO ROLES (DEVELOPER, SCRUM_MASTER, PRODUCT_OWNER) VALUES (FALSE, FALSE, FALSE); -- 1: None
INSERT INTO ROLES (DEVELOPER, SCRUM_MASTER, PRODUCT_OWNER) VALUES (FALSE, FALSE, TRUE); -- 2: PO
INSERT INTO ROLES (DEVELOPER, SCRUM_MASTER, PRODUCT_OWNER) VALUES (FALSE, TRUE, FALSE); -- 3: SM
INSERT INTO ROLES (DEVELOPER, SCRUM_MASTER, PRODUCT_OWNER) VALUES (FALSE, TRUE, TRUE); -- 4: SM PO
INSERT INTO ROLES (DEVELOPER, SCRUM_MASTER, PRODUCT_OWNER) VALUES (TRUE, FALSE, FALSE); -- 5: DEV
INSERT INTO ROLES (DEVELOPER, SCRUM_MASTER, PRODUCT_OWNER) VALUES (TRUE, FALSE, TRUE); -- 6: DEV PO
INSERT INTO ROLES (DEVELOPER, SCRUM_MASTER, PRODUCT_OWNER) VALUES (TRUE, TRUE, FALSE); -- 7: DEV SM
INSERT INTO ROLES (DEVELOPER, SCRUM_MASTER, PRODUCT_OWNER) VALUES (TRUE, TRUE, TRUE); -- 8: DEV SM PO


INSERT INTO USER (FORENAME, SURNAME, EMAIL, PROFILE_PICTURE, ROLES_ID) VALUES ('Snoop', 'Dogg', 'snoop.dogg@shizzle.hold.up', 'snoop.jpg', 8); -- id: 1
INSERT INTO USER (FORENAME, SURNAME, EMAIL, PROFILE_PICTURE, ROLES_ID) VALUES ('Richard', 'Hendrix', 'r.hendrix@valley.com', 'richard_hendrix.jpg', 8); -- id: 2
INSERT INTO USER (FORENAME, SURNAME, EMAIL, PROFILE_PICTURE, ROLES_ID) VALUES ('Bertram', 'Gilfoyle', 'b.gilfoyle@valley.com', 'bertram_gilfoyle.jpg', 7); -- id: 3
INSERT INTO USER (FORENAME, SURNAME, EMAIL, PROFILE_PICTURE, ROLES_ID) VALUES ('Dinesh', 'Chugtai', 'd.chugtai@valley.com', 'dinesh_chugtai.jpg', 7); -- id: 4
INSERT INTO USER (FORENAME, SURNAME, EMAIL, PROFILE_PICTURE, ROLES_ID) VALUES ('Erlich', 'Bachman', 'e.bachman@valley.com', 'erlich_bachman.jpg', 2); -- id: 5
INSERT INTO USER (FORENAME, SURNAME, EMAIL, PROFILE_PICTURE, ROLES_ID) VALUES ('Jared', 'Dunn', 'j.dunn@valley.com', 'jared_dunn.jpg', 4); -- id: 6
INSERT INTO USER (FORENAME, SURNAME, EMAIL, PROFILE_PICTURE, ROLES_ID) VALUES ('Nelson', 'Bighetti', 'big.head@valley.com', 'nelson_bighetti.jpg', 5); -- id: 7
INSERT INTO USER (FORENAME, SURNAME, EMAIL, PROFILE_PICTURE, ROLES_ID) VALUES ('Jian', 'Yang', 'j.yang@valley.com', 'jian_yang.jpg', 5); -- id: 8
INSERT INTO USER (FORENAME, SURNAME, EMAIL, PROFILE_PICTURE, ROLES_ID) VALUES ('Gavin', 'Belson', 'g.belson@valley.com', 'gavin_belson.jpg', 1); -- id: 9
INSERT INTO USER (FORENAME, SURNAME, EMAIL, PROFILE_PICTURE, ROLES_ID) VALUES ('Russ', 'Hanneman', 'r.hanneman@valley.com', 'russ_hanneman.jpg', 2); -- id: 10
INSERT INTO USER (FORENAME, SURNAME, EMAIL, PROFILE_PICTURE, ROLES_ID) VALUES ('Monica', 'Hall', 'm.hall@valley.com', 'monica_hall.jpg', 3); -- id: 11
INSERT INTO USER (FORENAME, SURNAME, EMAIL, PROFILE_PICTURE, ROLES_ID) VALUES ('Jack', 'Barker', 'j.barker@valley.com', 'jack_barker.jpg', 8); -- id: 12
INSERT INTO USER (FORENAME, SURNAME, EMAIL, PROFILE_PICTURE, ROLES_ID) VALUES ('Keenan', 'Feldspar', 'k.feldspar@valley.com', 'keenan_feldspar.jpg', 8); -- id: 13


-- E2E Test Data
INSERT INTO USER (FORENAME, SURNAME, EMAIL, PROFILE_PICTURE, ROLES_ID) VALUES ('e2eForename1', 'e2eSurname1', 'user1@e2e.com', 'default.jpg', 5); -- id: 14
INSERT INTO USER (FORENAME, SURNAME, EMAIL, PROFILE_PICTURE, ROLES_ID) VALUES ('e2eForename2', 'e2eSurname2', 'user2@e2e.com', 'default.jpg', 1); -- id: 15
INSERT INTO USER (FORENAME, SURNAME, EMAIL, PROFILE_PICTURE, ROLES_ID) VALUES ('e2eForename3', 'e2eSurname3', 'user3@e2e.com', 'default.jpg', 2); -- id: 16
INSERT INTO USER (FORENAME, SURNAME, EMAIL, PROFILE_PICTURE, ROLES_ID) VALUES ('e2eForename4', 'e2eSurname4', 'user4@e2e.com', 'default.jpg', 2); -- id: 17
INSERT INTO USER (FORENAME, SURNAME, EMAIL, PROFILE_PICTURE, ROLES_ID) VALUES ('e2eForename5', 'e2eSurname5', 'user5@e2e.com', 'default.jpg', 3); -- id: 18
INSERT INTO USER (FORENAME, SURNAME, EMAIL, PROFILE_PICTURE, ROLES_ID) VALUES ('e2eForename6', 'e2eSurname6', 'user6@e2e.com', 'default.jpg', 3); -- id: 19
INSERT INTO USER (FORENAME, SURNAME, EMAIL, PROFILE_PICTURE, ROLES_ID) VALUES ('e2eForename7', 'e2eSurname7', 'user7@e2e.com', 'default.jpg', 3); -- id: 20
INSERT INTO USER (FORENAME, SURNAME, EMAIL, PROFILE_PICTURE, ROLES_ID) VALUES ('e2eForename8', 'e2eSurname8', 'user8@e2e.com', 'default.jpg', 5); -- id: 21
INSERT INTO USER (FORENAME, SURNAME, EMAIL, PROFILE_PICTURE, ROLES_ID) VALUES ('e2eForename9', 'e2eSurname9', 'user9@e2e.com', 'default.jpg', 5); -- id: 22
INSERT INTO USER (FORENAME, SURNAME, EMAIL, PROFILE_PICTURE, ROLES_ID) VALUES ('e2eForename10', 'e2eSurname10', 'user10@e2e.com', 'default.jpg', 5); -- id: 23


-- All the passwords below are 'Silic0n', except for first one for Snoop Dogg, which is F0shizzle
INSERT INTO ACCOUNT (PASSWORD, USER_ID)
VALUES ('$2a$12$misQNO0JawDg7ux/6aP3teTboi//BXWO4U1vCM6ApMGmmH6WVS6KG', 1);
INSERT INTO ACCOUNT (PASSWORD, USER_ID)
VALUES ('$2a$12$erdr9N/9eM/j479kPmdqnustR3jwHydY5BzbmUMVFKrPDTpSH2r1q', 2);
INSERT INTO ACCOUNT (PASSWORD, USER_ID)
VALUES ('$2a$12$erdr9N/9eM/j479kPmdqnustR3jwHydY5BzbmUMVFKrPDTpSH2r1q', 3);
INSERT INTO ACCOUNT (PASSWORD, USER_ID)
VALUES ('$2a$12$erdr9N/9eM/j479kPmdqnustR3jwHydY5BzbmUMVFKrPDTpSH2r1q', 4);
INSERT INTO ACCOUNT (PASSWORD, USER_ID)
VALUES ('$2a$12$erdr9N/9eM/j479kPmdqnustR3jwHydY5BzbmUMVFKrPDTpSH2r1q', 5);
INSERT INTO ACCOUNT (PASSWORD, USER_ID)
VALUES ('$2a$12$erdr9N/9eM/j479kPmdqnustR3jwHydY5BzbmUMVFKrPDTpSH2r1q', 6);
INSERT INTO ACCOUNT (PASSWORD, USER_ID)
VALUES ('$2a$12$erdr9N/9eM/j479kPmdqnustR3jwHydY5BzbmUMVFKrPDTpSH2r1q', 7);
INSERT INTO ACCOUNT (PASSWORD, USER_ID)
VALUES ('$2a$12$erdr9N/9eM/j479kPmdqnustR3jwHydY5BzbmUMVFKrPDTpSH2r1q', 8);
INSERT INTO ACCOUNT (PASSWORD, USER_ID)
VALUES ('$2a$12$erdr9N/9eM/j479kPmdqnustR3jwHydY5BzbmUMVFKrPDTpSH2r1q', 9);
INSERT INTO ACCOUNT (PASSWORD, USER_ID)
VALUES ('$2a$12$erdr9N/9eM/j479kPmdqnustR3jwHydY5BzbmUMVFKrPDTpSH2r1q', 10);
INSERT INTO ACCOUNT (PASSWORD, USER_ID)
VALUES ('$2a$12$erdr9N/9eM/j479kPmdqnustR3jwHydY5BzbmUMVFKrPDTpSH2r1q', 11);
INSERT INTO ACCOUNT (PASSWORD, USER_ID)
VALUES ('$2a$12$erdr9N/9eM/j479kPmdqnustR3jwHydY5BzbmUMVFKrPDTpSH2r1q', 12);
INSERT INTO ACCOUNT (PASSWORD, USER_ID)
VALUES ('$2a$12$erdr9N/9eM/j479kPmdqnustR3jwHydY5BzbmUMVFKrPDTpSH2r1q', 13);


-- E2E Test Data
-- All the passwords below are 'Aut0mation'
INSERT INTO ACCOUNT (PASSWORD, USER_ID)
VALUES ('$2a$04$OqV735nAjlL9ddhdoCFTNeuxCgperWBOrt4j1RHlleE8lTgY40Ntq', 14);
INSERT INTO ACCOUNT (PASSWORD, USER_ID)
VALUES ('$2a$04$OqV735nAjlL9ddhdoCFTNeuxCgperWBOrt4j1RHlleE8lTgY40Ntq', 15);
INSERT INTO ACCOUNT (PASSWORD, USER_ID)
VALUES ('$2a$04$OqV735nAjlL9ddhdoCFTNeuxCgperWBOrt4j1RHlleE8lTgY40Ntq', 16);
INSERT INTO ACCOUNT (PASSWORD, USER_ID)
VALUES ('$2a$04$OqV735nAjlL9ddhdoCFTNeuxCgperWBOrt4j1RHlleE8lTgY40Ntq', 17);
INSERT INTO ACCOUNT (PASSWORD, USER_ID)
VALUES ('$2a$04$OqV735nAjlL9ddhdoCFTNeuxCgperWBOrt4j1RHlleE8lTgY40Ntq', 18);
INSERT INTO ACCOUNT (PASSWORD, USER_ID)
VALUES ('$2a$04$OqV735nAjlL9ddhdoCFTNeuxCgperWBOrt4j1RHlleE8lTgY40Ntq', 19);
INSERT INTO ACCOUNT (PASSWORD, USER_ID)
VALUES ('$2a$04$OqV735nAjlL9ddhdoCFTNeuxCgperWBOrt4j1RHlleE8lTgY40Ntq', 20);
INSERT INTO ACCOUNT (PASSWORD, USER_ID)
VALUES ('$2a$04$OqV735nAjlL9ddhdoCFTNeuxCgperWBOrt4j1RHlleE8lTgY40Ntq', 21);
INSERT INTO ACCOUNT (PASSWORD, USER_ID)
VALUES ('$2a$04$OqV735nAjlL9ddhdoCFTNeuxCgperWBOrt4j1RHlleE8lTgY40Ntq', 22);
INSERT INTO ACCOUNT (PASSWORD, USER_ID)
VALUES ('$2a$04$OqV735nAjlL9ddhdoCFTNeuxCgperWBOrt4j1RHlleE8lTgY40Ntq', 23);


INSERT INTO PROJECT (NAME, DESCRIPTION, MANAGER_ID, PRODUCT_OWNER_ID) -- id: 1
VALUES ('Pied Piper', 'Cloud storage using revolutionary middle-out compression.', 2, 5);
INSERT INTO PROJECT (NAME, DESCRIPTION, MANAGER_ID, PRODUCT_OWNER_ID) -- id: 2
VALUES ('Piper Chat', 'A super fast video chat application, using middle-out compression.', 4, 5);
INSERT INTO PROJECT (NAME, DESCRIPTION, MANAGER_ID, PRODUCT_OWNER_ID) -- id: 3
VALUES ('Hooli Chat', 'A super fast video chat application, using middle-out compression.', 9, 12);
INSERT INTO PROJECT (NAME, DESCRIPTION, MANAGER_ID, PRODUCT_OWNER_ID) -- id: 4
VALUES ('The New Internet', 'Using middle-out compression, de-centralise the internet by using edge and personal devices.', 2, 10);


-- E2E Test Data
INSERT INTO PROJECT (NAME, DESCRIPTION, MANAGER_ID, PRODUCT_OWNER_ID) -- id: 5
VALUES ('e2eProjectName1', 'e2eProjectDescription1', 15, 16);
INSERT INTO PROJECT (NAME, DESCRIPTION, MANAGER_ID, PRODUCT_OWNER_ID) -- id: 6
VALUES ('e2eProjectName2', 'e2eProjectDescription2', 16, 16);


INSERT INTO PROJECT_USER (PROJECT_ID, USER_ID, SCRUM_MASTER) VALUES (1, 2, TRUE);
INSERT INTO PROJECT_USER (PROJECT_ID, USER_ID) VALUES (1, 3);
INSERT INTO PROJECT_USER (PROJECT_ID, USER_ID) VALUES (1, 4);
INSERT INTO PROJECT_USER (PROJECT_ID, USER_ID) VALUES (1, 5);
INSERT INTO PROJECT_USER (PROJECT_ID, USER_ID, SCRUM_MASTER) VALUES (1, 6, TRUE);
INSERT INTO PROJECT_USER (PROJECT_ID, USER_ID) VALUES (1, 7);
INSERT INTO PROJECT_USER (PROJECT_ID, USER_ID) VALUES (1, 8);
INSERT INTO PROJECT_USER (PROJECT_ID, USER_ID) VALUES (2, 3);
INSERT INTO PROJECT_USER (PROJECT_ID, USER_ID) VALUES (2, 4);
INSERT INTO PROJECT_USER (PROJECT_ID, USER_ID) VALUES (2, 5);
INSERT INTO PROJECT_USER (PROJECT_ID, USER_ID) VALUES (2, 7);
INSERT INTO PROJECT_USER (PROJECT_ID, USER_ID) VALUES (2, 8);
INSERT INTO PROJECT_USER (PROJECT_ID, USER_ID, SCRUM_MASTER) VALUES (2, 11, TRUE);
INSERT INTO PROJECT_USER (PROJECT_ID, USER_ID) VALUES (3, 1);
INSERT INTO PROJECT_USER (PROJECT_ID, USER_ID) VALUES (3, 2);
INSERT INTO PROJECT_USER (PROJECT_ID, USER_ID) VALUES (3, 7);
INSERT INTO PROJECT_USER (PROJECT_ID, USER_ID) VALUES (3, 8);
INSERT INTO PROJECT_USER (PROJECT_ID, USER_ID, SCRUM_MASTER) VALUES (3, 12, TRUE);
INSERT INTO PROJECT_USER (PROJECT_ID, USER_ID) VALUES (3, 13);
INSERT INTO PROJECT_USER (PROJECT_ID, USER_ID, SCRUM_MASTER) VALUES (4, 2, TRUE);
INSERT INTO PROJECT_USER (PROJECT_ID, USER_ID) VALUES (4, 10);
INSERT INTO PROJECT_USER (PROJECT_ID, USER_ID) VALUES (4, 6);


-- E2E Test Data
INSERT INTO PROJECT_USER (PROJECT_ID, USER_ID) VALUES (5, 16);
INSERT INTO PROJECT_USER (PROJECT_ID, USER_ID) VALUES (5, 17);
INSERT INTO PROJECT_USER (PROJECT_ID, USER_ID, SCRUM_MASTER) VALUES (5, 18, TRUE);
INSERT INTO PROJECT_USER (PROJECT_ID, USER_ID) VALUES (5, 19);
INSERT INTO PROJECT_USER (PROJECT_ID, USER_ID) VALUES (5, 20);
INSERT INTO PROJECT_USER (PROJECT_ID, USER_ID) VALUES (5, 21);
INSERT INTO PROJECT_USER (PROJECT_ID, USER_ID) VALUES (5, 22);


INSERT INTO SPRINT (PROJECT_ID, NAME, START_DATE, END_DATE, SCRUM_MASTER_ID) -- id: 1
VALUES (1, 'Sprint 1 - Compression', DATEADD('WEEK', 4, CURRENT_DATE), DATEADD('WEEK', 6, CURRENT_DATE), 2);
INSERT INTO SPRINT (PROJECT_ID, NAME, START_DATE, END_DATE, SCRUM_MASTER_ID) -- id: 2
VALUES (1, 'Sprint 1 - Cloud', DATEADD('WEEK', 5, CURRENT_DATE), DATEADD('WEEK', 7, CURRENT_DATE), 6);
INSERT INTO SPRINT (PROJECT_ID, NAME, START_DATE, END_DATE, SCRUM_MASTER_ID) -- id: 3
VALUES (1, 'Sprint 2 - Compression', DATEADD('WEEK', 10, CURRENT_DATE), DATEADD('WEEK', 12, CURRENT_DATE), 2);
INSERT INTO SPRINT (PROJECT_ID, NAME, START_DATE, END_DATE, SCRUM_MASTER_ID) -- id: 4
VALUES (2, 'Sprint 1 - Video chat', DATEADD('WEEK', 10, CURRENT_DATE), DATEADD('WEEK', 12, CURRENT_DATE), 1);


INSERT INTO SPRINT_USER (SPRINT_ID, USER_ID) VALUES (1, 2);
INSERT INTO SPRINT_USER (SPRINT_ID, USER_ID) VALUES (1, 3);
INSERT INTO SPRINT_USER (SPRINT_ID, USER_ID) VALUES (1, 8);
INSERT INTO SPRINT_USER (SPRINT_ID, USER_ID) VALUES (2, 4);
INSERT INTO SPRINT_USER (SPRINT_ID, USER_ID) VALUES (2, 7);
INSERT INTO SPRINT_USER (SPRINT_ID, USER_ID) VALUES (1, 2);
INSERT INTO SPRINT_USER (SPRINT_ID, USER_ID) VALUES (1, 3);

INSERT INTO USER_STORY (NAME, DESCRIPTION, INDEX, POINTS, MARKET_VALUE, PROJECT_ID, SPRINT_ID) -- id: 1
VALUES ('Compress and upload a file', 'Using the algorithm, a user should be able to upload a file to the cloud.', 0, 8, 32, 1, 1);
INSERT INTO USER_STORY (NAME, DESCRIPTION, INDEX, POINTS, MARKET_VALUE, PROJECT_ID, SPRINT_ID) -- id: 2
VALUES ('Download and decompress a file', 'Using the algorithm, a user should be able to download a file from the cloud.', 1, 8, 36, 1, 3);
INSERT INTO USER_STORY (NAME, DESCRIPTION, INDEX, POINTS, MARKET_VALUE, PROJECT_ID, SPRINT_ID) -- id: 3
VALUES ('Auto-sync photos', 'Auto-synchronisation should be an option to automatically upload photos taken on device.', 2, 13, 55, 1, 2);
INSERT INTO USER_STORY (NAME, DESCRIPTION, INDEX, POINTS, MARKET_VALUE, PROJECT_ID) -- id: 4
VALUES ('Offline mode', 'Saving files locally via the app to be access offline.', 3, 5, 15, 1);
INSERT INTO USER_STORY (NAME, DESCRIPTION, INDEX, POINTS, MARKET_VALUE, PROJECT_ID) -- id: 5
VALUES ('Authentication', 'Login/Register with the video-chat app', 1, 30, 50, 2);
INSERT INTO USER_STORY (NAME, DESCRIPTION, INDEX, POINTS, MARKET_VALUE, PROJECT_ID) -- id: 6
VALUES ('Add friends', 'Ability to add friends to chat with', 2, 20, 100, 2);
INSERT INTO USER_STORY (NAME, DESCRIPTION, INDEX, POINTS, MARKET_VALUE, PROJECT_ID, SPRINT_ID) -- id: 7
VALUES ('Chat image resolution', 'Improve the video-chat image resolution', 3, 50, 100, 2, 4);

INSERT INTO TASK (USER_STORY_ID, ASSIGNEE_ID, NAME, DESCRIPTION, INITIAL_ESTIMATE) VALUES (1,1,'Compress the File','Compressing Task',5);
INSERT INTO TASK (USER_STORY_ID, ASSIGNEE_ID, NAME, DESCRIPTION, INITIAL_ESTIMATE) VALUES (1,2,'Upload the File','Uploading the File',15);


INSERT INTO ACCEPTANCE_TEST (GIVEN, WHEN, THEN, USER_STORY_ID) -- id: 1
VALUES ('The user has selected a file', 'The user tries to upload it', 'The file should be compressed and uploaded', 1);
INSERT INTO ACCEPTANCE_TEST (GIVEN, WHEN, THEN, USER_STORY_ID) -- id: 2
VALUES ('The user has selected a file', 'The user tries to download it', 'The file should be downloaded and decompressed', 2);
INSERT INTO ACCEPTANCE_TEST (GIVEN, WHEN, THEN, USER_STORY_ID) -- id: 3
VALUES ('There is internet connection', 'The user has taken a photo', 'The file should be automatically compressed and uploaded', 3);
INSERT INTO ACCEPTANCE_TEST (GIVEN, WHEN, THEN, USER_STORY_ID) -- id: 4
VALUES ('There is no internet connection', 'The user has taken a photo', 'The file should be added to a queue to be uploaded when connection is available', 3);
INSERT INTO ACCEPTANCE_TEST (GIVEN, WHEN, THEN, USER_STORY_ID) -- id: 5
VALUES ('There is internet connection', 'The user has selected a file for offline mode', 'The file should be downloaded and compressed to be accessible without a connection', 4);


INSERT INTO SKILL (DESCRIPTION, USER_ID) 
VALUES ('Java', 2);
INSERT INTO SKILL (DESCRIPTION, USER_ID) 
VALUES ('C++', 2);
INSERT INTO SKILL (DESCRIPTION, USER_ID) 
VALUES ('Ruby', 2);
INSERT INTO SKILL (DESCRIPTION, USER_ID) 
VALUES ('JavaScript', 2);
INSERT INTO SKILL (DESCRIPTION, USER_ID) 
VALUES ('Project Management', 2);
INSERT INTO SKILL (DESCRIPTION, USER_ID) 
VALUES ('Linux', 2);