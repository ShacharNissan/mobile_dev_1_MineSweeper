package com.shacharnissan.minesweeper.logic;

public class Cell {
    private TypeEnum type;
    private int nearMines;
    private boolean isClicked;
    private boolean isFlaged;

    public Cell(){
        this.type = TypeEnum.NUMBER;
        this.nearMines = 0;
        this.isClicked = false;
        this.isFlaged = false;
    }

    public TypeEnum getType() {
        return type;
    }

    public void setType(TypeEnum type) {
        this.type = type;
    }

    public boolean isClicked() {
        return isClicked;
    }

    public void setClicked(boolean clicked) {
        if(isFlaged)
            return;
        isClicked = clicked;
    }

    public boolean isFlaged() {
        return isFlaged;
    }

    public void setFlaged(boolean flaged) {
        if(isClicked)
            return;
        isFlaged = flaged;
    }

    public int getNearMines() {
        return nearMines;
    }

    public void setNearMines(int nearMines) {
        this.nearMines = nearMines;
    }

    public void increaseMineCounter(){
        this.nearMines++;
    }
}
