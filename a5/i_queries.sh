#!/bin/bash

# echo exit | source ./sqlplus_l.sh @a_queries.sql

function create_player {
   echo "creating player..."
   ./sqlplus_l.sh @./inserts/i_player.sql
   read -p "Press any key to continue."
   done_message
   pkc
}
function create_team {
   echo "creating team..."
   ./sqlplus_l.sh @./inserts/i_team.sql
   read -p "Press any key to continue."
   done_message
   pkc
}
function create_plays_for {
   echo "creating plays for..."
   ./sqlplus_l.sh @./inserts/i_plays_for.sql
   read -p "Press any key to continue."
   done_message
   pkc
}
function create_season {
   echo "creating season..."
   ./sqlplus_l.sh @./inserts/i_season.sql
   read -p "Press any key to continue."
   done_message
   pkc
}
function create_game {
   echo "creating game..."
   ./sqlplus_l.sh @./inserts/i_game.sql
   read -p "Press any key to continue."
   done_message
   pkc
}
function create_statistic {
   echo "creating statistic..."
   ./sqlplus_l.sh @./inserts/i_statistic.sql
   read -p "Press any key to continue."
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
   echo "Database Utility - Insert Queries"
   tput sgr0

   tput cup 5 0
   # Set reverse video mode
   tput rev
   echo "Main Menu"
   tput sgr0

   tput cup 7 0
   echo "1. Insert Player"

   tput cup 8 0
   echo "2. Insert Team"

   tput cup 9 0
   echo "3. Insert Plays-For Record"

   tput cup 10 0
   echo "4. Insert Season"
   tput cup 11 0
   echo "5. Insert Game"
   echo "6. Insert Statistic"
   echo "b. Back"

   # Set bold mode 
   tput bold
   tput cup 15 0
   read -p "Enter your choice " choice

   tput clear

   case $choice in
      1) create_player
         ;;
      2) create_team
         ;;
      3) create_plays_for
         ;;
      4) create_season
         ;;
      5) create_game
         ;;
      6) create_statistic
         ;;
      b) break
         ;;
      B) break
         ;;
      *) echo Invalid input, heading back to insert menu
         pkc
         continue
         ;;

      esac

   done

   tput cup 3 0

   # read -p "Press any key to quit."

   tput clear
   tput sgr0
   tput rc


