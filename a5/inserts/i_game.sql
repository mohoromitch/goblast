ACCEPT i_id NUMBER PROMPT 'Game Id: '
ACCEPT i_home_team_id NUMBER PROMPT 'Home Team Id: '
ACCEPT i_visitor_team_id NUMBER PROMPT 'Visitor Team Id: '
ACCEPT i_season_id NUMBER PROMPT 'Season Id: '
ACCEPT i_home_score NUMBER PROMPT 'Home Team Final Score: '
ACCEPT i_visitor_score NUMBER PROMPT 'Visitor Team Final Score: '
ACCEPT i_game_location CHAR FORMAT 'A20' prompt 'Game Location: '
ACCEPT i_game_date CHAR FORMAT 'A20' prompt 'Game Date (YY-MON-DD HH24:MI): '
INSERT INTO GAMES (ID, HOME_TEAM_ID, VISITOR_TEAM_ID, SEASON_ID, HOME_SCORE, VISITOR_SCORE, GAME_LOCATION, GAME_DATE)
VALUES (&i_id, &i_home_team_id, &i_visitor_team_id, &i_season_id, &i_home_score, &i_visitor_score, '&i_game_location', TO_DATE('&i_game_date','YY-MON-DD HH24:MI'));
EXIT
