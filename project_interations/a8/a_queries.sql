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
                     LEFT JOIN PERFORMED_IN pi ON pi.GAME_ID=GAMES.ID
                     LEFT JOIN STATISTICS s2 ON s2.ID = pi.STATISTIC_ID
                   WHERE
                     GAME_DATE > TO_DATE(:sd) AND GAME_DATE < TO_DATE(:ed)
                   GROUP BY pi.PLAYER_ID
                 ) i
             ) s ON p.ID = s.PLAYER_ID ORDER BY BA DESC;

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