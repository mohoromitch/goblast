#!/bin/bash

# Used to halt script and let users read messages.
function pkc {
   read -p "Press any key to continue" key
}

function done_message {
   echo "Done."
}

function create_tables {
   echo "creating tables..."
   ./c_tables.sh
   done_message
   pkc
}

function populate_tables {
   echo "populating tables..."
   ./p_tables.sh
   done_message
   pkc
}

function create_views {
   echo "creating views..."
   ./c_views.sh
   done_message
   pkc
}

function advanced_queries {
   echo "running advnaced queries..."
   ./a_queries.sql
   done_message
   pkc
}

function drop_views {
   echo "dropping views..."
   ./d_views.sh
   done_message
   pkc
}

function drop_tables {
   echo "droping all tables..."
   ./d_tables.sh
   done_message
   pkc
}


# Script UI Start

# clear the screen

while true; do

   tput clear

   # Move cursor to screen location X,Y (top left is 0,0)
   tput cup 2 0

   # Set a foreground colour using ANSI escape
   tput setaf 3
   echo "CPS510 - Assignment 5"
   tput cup 3 0
   echo "Database Utility"
   tput sgr0

   tput cup 5 0
   # Set reverse video mode
   tput rev
   echo "Main Menu"
   tput sgr0

   tput cup 7 0
   echo "1. Create Tables"

   tput cup 8 0
   echo "2. Populate Tables"

   tput cup 9 0
   echo "3. Create Views"

   tput cup 10 0
   echo "4. Drop Tables"
   tput cup 11 0
   echo "5. Drop Views"
   echo "6. Advanced Queries"
   echo "q. Quit"

   # Set bold mode 
   tput bold
   tput cup 15 0
   read -p "Enter your choice " choice

   tput clear

   case $choice in
      1) create_tables
         ;;
      2) populate_tables
         ;;
      3) create_views
         ;;
      4) drop_tables
         ;;
      5) drop_views
         ;;
      6) advanced_queries
         ;;
      q) break
         ;;
      Q) break
         ;;
      *) echo Invalid input, heading back to main menu
         pkc
         continue
         ;;

      esac

   done

   tput cup 3 0

   read -p "Press any key to quit."

   tput clear
   tput sgr0
   tput rc


