CREATE TABLE ROLES
(
  ID            BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  DEVELOPER     BOOLEAN,
  SCRUM_MASTER  BOOLEAN,
  PRODUCT_OWNER BOOLEAN
);
CREATE TABLE USER
(
  ID       BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  EMAIL    VARCHAR(255)                      NOT NULL,
  FORENAME VARCHAR(255)                      NOT NULL,
  SURNAME  VARCHAR(255)                      NOT NULL,
  ROLES_ID BIGINT,
  FOREIGN KEY (ROLES_ID) REFERENCES ROLES (ID)
);
CREATE TABLE ACCOUNT
(
  ID       BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  USER_ID  BIGINT                            NOT NULL,
  PASSWORD VARCHAR(255)                      NOT NULL,
  FOREIGN KEY (USER_ID) REFERENCES USER (ID)
);
CREATE TABLE PROJECT
(
  ID               BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  DESCRIPTION      VARCHAR(255)                      NOT NULL,
  NAME             VARCHAR(255)                      NOT NULL,
  MANAGER_ID       BIGINT                            NOT NULL,
  PRODUCT_OWNER_ID BIGINT,
  FOREIGN KEY (MANAGER_ID) REFERENCES USER (ID),
  FOREIGN KEY (PRODUCT_OWNER_ID) REFERENCES USER (ID)
);
CREATE TABLE PROJECT_USER
(
  PROJECT_ID   BIGINT NOT NULL,
  USER_ID      BIGINT NOT NULL,
  SCRUM_MASTER BOOLEAN DEFAULT FALSE,
  FOREIGN KEY (PROJECT_ID) REFERENCES PROJECT (ID),
  FOREIGN KEY (USER_ID) REFERENCES USER (ID)
);
CREATE TABLE SPRINT
(
  ID              BIGINT AUTO_INCREMENT PRIMARY KEY  NOT NULL,
  PROJECT_ID      BIGINT                             NOT NULL,
  NAME            VARCHAR(255)                       NOT NULL,
  START_DATE      TIMESTAMP                          NOT NULL,
  END_DATE        TIMESTAMP                          NOT NULL,
  SCRUM_MASTER_ID BIGINT                             NOT NULL,
  FOREIGN KEY (PROJECT_ID) REFERENCES PROJECT (ID),
  FOREIGN KEY (SCRUM_MASTER_ID) REFERENCES USER (ID)
);
CREATE TABLE SPRINT_USER
(
  SPRINT_ID BIGINT NOT NULL,
  USER_ID   BIGINT NOT NULL,
  FOREIGN KEY (SPRINT_ID) REFERENCES SPRINT (ID),
  FOREIGN KEY (USER_ID) REFERENCES USER (ID)
);
CREATE TABLE USER_STORY
(
  ID           BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  PROJECT_ID   BIGINT                            NOT NULL,
  SPRINT_ID    BIGINT,
  ASSIGNED     BOOLEAN,
  NAME         VARCHAR(255)                      NOT NULL,
  DESCRIPTION  VARCHAR(255)                      NOT NULL,
  POINTS       INTEGER,
  MARKET_VALUE INTEGER,
  INDEX        INTEGER,
  FOREIGN KEY (PROJECT_ID) REFERENCES PROJECT (ID),
  FOREIGN KEY (SPRINT_ID) REFERENCES SPRINT (ID)
);
CREATE TABLE TASK
(
  ID               BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  USER_STORY_ID    BIGINT                            NOT NULL,
  ASSIGNEE         BIGINT,
  NAME             VARCHAR(255)                      NOT NULL,
  DESCRIPTION      VARCHAR(255),
  INITIAL_ESTIMATE INTEGER                           NOT NULL,
  FOREIGN KEY (USER_STORY_ID) REFERENCES USER_STORY (ID),
  FOREIGN KEY (ASSIGNEE) REFERENCES USER (ID)
);
CREATE TABLE ACCEPTANCE_TEST
(
  ID BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  GIVEN VARCHAR(255) NOT NULL,
  WHEN VARCHAR(255) NOT NULL,
  THEN VARCHAR(255) NOT NULL,
  COMPLETED BOOLEAN DEFAULT FALSE,
  USER_STORY_ID BIGINT NOT NULL,
  FOREIGN KEY (USER_STORY_ID) REFERENCES USER_STORY (ID)
);
