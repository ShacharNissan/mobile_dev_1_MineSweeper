package com.shacharnissan.minesweeper.logic;

public enum DifficultyEnum {
    EASY(4),
    MEDIUM(6),
    HARD(8);

    private final int value;
    DifficultyEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
