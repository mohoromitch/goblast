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
