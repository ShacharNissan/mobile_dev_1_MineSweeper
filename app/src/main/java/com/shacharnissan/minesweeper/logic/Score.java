package com.shacharnissan.minesweeper.logic;

public class Score {
    private String place;
    private String timeScore;
    private DifficultyEnum difficulty;

    public Score (String place, String timeScore, DifficultyEnum difficulty){
        this.place = place;
        this.timeScore = timeScore;
        this.difficulty = difficulty;
    }

    public Score (String place, String timeScore, String difficulty){
        this.place = place;
        this.timeScore = timeScore;
        this.difficulty = DifficultyEnum.valueOf(difficulty);
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getTimeScore() {
        return timeScore;
    }

    public Long convertStringTimeToLongMillisecondTime() {
        Long longTimeMillisecond;
        String min = this.timeScore.split(":")[0];
        String sec = this.timeScore.split(":")[1];
        longTimeMillisecond = 1000*(Long.parseLong(min)*60 + Long.parseLong(sec));

        longTimeMillisecond = checkIfTheLongNumerIsZero(longTimeMillisecond);

        return longTimeMillisecond;
    }

    private Long checkIfTheLongNumerIsZero(Long longTime) {
        // if longTime is 0 , it means there is no new result.
        if (longTime == 0)
            return Long.MAX_VALUE; // save max value for the algorithm of "save the minimum result"
        else
            return longTime;
    }


    public void setTimeScore(String timeScore) {
        this.timeScore = timeScore;
    }

    public DifficultyEnum getDifficulty() {
        return difficulty;
    }

    public String getStringDifficulty() {
        return "" + difficulty;
    }

    public void setDifficulty(DifficultyEnum difficulty) {
        this.difficulty = difficulty;
    }

    @Override
    public String toString() {
        return "Score{" +
                "place='" + place + '\'' +
                ", timeScore=" + timeScore +
                ", difficulty=" + difficulty +
                '}';
    }
}