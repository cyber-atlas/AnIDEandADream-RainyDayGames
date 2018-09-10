package edu.iastate.cs309.parlorgames;

import java.sql.Date;

public class Utility {
    enum Games
    {
        TIC_TAC_TOE, BATTLESHIP, CONNECT_FOUR, SNAKE;
    }

    public class Score
    {
        Long user_id;
        String username;
        Utility.Games gameName;
        Integer score;
        Date date;
        public void Score(Long id, String username, Utility.Games gameName, Integer score, Date date)
        {
            this.user_id = id;
            this.username = username;
            this.gameName = gameName;
            this.score = score;
            this.date = date;
        }

        public boolean uploadScore()
        {
            //todo interface with database
            return false;
        }
    }
}


