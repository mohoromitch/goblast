# CPS510 Database Systems

## Assignment 4 - Views

The following SQL queries below contain the view generation code for assignment 4.

### View 1

View 1 creates a view that shows the statistics for every player for every season.

#### SQL View Creation Code

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

#### SQL Query Code

```sql

SELECT
  PLAYER_ID AS "Player ID",
  PLAYER_FNAME AS "First Name",
  PLAYER_LNAME AS "Last Name",
  SEASON_ID AS "Season ID",
  TRUNC(AB_AVG, 1) AS "Season AB",
  TRUNC(R_AVG, 1) AS "Season R",
  TRUNC(RBI_AVG, 1) AS "Season RBI",
  TRUNC("1B_AVG", 1) AS "Season 1B",
  TRUNC("2B_AVG", 1) AS "Season 2B",
  TRUNC("3B_AVG", 1) AS "Season 3B",
  TRUNC(HR_AVG, 1) AS "Season HR",
  TRUNC(H, 1) AS "Season Hits",
  TRUNC(BA, 1) AS "Season Batting Average"
FROM PLAYER_SEASON_STATS;
```

#### Result
<table border="1" style="border-collapse:collapse">
<tr><th>Player ID</th><th>First Name</th><th>Last Name</th><th>Season ID</th><th>Season AB</th><th>Season R</th><th>Season RBI</th><th>Season 1B</th><th>Season 2B</th><th>Season 3B</th><th>Season HR</th><th>Season Hits</th><th>Season Batting Average</th></tr>
<tr><td>0</td><td>Cady</td><td>Heron</td><td>1</td><td>7</td><td>0.7</td><td>0.7</td><td>0</td><td>0</td><td>0.2</td><td>0.5</td><td>3</td><td>0.1</td></tr>
<tr><td>0</td><td>Cady</td><td>Heron</td><td>2</td><td>5</td><td>0.2</td><td>0.7</td><td>0</td><td>0</td><td>0</td><td>0.2</td><td>1</td><td>0</td></tr>
<tr><td>1</td><td>Regina</td><td>George</td><td>1</td><td>7</td><td>0.5</td><td>0.7</td><td>0</td><td>0</td><td>0.2</td><td>0.2</td><td>2</td><td>0</td></tr>
<tr><td>1</td><td>Regina</td><td>George</td><td>2</td><td>5</td><td>0</td><td>0</td><td>0</td><td>0</td><td>0</td><td>0</td><td>0</td><td>0</td></tr>
<tr><td>2</td><td>Gretchen</td><td>Wieners</td><td>1</td><td>7</td><td>0.2</td><td>0</td><td>0.2</td><td>0.2</td><td>0</td><td>0</td><td>2</td><td>0</td></tr>
<tr><td>2</td><td>Gretchen</td><td>Wieners</td><td>2</td><td>5</td><td>0.2</td><td>0.2</td><td>0</td><td>0.2</td><td>0</td><td>0.2</td><td>2</td><td>0.1</td></tr>
<tr><td>3</td><td>Janis</td><td>Ian</td><td>1</td><td>7</td><td>0.5</td><td>0.2</td><td>0</td><td>0</td><td>0.2</td><td>0</td><td>1</td><td>0</td></tr>
<tr><td>3</td><td>Janis</td><td>Ian</td><td>2</td><td>5</td><td>0.2</td><td>0.2</td><td>0.2</td><td>0</td><td>0</td><td>0</td><td>1</td><td>0</td></tr>
<tr><td>4</td><td>Karen</td><td>Smith</td><td>1</td><td>6</td><td>0.5</td><td>0</td><td>0.5</td><td>0.5</td><td>0</td><td>0.2</td><td>5</td><td>0.2</td></tr>
<tr><td>4</td><td>Karen</td><td>Smith</td><td>2</td><td>5</td><td>0.5</td><td>0.5</td><td>0.2</td><td>0.2</td><td>0</td><td>0</td><td>2</td><td>0.1</td></tr>
<tr><td>5</td><td>Kevin</td><td>Gnapoor</td><td>1</td><td>6</td><td>0.2</td><td>0.5</td><td>0.5</td><td>0</td><td>0</td><td>0.2</td><td>3</td><td>0.1</td></tr>
<tr><td>5</td><td>Kevin</td><td>Gnapoor</td><td>2</td><td>5</td><td>0.5</td><td>0.5</td><td>0.2</td><td>0.5</td><td>0</td><td>0</td><td>3</td><td>0.1</td></tr>
<tr><td>6</td><td>Trang</td><td>Pak</td><td>1</td><td>6</td><td>0</td><td>0.7</td><td>0.2</td><td>0</td><td>0</td><td>0</td><td>1</td><td>0</td></tr>
<tr><td>6</td><td>Trang</td><td>Pak</td><td>2</td><td>5</td><td>0.5</td><td>0.2</td><td>0.5</td><td>0</td><td>0.2</td><td>0</td><td>3</td><td>0.1</td></tr>
<tr><td>7</td><td>Aaron</td><td>Samuels</td><td>1</td><td>6</td><td>0.2</td><td>0.5</td><td>0.2</td><td>0</td><td>0</td><td>0</td><td>1</td><td>0</td></tr>
<tr><td>7</td><td>Aaron</td><td>Samuels</td><td>2</td><td>5</td><td>0.2</td><td>0</td><td>0</td><td>0</td><td>0</td><td>0.2</td><td>1</td><td>0</td></tr>
<tr><td>8</td><td>Glenn</td><td>Cocoo</td><td>1</td><td>6</td><td>0</td><td>0</td><td>0</td><td>0.2</td><td>0</td><td>0</td><td>1</td><td>0</td></tr>
<tr><td>8</td><td>Glenn</td><td>Cocoo</td><td>2</td><td>5</td><td>0.2</td><td>0.5</td><td>0</td><td>0.2</td><td>0.2</td><td>0</td><td>2</td><td>0.1</td></tr>
<tr><td>9</td><td>Shane</td><td>Oman</td><td>1</td><td>6</td><td>0</td><td>0</td><td>0.2</td><td>0</td><td>0</td><td>0</td><td>1</td><td>0</td></tr>
<tr><td>9</td><td>Shane</td><td>Oman</td><td>2</td><td>5</td><td>0.5</td><td>0.2</td><td>0.2</td><td>0</td><td>0</td><td>0.2</td><td>2</td><td>0.1</td></tr>
<tr><td>10</td><td>Ms.</td><td>Norbury</td><td>1</td><td>7</td><td>0.2</td><td>0.2</td><td>0</td><td>0.2</td><td>0</td><td>0</td><td>1</td><td>0</td></tr>
<tr><td>10</td><td>Ms.</td><td>Norbury</td><td>2</td><td>5</td><td>0.5</td><td>0.2</td><td>0.5</td><td>0.2</td><td>0</td><td>0</td><td>3</td><td>0.1</td></tr>
<tr><td>11</td><td>Mrs.</td><td>George</td><td>1</td><td>7</td><td>0.2</td><td>0.7</td><td>0</td><td>0.2</td><td>0</td><td>0.2</td><td>2</td><td>0</td></tr>
<tr><td>11</td><td>Mrs.</td><td>George</td><td>2</td><td>5</td><td>0.2</td><td>0.5</td><td>0.2</td><td>0</td><td>0.5</td><td>0</td><td>3</td><td>0.1</td></tr>
<tr><td>12</td><td>Caroline</td><td>Krafft</td><td>1</td><td>6</td><td>0.2</td><td>0</td><td>0</td><td>0.2</td><td>0</td><td>0</td><td>1</td><td>0</td></tr>
<tr><td>12</td><td>Caroline</td><td>Krafft</td><td>2</td><td>5</td><td>0.5</td><td>0.2</td><td>0</td><td>0.2</td><td>0.2</td><td>0</td><td>2</td><td>0.1</td></tr>
<tr><td>13</td><td>Coach</td><td>Carr</td><td>1</td><td>6</td><td>0</td><td>0</td><td>0.2</td><td>0</td><td>0</td><td>0</td><td>1</td><td>0</td></tr>
<tr><td>13</td><td>Coach</td><td>Carr</td><td>2</td><td>5</td><td>0.2</td><td>0.7</td><td>0</td><td>0</td><td>0.2</td><td>0.2</td><td>2</td><td>0.1</td></tr>
<tr><td>14</td><td>Principal</td><td>Duvall</td><td>1</td><td>6</td><td>0.5</td><td>0.2</td><td>0</td><td>0</td><td>0.2</td><td>0.2</td><td>2</td><td>0</td></tr>
<tr><td>14</td><td>Principal</td><td>Duvall</td><td>2</td><td>5</td><td>0</td><td>0</td><td>0</td><td>0</td><td>0</td><td>0</td><td>0</td><td>0</td></tr>
<tr><td>15</td><td>Emma</td><td>Gerber</td><td>1</td><td>6</td><td>0</td><td>0</td><td>0</td><td>0</td><td>0</td><td>0</td><td>0</td><td>0</td></tr>
<tr><td>15</td><td>Emma</td><td>Gerber</td><td>2</td><td>5</td><td>0.5</td><td>0</td><td>0</td><td>0.2</td><td>0</td><td>0</td><td>1</td><td>0</td></tr>
<tr><td>16</td><td>Taylor</td><td>Wedell</td><td>1</td><td>6</td><td>0.2</td><td>0</td><td>0.2</td><td>0</td><td>0</td><td>0</td><td>1</td><td>0</td></tr>
<tr><td>17</td><td>Kristen</td><td>Hadley</td><td>1</td><td>6</td><td>0.5</td><td>0.5</td><td>0.5</td><td>0</td><td>0</td><td>0</td><td>2</td><td>0</td></tr>
<tr><td>17</td><td>Kristen</td><td>Hadley</td><td>2</td><td>5</td><td>0.5</td><td>0.2</td><td>0.5</td><td>0</td><td>0</td><td>0</td><td>2</td><td>0.1</td></tr>
<tr><td>18</td><td>Dawn</td><td>Schweitzer</td><td>1</td><td>6</td><td>0</td><td>0</td><td>0.2</td><td>0</td><td>0</td><td>0</td><td>1</td><td>0</td></tr>
<tr><td>18</td><td>Dawn</td><td>Schweitzer</td><td>2</td><td>5</td><td>0.5</td><td>0.2</td><td>0.2</td><td>0</td><td>0.2</td><td>0</td><td>2</td><td>0.1</td></tr>
<tr><td>19</td><td>Tim</td><td>Pak</td><td>1</td><td>6</td><td>0.2</td><td>0.2</td><td>0</td><td>0</td><td>0</td><td>0</td><td>0</td><td>0</td></tr>
<tr><td>19</td><td>Tim</td><td>Pak</td><td>2</td><td>5</td><td>0.2</td><td>0.5</td><td>0</td><td>0.2</td><td>0</td><td>0</td><td>1</td><td>0</td></tr>
<tr><td>20</td><td>Damian</td><td>Leigh</td><td>2</td><td>5</td><td>0.5</td><td>0.2</td><td>0.2</td><td>0</td><td>0</td><td>0</td><td>1</td><td>0</td></tr></table>

### View 2

This view shows the career statistics of every player in the database.

#### SQL Creation Code

```sql
CREATE VIEW PLAYER_CAREER_STATS (
    PLAYER_ID,
    PLAYER_FNAME,
    PLAYER_LNAME,
    AB_CAREER,
    R_CAREER,
    RBI_CAREER,
    "1B_CAREER",
    "2B_CAREER",
    "3B_CAREER",
    "HR_CAREER",
    "H_CAREER",
    "BA_CAREER"
) AS
  SELECT
    PLAYER_ID,
    PLAYER_FNAME,
    PLAYER_LNAME,
    avg(AB_AVG),
    avg(R_AVG),
    avg(RBI_AVG),
    avg("1B_AVG"),
    avg("2B_AVG"),
    avg("3B_AVG"),
    avg(HR_AVG),
    avg(H),
    avg(BA)
  FROM PLAYER_SEASON_STATS
  GROUP BY PLAYER_ID, PLAYER_FNAME, PLAYER_LNAME
  ORDER BY PLAYER_LNAME, PLAYER_FNAME, PLAYER_ID;
```

#### SQL Query Code
```sql
SELECT
  PLAYER_ID AS "Player ID",
  PLAYER_FNAME AS "First Name",
  PLAYER_LNAME AS "Last Name",
  TRUNC(AB_CAREER, 1) AS "Career AB",
  TRUNC(R_CAREER, 1) AS "Career R",
  TRUNC(RBI_CAREER, 1) AS "Career RBI",
  TRUNC("1B_CAREER", 1) AS "Career 1B",
  TRUNC("2B_CAREER", 1) AS "Career 2B",
  TRUNC("3B_CAREER", 1) AS "Career 3B",
  TRUNC(HR_CAREER, 1) AS "Career HR",
  TRUNC(H_CAREER, 1) AS "Career Hits",
  TRUNC(BA_CAREER, 1) AS "Career Batting Average"
FROM PLAYER_CAREER_STATS;
```

#### Result

<table border="1" style="border-collapse:collapse">
<tr><th>Player ID</th><th>First Name</th><th>Last Name</th><th>Career AB</th><th>Career R</th><th>Career RBI</th><th>Career 1B</th><th>Career 2B</th><th>Career 3B</th><th>Career HR</th><th>Career Hits</th><th>Career Batting Average</th></tr>
<tr><td>13</td><td>Coach</td><td>Carr</td><td>5.5</td><td>0.1</td><td>0.3</td><td>0.1</td><td>0</td><td>0.1</td><td>0.1</td><td>1.5</td><td>0</td></tr>
<tr><td>8</td><td>Glenn</td><td>Cocoo</td><td>5.5</td><td>0.1</td><td>0.2</td><td>0</td><td>0.2</td><td>0.1</td><td>0</td><td>1.5</td><td>0</td></tr>
<tr><td>14</td><td>Principal</td><td>Duvall</td><td>5.5</td><td>0.2</td><td>0.1</td><td>0</td><td>0</td><td>0.1</td><td>0.1</td><td>1</td><td>0</td></tr>
<tr><td>11</td><td>Mrs.</td><td>George</td><td>6</td><td>0.2</td><td>0.6</td><td>0.1</td><td>0.1</td><td>0.2</td><td>0.1</td><td>2.5</td><td>0.1</td></tr>
<tr><td>1</td><td>Regina</td><td>George</td><td>6</td><td>0.2</td><td>0.3</td><td>0</td><td>0</td><td>0.1</td><td>0.1</td><td>1</td><td>0</td></tr>
<tr><td>15</td><td>Emma</td><td>Gerber</td><td>5.5</td><td>0.2</td><td>0</td><td>0</td><td>0.1</td><td>0</td><td>0</td><td>0.5</td><td>0</td></tr>
<tr><td>5</td><td>Kevin</td><td>Gnapoor</td><td>5.5</td><td>0.3</td><td>0.5</td><td>0.3</td><td>0.2</td><td>0</td><td>0.1</td><td>3</td><td>0.1</td></tr>
<tr><td>17</td><td>Kristen</td><td>Hadley</td><td>5.5</td><td>0.5</td><td>0.3</td><td>0.5</td><td>0</td><td>0</td><td>0</td><td>2</td><td>0</td></tr>
<tr><td>0</td><td>Cady</td><td>Heron</td><td>6</td><td>0.5</td><td>0.7</td><td>0</td><td>0</td><td>0.1</td><td>0.3</td><td>2</td><td>0</td></tr>
<tr><td>3</td><td>Janis</td><td>Ian</td><td>6</td><td>0.3</td><td>0.2</td><td>0.1</td><td>0</td><td>0.1</td><td>0</td><td>1</td><td>0</td></tr>
<tr><td>12</td><td>Caroline</td><td>Krafft</td><td>5.5</td><td>0.3</td><td>0.1</td><td>0</td><td>0.2</td><td>0.1</td><td>0</td><td>1.5</td><td>0</td></tr>
<tr><td>20</td><td>Damian</td><td>Leigh</td><td>5</td><td>0.5</td><td>0.2</td><td>0.2</td><td>0</td><td>0</td><td>0</td><td>1</td><td>0</td></tr>
<tr><td>10</td><td>Ms.</td><td>Norbury</td><td>6</td><td>0.3</td><td>0.2</td><td>0.2</td><td>0.2</td><td>0</td><td>0</td><td>2</td><td>0</td></tr>
<tr><td>9</td><td>Shane</td><td>Oman</td><td>5.5</td><td>0.2</td><td>0.1</td><td>0.2</td><td>0</td><td>0</td><td>0.1</td><td>1.5</td><td>0</td></tr>
<tr><td>19</td><td>Tim</td><td>Pak</td><td>5.5</td><td>0.2</td><td>0.3</td><td>0</td><td>0.1</td><td>0</td><td>0</td><td>0.5</td><td>0</td></tr>
<tr><td>6</td><td>Trang</td><td>Pak</td><td>5.5</td><td>0.2</td><td>0.5</td><td>0.3</td><td>0</td><td>0.1</td><td>0</td><td>2</td><td>0</td></tr>
<tr><td>7</td><td>Aaron</td><td>Samuels</td><td>5.5</td><td>0.2</td><td>0.2</td><td>0.1</td><td>0</td><td>0</td><td>0.1</td><td>1</td><td>0</td></tr>
<tr><td>18</td><td>Dawn</td><td>Schweitzer</td><td>5.5</td><td>0.2</td><td>0.1</td><td>0.2</td><td>0</td><td>0.1</td><td>0</td><td>1.5</td><td>0</td></tr>
<tr><td>4</td><td>Karen</td><td>Smith</td><td>5.5</td><td>0.5</td><td>0.2</td><td>0.3</td><td>0.3</td><td>0</td><td>0.1</td><td>3.5</td><td>0.1</td></tr>
<tr><td>16</td><td>Taylor</td><td>Wedell</td><td>6</td><td>0.2</td><td>0</td><td>0.2</td><td>0</td><td>0</td><td>0</td><td>1</td><td>0</td></tr>
<tr><td>2</td><td>Gretchen</td><td>Wieners</td><td>6</td><td>0.2</td><td>0.1</td><td>0.1</td><td>0.2</td><td>0</td><td>0.1</td><td>2</td><td>0</td></tr></table>


## Assignment 5 - Advanced Queries

This section of the report contains advanced queries from Assignment 5.

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



