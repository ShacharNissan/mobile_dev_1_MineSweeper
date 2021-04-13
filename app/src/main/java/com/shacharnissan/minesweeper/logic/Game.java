package com.shacharnissan.minesweeper.logic;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;

public class Game {
    public static long EASY_BEST_TIME = 0;
    public static long MEDIUM_BEST_TIME = 0;
    public static long HARD_BEST_TIME = 0;
    private Board board;
    private Date startTime;
    private long resultTime;
    private DifficultyEnum difficulty;

    public Game(DifficultyEnum difficulty) {
        this.board = new Board(difficulty);
        this.difficulty = difficulty;
        this.startTime = null;
    }
    private void gameEnd(){
        resultTime = (new Date().getTime()) - startTime.getTime();
        switch (difficulty){
            case EASY:
                if (EASY_BEST_TIME < resultTime) {
                    EASY_BEST_TIME = resultTime;
                }
                break;
            case MEDIUM:
                if (MEDIUM_BEST_TIME < resultTime)
                    MEDIUM_BEST_TIME = resultTime;
                break;
            case HARD:
                if (HARD_BEST_TIME < resultTime)
                    HARD_BEST_TIME = resultTime;
                break;
        }
    }
    public long getResultTime(){
        return resultTime;
    }

    public DifficultyEnum getDifficulty(){
        return this.difficulty;
    }
    public Cell getCell(int index){
        return this.board.getCell(index);
    }

    public long getTime() {
        return (new Date()).getTime() - this.startTime.getTime();
    }

    public int[] getTimeInMinutes() {
        return null;
        //return new int[]{this.timer / 60 , this.timer % 60 };
    }

    public StatusEnum getGameStatus() {
        return null;
        //return this
    }

    public void newGame() {
        this.board = new Board(difficulty);
    }

    public boolean clickCell(int index) {
        if (startTime == null) {
            //      DateFormat dateFormat = new SimpleDateFormat("mm:ss");
            this.startTime = new Date();
        }
        if (this.board.clickCell(index)) {
            gameEnd();
            return true;
        }
        return false;
    }

    public Board getBoard() {
        return board;
    }
}
