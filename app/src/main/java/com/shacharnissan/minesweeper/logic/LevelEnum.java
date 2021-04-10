package com.shacharnissan.minesweeper.logic;

public enum LevelEnum {
    EASY(3),
    MEDIUM(4),
    HARD(5);

    private final int value;
    LevelEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
