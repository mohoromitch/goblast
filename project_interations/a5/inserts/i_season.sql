ACCEPT i_id NUMBER PROMPT 'Id: '
ACCEPT i_year NUMBER PROMPT 'Year (yyyy): '
ACCEPT i_type CHAR FORMAT 'A20' prompt 'Season Type (regular or post): '
INSERT INTO SEASONS (ID, YEAR, TYPE) VALUES (&i_id, &i_year, '&i_type');
EXIT
