package com.shacharnissan.minesweeper.logic;

import java.util.Random;

public class Board {
    private DifficultyEnum size;
    private StatusEnum status;
    private Cell[][] cells;

    public Board(DifficultyEnum size) {
        this.size = size;
        this.cells = new Cell[size.getValue()][size.getValue()];
        initCells();
    }

    public Cell getCell(int index){
        int cellJ = index % size.getValue();
        int cellI = index / size.getValue();
        return cells[cellI][cellJ];
    }

    private void initCells() {
        cleanBoard();
        initMines();
    }

    private void initMines() {
//        int Num_of_mines = 1;  // only for test
        int Num_of_mines = (int)size.getValue()-2;
        Random rand = new Random();
        for (int i = 0; i < Num_of_mines; i++) {
            int cellNum;
            int check = 0, cellI, cellJ;
            do {
                cellNum = rand.nextInt(size.getValue()*size.getValue());
                cellJ = cellNum % size.getValue();
                cellI = cellNum / size.getValue();
                check += 1;
                if (check > 10)   //  try 10 times to  find empty cell for insert a mine.
                    throw new RuntimeException("Failed to fill Mines");
            } while (cells[cellI][cellJ].getType() != TypeEnum.NUMBER);

            cells[cellI][cellJ].setType(TypeEnum.MINE);
            increaseMineNeighbors(cellI, cellJ);
        }
    }

    private void increaseMineNeighbors(int cellI, int cellJ) {
        for (int i = cellI - 1; i <= cellI + 1 && i < size.getValue(); i++) {
            if (i < 0)
                continue;
            for (int j = cellJ - 1; j <= cellJ + 1 && j < size.getValue(); j++) {
                if (j < 0)
                    continue;
                if (i == cellI && j == cellJ)
                    continue;
                cells[i][j].increaseMineCounter();
            }
        }
    }

    private void cleanBoard() {
        this.cells = new Cell[size.getValue()][size.getValue()];
        for (int i = 0; i < size.getValue(); i++)
            for (int j = 0; j < size.getValue(); j++)
                cells[i][j] = new Cell();
    }

    public boolean clickCell(int index) {
        int cellJ = index % size.getValue();
        int cellI = index / size.getValue();

        if (cells[cellI][cellJ].isClicked())
            return false;

        cells[cellI][cellJ].setClicked(true);
        cells[cellI][cellJ].setFlaged(false);

        if (cells[cellI][cellJ].getType() == TypeEnum.MINE) {
            cells[cellI][cellJ].setType(TypeEnum.MINE_CLICKED);
            openLose();
            status = StatusEnum.LOSE;
            return true;
        }
        else {
            openNearEmptyCells(cellI, cellJ);
        }

        if(!checkBoard()) {
            status = StatusEnum.CONTINUE;
            return false;
        }

        return true;
    }

    public boolean checkBoard() {
        for (int i = 0; i < size.getValue(); i++)
            for (int j = 0; j < size.getValue(); j++)
                if (cells[i][j].getType() == TypeEnum.NUMBER)
                    if (!cells[i][j].isClicked())
                        return false;

        openWin();
        status = StatusEnum.WIN;
        return true;
    }

    public StatusEnum getStatus(){
        return status;
    }

    private void openNearEmptyCells(int cellI, int cellJ) {
        if (cellI < 0 || cellI > size.getValue() || cellJ < 0 || cellJ > size.getValue())
            return;
        if (cells[cellI][cellJ].getType() != TypeEnum.NUMBER)
            return;

        cells[cellI][cellJ].setClicked(true);

        if (cells[cellI][cellJ].getNearMines() > 0)
            return;

        for (int i = cellI - 1; i <= cellI + 1 && i < size.getValue(); i++) {
            if (i < 0)
                continue;
            for (int j = cellJ - 1; j <= cellJ + 1 && j < size.getValue(); j++) {
                if (j < 0)
                    continue;
                cells[i][j].setClicked(true);
            }
        }
    }

    private void openWin() {
        for (int i = 0; i < size.getValue(); i++) {
            for (int j = 0; j < size.getValue(); j++) {
                if (cells[i][j].getType() == TypeEnum.MINE)
                    cells[i][j].setFlaged(true);
                else if (cells[i][j].isFlaged())
                    cells[i][j].setType(TypeEnum.WRONG_FLAG);
                cells[i][j].setClicked(true);
            }
        }
    }

    private void openLose() {
        for (int i = 0; i < size.getValue(); i++) {
            for (int j = 0; j < size.getValue(); j++) {
                cells[i][j].setClicked(true);
                if (cells[i][j].isFlaged() && cells[i][j].getType() != TypeEnum.MINE)
                    cells[i][j].setType(TypeEnum.WRONG_FLAG);
            }
        }
    }

    public int getBoardSize(){
        return this.size.getValue() * this.size.getValue();
    }

}
