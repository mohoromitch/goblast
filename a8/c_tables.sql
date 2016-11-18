CREATE TABLE MMOHOROV.TEAMS
(
  id INT PRIMARY KEY NOT NULL,
  name VARCHAR(25)
);


CREATE TABLE MMOHOROV.PLAYERS
(
  id INT PRIMARY KEY NOT NULL,
  first_name VARCHAR(25),
  last_name VARCHAR(25)
);


CREATE TABLE MMOHOROV.SEASONS
(
  id INT PRIMARY KEY,
  year INT NOT NULL,
  type VARCHAR(10) NOT NULL
);


CREATE TABLE MMOHOROV.PLAYS_FOR
(
  PLAYER_ID INT,
  SEASON_ID INT,
  TEAM_ID INT,
  CONSTRAINT PLAY PRIMARY KEY (PLAYER_ID, TEAM_ID, SEASON_ID),
  CONSTRAINT PLAYER_ID FOREIGN KEY (PLAYER_ID) REFERENCES PLAYERS (ID),
  CONSTRAINT SEASON_ID FOREIGN KEY (SEASON_ID) REFERENCES SEASONS (ID),
  CONSTRAINT TEAM_ID FOREIGN KEY (TEAM_ID) REFERENCES TEAMS (ID)
);


CREATE TABLE MMOHOROV.GAMES
(
  ID INT PRIMARY KEY NOT NULL,
  HOME_TEAM_ID INT NOT NULL,
  VISITOR_TEAM_ID INT NOT NULL,
  SEASON_ID INT NOT NULL,
  HOME_SCORE INT NOT NULL,
  VISITOR_SCORE INT NOT NULL,
  GAME_LOCATION VARCHAR2(50) NOT NULL,
  GAME_DATE TIMESTAMP NOT NULL,
  CONSTRAINT HOME_TEAM_ID FOREIGN KEY (HOME_TEAM_ID) REFERENCES TEAMS (ID),
  CONSTRAINT VISITOR_TEAM_ID FOREIGN KEY (VISITOR_TEAM_ID) REFERENCES TEAMS (ID),
  CONSTRAINT GAME_SEASON_ID FOREIGN KEY (SEASON_ID) REFERENCES SEASONS (ID)
);

CREATE TABLE MMOHOROV.STATISTICS
(
  ID INT PRIMARY KEY NOT NULL,
  AB INT NOT NULL,
  H INT NOT NULL,
  R INT NOT NULL,
  RBI INT NOT NULL,
  "1B" INT NOT NULL,
  "2B" INT NOT NULL,
  "3B" INT NOT NULL,
  HR INT NOT NULL
);


CREATE TABLE MMOHOROV.PERFORMED_IN
(
  PLAYER_ID INT NOT NULL,
  GAME_ID INT NOT NULL,
  STATISTIC_ID INT NOT NULL,
  CONSTRAINT PERFORMED_IN_PERFORMANCE_ID PRIMARY KEY (PLAYER_ID, GAME_ID, STATISTIC_ID),
  CONSTRAINT PERFORMED_IN_PLAYER_ID_S FOREIGN KEY (PLAYER_ID) REFERENCES PLAYERS (ID),
  CONSTRAINT PERFORMED_IN_GAME_ID FOREIGN KEY (GAME_ID) REFERENCES GAMES (ID),
  CONSTRAINT PERFORMED_IN_STATISTIC_ID FOREIGN KEY (STATISTIC_ID) REFERENCES STATISTICS(ID)
);