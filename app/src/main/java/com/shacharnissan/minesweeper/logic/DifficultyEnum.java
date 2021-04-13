package com.shacharnissan.minesweeper.logic;

public enum DifficultyEnum {
    EASY(3),
    MEDIUM(4),
    HARD(5);

    private final int value;
    DifficultyEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
