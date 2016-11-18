# CPS510 Assignment 8 - Normalization/BCNF

## `CREATE TABLE` Modifications

For this assignment, the `STATISTICS` table was normalized and changed to BCNF form.

The original table generation code was:


```sql
CREATE TABLE MMOHOROV.STATISTICS
(
 PLAYER_ID INT NOT NULL,
 GAME_ID INT NOT NULL,
 AB INT NOT NULL,
 H INT NOT NULL,
 R INT NOT NULL,
 RBI INT NOT NULL,
 "1B" INT NOT NULL,
 "2B" INT NOT NULL,
 "3B" INT NOT NULL,
 HR INT NOT NULL,
 CONSTRAINT STATISTICS_ID PRIMARY KEY (PLAYER_ID, GAME_ID),
 CONSTRAINT PLAYER_ID_S FOREIGN KEY (PLAYER_ID) REFERENCES PLAYERS (ID),
 CONSTRAINT GAME_ID FOREIGN KEY (GAME_ID) REFERENCES GAMES (ID)
 )
```

This was changed to:

```sql
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
```

Which removed the references to the primary keys of other entities.

To maintain the same relationship, a new table `PERFORMED_IN` was created which maps the `ID` of players with the `ID` of games with the `ID` of a statistic entity.
The SQL table generation code for this new table is below.

```sql
CREATE TABLE MMOHOROV.PERFORMED_IN
(
 PLAYER_ID INT NOT NULL,
 GAME_ID INT NOT NULL,
 STATISTIC_ID INT NOT NULL,
 CONSTRAINT PERFORMED_IN_PERFORMANCE_ID PRIMARY KEY 
 (PLAYER_ID, GAME_ID, STATISTIC_ID),
 CONSTRAINT PERFORMED_IN_PLAYER_ID_S FOREIGN KEY 
 (PLAYER_ID) REFERENCES PLAYERS (ID),
 CONSTRAINT PERFORMED_IN_GAME_ID FOREIGN KEY 
 (GAME_ID) REFERENCES GAMES (ID),
 CONSTRAINT PERFORMED_IN_STATISTIC_ID FOREIGN KEY 
 (STATISTIC_ID) REFERENCES STATISTICS(ID)
 ); 
```

## `INSERT` Modification

Since the `STATISTICS` table was modified, the insertions had to be modified as well. All the foreign keys that were present in every `STATISTICS` `INSERT` was moved to a new `INSERT` for the `PERFORMS_IN` table.

## `VIEW` Modification

One view which used the previous `STATISTICS` table had to be modified in order to work with the new normalized `STATISTICS` table.

```sql
CREATE VIEW PLAYER_SEASON_STATS (
    PLAYER_ID,
    SEASON_ID,
    PLAYER_FNAME,
    PLAYER_LNAME,
    AB_AVG, R_AVG,
    RBI_AVG,
    "1B_AVG",
    "2B_AVG",
    "3B_AVG",
    "HR_AVG",
    H,
    BA)
AS
  SELECT
    s.PLAYER_ID,
    SEASONS.ID,
    PLAYERS.FIRST_NAME,
    PLAYERS.LAST_NAME,
    avg(s.AB),
    avg(s.R),
    avg(s.RBI),
    avg(s."1B"),
    avg(s."2B"),
    avg(s."3B"),
    avg(s.HR),
    sum(s."1B") + sum(s."2B") + sum(s."3B") + sum(s.HR),
    (sum(s."1B") + sum(s."2B") + sum(s."3B") + sum(s.HR) ) / sum(s.AB)
  FROM STATISTICS s
    INNER JOIN GAMES
      ON s.GAME_ID = GAMES.ID
    INNER JOIN SEASONS
      ON SEASONS.ID = GAMES.SEASON_ID
    RIGHT OUTER JOIN PLAYERS
      ON s.PLAYER_ID = PLAYERS.ID
  GROUP BY s.PLAYER_ID, SEASONS.ID, PLAYERS.FIRST_NAME, PLAYERS.LAST_NAME
  ORDER BY s.PLAYER_ID, SEASONS.ID
  WITH READ ONLY;
```

An additional join was used to map the player's ID with the newly added statistics ID.

```sql
CREATE VIEW PLAYER_SEASON_STATS (
    PLAYER_ID,
    SEASON_ID,
    PLAYER_FNAME,
    PLAYER_LNAME,
    AB_AVG, R_AVG,
    RBI_AVG,
    "1B_AVG",
    "2B_AVG",
    "3B_AVG",
    "HR_AVG",
    H,
    BA)
AS
  SELECT
    pi.PLAYER_ID,
    SEASONS.ID,
    PLAYERS.FIRST_NAME,
    PLAYERS.LAST_NAME,
    avg(s.AB),
    avg(s.R),
    avg(s.RBI),
    avg(s."1B"),
    avg(s."2B"),
    avg(s."3B"),
    avg(s.HR),
    sum(s."1B") + sum(s."2B") + sum(s."3B") + sum(s.HR),
    (sum(s."1B") + sum(s."2B") + sum(s."3B") + sum(s.HR) ) / sum(s.AB)
  FROM PERFORMED_IN pi
  LEFT JOIN STATISTICS s ON pi.STATISTIC_ID=s.ID
    INNER JOIN GAMES
      ON pi.GAME_ID = GAMES.ID
    INNER JOIN SEASONS
      ON SEASONS.ID = GAMES.SEASON_ID
    RIGHT OUTER JOIN PLAYERS
      ON pi.PLAYER_ID = PLAYERS.ID
  GROUP BY pi.PLAYER_ID, SEASONS.ID, PLAYERS.FIRST_NAME, PLAYERS.LAST_NAME
  ORDER BY pi.PLAYER_ID, SEASONS.ID
  WITH READ ONLY;
```


## `DROP` Modification

The only necessary change to dropping tables is the addition of the following statement:

```sql
DROP TABLE MMOHOROV.PERFORMED_IN;
```

Which drops the new `PERFORMED_IN` table.
