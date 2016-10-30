# Assignment 5
This is the shell script portion of the assignment.

In order for a5.sh to work, make sure that you run `chmod u+x *.sh` in the directory.
Also, you will need to create a file called *sqlplus_l.sh* using the following template:

```bash
#!/bin/bash
sqlplus64 "USERNAME/PASSWORD{q2Cp2N@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(Host=oracle.scs.ryerson.ca)(Port=1521))(CONNECT_DATA=(SID=orcl)))" $1
```

Sub in your OracleDB username and password.
