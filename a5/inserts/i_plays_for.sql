ACCEPT i_player_id NUMBER PROMPT 'Player Id: '
ACCEPT i_season_id NUMBER PROMPT 'Season Id: '
ACCEPT i_team_id NUMBER PROMPT 'Team Id: '
INSERT INTO PLAYS_FOR (PLAYER_ID, SEASON_ID, TEAM_ID)  VALUES (&i_player_id, &i_season_id, &i_team_id);
EXIT;
