package com.shacharnissan.minesweeper.logic;

import java.util.ArrayList;
import java.util.Date;


public class Game {
    public static long EASY_BEST_TIME = Long.MAX_VALUE;
    public static long MEDIUM_BEST_TIME = Long.MAX_VALUE;
    public static long HARD_BEST_TIME = Long.MAX_VALUE;

    // these lists are save the result in millisecond:
    public static ArrayList<Long> best10resultsEasy = new ArrayList<>();
    public static ArrayList<Long> best10resultsMedium = new ArrayList<>();
    public static ArrayList<Long> best10resultsHard = new ArrayList<>();

    public final static int NUM_OF_RESULTS = 10;

    private Board board;
    private Date startTime;
    private long resultTime;
    private DifficultyEnum difficulty;
    private boolean isStarted;

    public Game(DifficultyEnum difficulty) {
        this.board = new Board(difficulty);
        this.difficulty = difficulty;
        this.startTime = null;
        this.isStarted = false;
        initScoresLists();
    }

    private void initScoresLists() {
        int i;
        for (i = 0; i<NUM_OF_RESULTS; i++) {
            best10resultsEasy.add(Long.MAX_VALUE);
            best10resultsMedium.add(Long.MAX_VALUE);
            best10resultsHard.add(Long.MAX_VALUE);
        }
    }

    private void saveNewRecord(){
        resultTime = (new Date().getTime()) - startTime.getTime();
        switch (difficulty){
            case EASY:
                if (EASY_BEST_TIME > resultTime) {
                    EASY_BEST_TIME = resultTime;
                }
                break;
            case MEDIUM:
                if (MEDIUM_BEST_TIME > resultTime)
                    MEDIUM_BEST_TIME = resultTime;
                break;
            case HARD:
                if (HARD_BEST_TIME > resultTime)
                    HARD_BEST_TIME = resultTime;
                break;
        }
        checkIfIn10BestScoresAndSave(resultTime, difficulty);
    }

    void checkIfIn10BestScoresAndSave(long resultTime, DifficultyEnum difficulty){
        int i;
        switch (difficulty){
            case EASY:
                for (i=0;i<NUM_OF_RESULTS;i++){
                    if (resultTime < best10resultsEasy.get(i)) {
                        best10resultsEasy.add(i, resultTime);
                        best10resultsEasy.remove(10);  // drop last result
                        break;  // stop to search in the scores list
                    }
                    // else -> the for loop keeps search if the new result is in the best 10.
                }
                break;
            case MEDIUM:
                for (i=0;i<NUM_OF_RESULTS;i++){
                    if (resultTime < best10resultsMedium.get(i)) {
                        best10resultsMedium.add(i, resultTime);
                        best10resultsMedium.remove(10);  // drop last result
                        break;  // stop to search in the scores list
                    }
                    // else -> the for loop keeps search if the new result is in the best 10.
                }
                break;
            case HARD:
                for (i=0;i<NUM_OF_RESULTS;i++){
                    if (resultTime < best10resultsHard.get(i)) {
                        best10resultsHard.add(i, resultTime);
                        best10resultsHard.remove(10);  // drop last result
                        break;  // stop to search in the scores list
                    }
                    // else -> the for loop keeps search if the new result is in the best 10.
                }
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

    public StatusEnum getGameStatus() {
        return null;
        //return this
    }

    public void newGame() {
        this.board = new Board(difficulty);
    }

    public boolean clickCell(int index) {
        if (startTime == null) {
            this.startTime = new Date();
            this.isStarted = true;
        }
        if (this.board.clickCell(index))  // true - the game is over ; false - the game is not over
        {
            if (getBoard().getStatus() == StatusEnum.WIN)
                saveNewRecord();
            return true;
        }
        return false;
    }

    public Board getBoard() {
        return board;
    }

    public boolean getIsStarted()
    {
        return isStarted;
    }

}