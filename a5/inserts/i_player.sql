ACCEPT i_id NUMBER PROMPT 'Id: '
ACCEPT i_first_name CHAR FORMAT 'A20' prompt 'First Name: '
ACCEPT i_last_name CHAR FORMAT 'A20' prompt 'Last Name: '
INSERT INTO PLAYERS (ID, FIRST_NAME, LAST_NAME) VALUES (&i_id, '&i_first_name', '&i_last_name');
EXIT
