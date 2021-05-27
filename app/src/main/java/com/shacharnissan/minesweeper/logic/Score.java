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

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getTimeScore() {
        return timeScore;
    }

    public void setTimeScore(String timeScore) {
        this.timeScore = timeScore;
    }

    public DifficultyEnum getDifficulty() {
        return difficulty;
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