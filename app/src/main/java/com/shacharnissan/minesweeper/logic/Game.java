package com.shacharnissan.minesweeper.logic;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;

public class Game {
    private Board board;
    private Date startTime;
    private DifficultyEnum difficulty;

    public Game(DifficultyEnum difficulty) {
        this.board = new Board(difficulty);
        this.startTime = null;
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

    public boolean clickCell(int x, int y) {
        if (startTime == null) {
            //      DateFormat dateFormat = new SimpleDateFormat("mm:ss");
            this.startTime = new Date();
        }
        return this.board.clickCell(x * this.difficulty.getValue() + y);
    }
}
