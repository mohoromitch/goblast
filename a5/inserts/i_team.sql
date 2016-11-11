
ACCEPT i_id NUMBER PROMPT 'Id: '
ACCEPT i_name CHAR FORMAT 'A20' prompt 'Team Name: '
INSERT INTO TEAMS (ID, NAME) VALUES (&i_id, '&i_name');
EXIT
