# CPS510 Database Systems

## Assignment 5 - Advanced Queries

This section of the report contains advanced queries from Assignment 5. The first two queries were previously submitted in the last report.

### Query 1

This query uses two sub queries unioned to compute the total runs each team had for each season. Two subqueries were used to extract the runs when the team is away and when the team is home. The two values are then summed to find the total for each team for each seasons.

#### SQL Query
```sql
SELECT
  SEASON_ID,
  TEAM_ID,
  TEAMS.NAME AS "Team Name",
  SEASONS.YEAR AS "Season Year",
  SEASONS.TYPE AS "Season Type",
  sum(SCORE_SUM) AS "Team Runs Per Season"
FROM (
  SELECT
    SEASON_ID,
    HOME_TEAM_ID AS TEAM_ID,
    sum(HOME_SCORE) AS SCORE_SUM
  FROM GAMES
  GROUP BY SEASON_ID, HOME_TEAM_ID
  UNION
  SELECT
    SEASON_ID,
    VISITOR_TEAM_ID,
    sum(VISITOR_SCORE) AS VISITOR_SCORE_SUM
  FROM GAMES
  GROUP BY SEASON_ID, VISITOR_TEAM_ID
  )
  JOIN TEAMS ON TEAM_ID = TEAMS.ID
  JOIN SEASONS ON SEASON_ID = SEASONS.ID
GROUP BY SEASON_ID, TEAM_ID, TEAMS.NAME, SEASONS.YEAR, Seasons.TYPE
ORDER BY SEASON_ID ASC, TEAM_ID ASC;
```

#### Result
<table border="1" style="border-collapse:collapse">
<tr><th>SEASON_ID</th><th>TEAM_ID</th><th>Team Name</th><th>Season Year</th><th>Season Type</th><th>Team Runs Per Season</th></tr>
<tr><td>1</td><td>1</td><td>The Plastics</td><td>2015</td><td>regular</td><td>12</td></tr>
<tr><td>1</td><td>2</td><td>The Mathletes</td><td>2015</td><td>regular</td><td>13</td></tr>
<tr><td>2</td><td>1</td><td>The Plastics</td><td>2016</td><td>regular</td><td>13</td></tr>
<tr><td>2</td><td>2</td><td>The Mathletes</td><td>2016</td><td>regular</td><td>14</td></tr></table>


### Query 2

This query uses one subquery pulling every player’s season’s performance data. The performance data is then partitioned by season, and ordered by their batting average. The players with the top 5 batting averages are selected with the where clause and finally ordered by season then ranking. The result is a table showing the top five players for every season.

#### SQL Query


```sql
SELECT
  RN AS "Season Ranking",
  PLAYER_FNAME AS "Player First Name",
  PLAYER_LNAME AS "Player Last Name",
  SEASON_ID AS "Season ID",
  SEASONS.YEAR AS "Season Year",
  SEASONS.TYPE AS "Season Type",
  TRUNC(BA, 3) AS "Batting Average"
FROM
  (
    SELECT PLAYER_ID, PLAYER_FNAME, PLAYER_LNAME, SEASON_ID, BA,
      ROW_NUMBER() OVER (PARTITION BY SEASON_ID ORDER BY BA DESC) RN
    FROM   PLAYER_SEASON_STATS
  ) a
  JOIN SEASONS ON SEASON_ID = SEASONS.ID
WHERE RN <= 5
ORDER BY SEASON_ID, BA DESC;
```

#### Result

<table border="1" style="border-collapse:collapse">
<tr><th>Season Ranking</th><th>Player First Name</th><th>Player Last Name</th><th>Season ID</th><th>Season Year</th><th>Season Type</th><th>Batting Average</th></tr>
<tr><td>1</td><td>Karen</td><td>Smith</td><td>1</td><td>2015</td><td>regular</td><td>0.208</td></tr>
<tr><td>2</td><td>Kevin</td><td>Gnapoor</td><td>1</td><td>2015</td><td>regular</td><td>0.125</td></tr>
<tr><td>3</td><td>Cady</td><td>Heron</td><td>1</td><td>2015</td><td>regular</td><td>0.107</td></tr>
<tr><td>4</td><td>Principal</td><td>Duvall</td><td>1</td><td>2015</td><td>regular</td><td>0.083</td></tr>
<tr><td>5</td><td>Kristen</td><td>Hadley</td><td>1</td><td>2015</td><td>regular</td><td>0.083</td></tr>
<tr><td>1</td><td>Kevin</td><td>Gnapoor</td><td>2</td><td>2016</td><td>regular</td><td>0.15</td></tr>
<tr><td>2</td><td>Mrs.</td><td>George</td><td>2</td><td>2016</td><td>regular</td><td>0.15</td></tr>
<tr><td>3</td><td>Ms.</td><td>Norbury</td><td>2</td><td>2016</td><td>regular</td><td>0.15</td></tr>
<tr><td>4</td><td>Trang</td><td>Pak</td><td>2</td><td>2016</td><td>regular</td><td>0.15</td></tr>
<tr><td>5</td><td>Gretchen</td><td>Wieners</td><td>2</td><td>2016</td><td>regular</td><td>0.1</td></tr></table>



### Query 3

This query, given a date range, calculatings a standing of the top players based off of the games played during that range. The players are ranked based on their batting average.

#### SQL Query

```sql
SELECT
  p.FIRST_NAME AS "FIRST NAME",
  p.LAST_NAME AS "LAST NAME",
  BA,
  GP AS "GAMES PLAYED",
  BA / GP AS "AVG POINTS PER GAME"
FROM PLAYERS p
  RIGHT JOIN (
               SELECT i.PLAYER_ID, i.BA, i.GP
               FROM
                 (
                   SELECT
                     COUNT(*) AS "GP",
                     PLAYER_ID,
                     (SUM("1B") + SUM("2B") + SUM("3B") + SUM(HR)) / SUM(AB) AS BA
                   FROM GAMES
                     LEFT JOIN STATISTICS s2 ON GAMES.ID = s2.GAME_ID
                   WHERE
                     GAME_DATE > TO_DATE(:sd) AND GAME_DATE < TO_DATE(:ed)
                   GROUP BY s2.PLAYER_ID
                 ) i
             ) s ON p.ID = s.PLAYER_ID ORDER BY BA DESC;
```

#### Result

Given the values `16-10-03` and `16-10-13` for the bind variables `:sd` and `:ed`, the following result is produced:

<body>
<table border="1" style="border-collapse:collapse">
<tr><th>FIRST NAME</th><th>LAST NAME</th><th>BA</th><th>GAMES PLAYED</th><th>AVG POINTS PER GAME</th></tr>
<tr><td>Kevin</td><td>Gnapoor</td><td>0.2</td><td>2</td><td>0.1</td></tr>
<tr><td>Trang</td><td>Pak</td><td>0.2</td><td>2</td><td>0.1</td></tr>
<tr><td>Ms.</td><td>Norbury</td><td>0.2</td><td>2</td><td>0.1</td></tr>
<tr><td>Shane</td><td>Oman</td><td>0.2</td><td>2</td><td>0.1</td></tr>
<tr><td>Janis</td><td>Ian</td><td>0.1</td><td>2</td><td>0.05</td></tr>
<tr><td>Coach</td><td>Carr</td><td>0.1</td><td>2</td><td>0.05</td></tr>
<tr><td>Gretchen</td><td>Wieners</td><td>0.1</td><td>2</td><td>0.05</td></tr>
<tr><td>Cady</td><td>Heron</td><td>0.1</td><td>2</td><td>0.05</td></tr>
<tr><td>Karen</td><td>Smith</td><td>0.1</td><td>2</td><td>0.05</td></tr>
<tr><td>Mrs.</td><td>George</td><td>0.1</td><td>2</td><td>0.05</td></tr>
<tr><td>Emma</td><td>Gerber</td><td>0.1</td><td>2</td><td>0.05</td></tr>
<tr><td>Kristen</td><td>Hadley</td><td>0.1</td><td>2</td><td>0.05</td></tr>
<tr><td>Glenn</td><td>Cocoo</td><td>0.1</td><td>2</td><td>0.05</td></tr>
<tr><td>Tim</td><td>Pak</td><td>0</td><td>2</td><td>0</td></tr>
<tr><td>Dawn</td><td>Schweitzer</td><td>0</td><td>2</td><td>0</td></tr>
<tr><td>Principal</td><td>Duvall</td><td>0</td><td>2</td><td>0</td></tr>
<tr><td>Caroline</td><td>Krafft</td><td>0</td><td>2</td><td>0</td></tr>
<tr><td>Aaron</td><td>Samuels</td><td>0</td><td>2</td><td>0</td></tr>
<tr><td>Damian</td><td>Leigh</td><td>0</td><td>2</td><td>0</td></tr>
<tr><td>Regina</td><td>George</td><td>0</td><td>2</td><td>0</td></tr></table>
</body>


### Query 4

The follwing query will produce the winning team id and name for every game, expanding on the information present in the `GAMES` table.

#### SQL Query

```sql
SELECT
  GAME_ID,
  SEASON_ID,
  HOME_TEAM_ID,
  VISITOR_TEAM_ID,
  HOME_SCORE,
  VISITOR_SCORE,
  WINNING_TEAM_ID,
  TEAMS.NAME AS WINNING_TEAM_NAME
FROM (
  SELECT
    GAME_ID,
    SEASON_ID,
    HOME_TEAM_ID,
    VISITOR_TEAM_ID,
    HOME_SCORE,
    VISITOR_SCORE,
    (CASE
     WHEN RUN_DIFFERENTIAL < 0
       THEN VISITOR_TEAM_ID
     WHEN RUN_DIFFERENTIAL > 0
       THEN HOME_TEAM_ID
     ELSE NULL
     END) AS WINNING_TEAM_ID
  FROM (
    SELECT
      GAMES.ID                               AS GAME_ID,
      GAMES.SEASON_ID                        AS SEASON_ID,
      GAMES.HOME_TEAM_ID                     AS HOME_TEAM_ID,
      GAMES.VISITOR_TEAM_ID                  AS VISITOR_TEAM_ID,
      GAMES.HOME_SCORE                       AS HOME_SCORE,
      GAMES.VISITOR_SCORE                    AS VISITOR_SCORE,
      GAMES.HOME_SCORE - GAMES.VISITOR_SCORE AS RUN_DIFFERENTIAL
    FROM GAMES
  )
  ) JOIN TEAMS ON WINNING_TEAM_ID = TEAMS.ID
ORDER BY SEASON_ID, GAME_ID;
```

#### Result

<body>
<table border="1" style="border-collapse:collapse">
<tr><th>GAME_ID</th><th>SEASON_ID</th><th>HOME_TEAM_ID</th><th>VISITOR_TEAM_ID</th><th>HOME_SCORE</th><th>VISITOR_SCORE</th><th>WINNING_TEAM_ID</th><th>WINNING_TEAM_NAME</th></tr>
<tr><td>1</td><td>1</td><td>1</td><td>2</td><td>5</td><td>2</td><td>1</td><td>The Plastics</td></tr>
<tr><td>2</td><td>1</td><td>1</td><td>2</td><td>3</td><td>6</td><td>2</td><td>The Mathletes</td></tr>
<tr><td>4</td><td>1</td><td>2</td><td>1</td><td>3</td><td>2</td><td>2</td><td>The Mathletes</td></tr>
<tr><td>5</td><td>2</td><td>1</td><td>2</td><td>8</td><td>2</td><td>1</td><td>The Plastics</td></tr>
<tr><td>6</td><td>2</td><td>1</td><td>2</td><td>2</td><td>3</td><td>2</td><td>The Mathletes</td></tr>
<tr><td>7</td><td>2</td><td>1</td><td>2</td><td>2</td><td>4</td><td>2</td><td>The Mathletes</td></tr>
<tr><td>8</td><td>2</td><td>1</td><td>2</td><td>1</td><td>5</td><td>2</td><td>The Mathletes</td></tr></table>
</body>


### Query 5

This query will provide the total number of games won per season per team, alongside the team name for readability.

#### SQL Query

```sql
SELECT
  TEAMS.ID,
  TEAMS.NAME,
  SEASON_ID,
  COUNT(WINNING_TEAM_ID) AS WINS
FROM TEAMS
  JOIN (
    SELECT
      GAME_ID,
      SEASON_ID,
      HOME_TEAM_ID,
      VISITOR_TEAM_ID,
      CASE WHEN RUN_DIFFERENTIAL > 0
        THEN HOME_TEAM_ID
      WHEN RUN_DIFFERENTIAL < 0
        THEN VISITOR_TEAM_ID
      ELSE NULL END WINNING_TEAM_ID
    FROM (
      SELECT
        GAMES.ID                               AS GAME_ID,
        GAMES.SEASON_ID                        AS SEASON_ID,
        GAMES.HOME_TEAM_ID                     AS HOME_TEAM_ID,
        GAMES.VISITOR_TEAM_ID                  AS VISITOR_TEAM_ID,
        GAMES.HOME_SCORE                       AS HOME_SCORE,
        GAMES.VISITOR_SCORE                    AS VISITOR_SCORE,
        GAMES.HOME_SCORE - GAMES.VISITOR_SCORE AS RUN_DIFFERENTIAL
      FROM GAMES
    )
    )
    ON TEAMS.ID = WINNING_TEAM_ID
GROUP BY TEAMS.ID, TEAMS.NAME, SEASON_ID
ORDER BY SEASON_ID, TEAMS.ID;
```

#### Result

<body>
<table border="1" style="border-collapse:collapse">
<tr><th>ID</th><th>NAME</th><th>SEASON_ID</th><th>WINS</th></tr>
<tr><td>1</td><td>The Plastics</td><td>1</td><td>1</td></tr>
<tr><td>2</td><td>The Mathletes</td><td>1</td><td>2</td></tr>
<tr><td>1</td><td>The Plastics</td><td>2</td><td>1</td></tr>
<tr><td>2</td><td>The Mathletes</td><td>2</td><td>3</td></tr></table>
</body>