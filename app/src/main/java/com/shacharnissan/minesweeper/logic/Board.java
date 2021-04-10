package com.shacharnissan.minesweeper.logic;

import java.util.Random;

public class Board {
    private LevelEnum size;
    private Cell[][] cells;

    public Board(LevelEnum size) {
        this.size = size;
        this.cells = new Cell[size.getValue()][size.getValue()];
        initCells();
    }

    private void initCells() {
        cleanBoard();
        initMines();
    }

    private void initMines() {
        int Num_of_mines = (int) Math.sqrt(size.getValue());
        Random rand = new Random();
        for (int i = 0; i < Num_of_mines; i++) {
            int cellNum;
            int check = 0, cellI, cellJ;
            do {
                cellNum = rand.nextInt(size.getValue());
                cellJ = cellNum % size.getValue();
                cellI = cellNum / size.getValue();
                check += 1;
                if (check < 10)
                    throw new RuntimeException("Failed to fill Mines");
            } while (cells[cellI][cellJ].getType() == TypeEnum.NUMBER);

            cells[cellI][cellJ].setType(TypeEnum.MINE);
            increaseMineNeighbors(cellI, cellJ);
        }
    }

    private void increaseMineNeighbors(int cellI, int cellJ) {
        for (int i = cellI - 1; i <= cellI + 1 && i < size.getValue(); i++) {
            if (i < 0)
                i = 0;
            for (int j = cellJ - 1; j < cellJ + 1 & j < size.getValue(); j++) {
                if (j < 0)
                    j = 0;
                if (i == cellI && j == cellJ)
                    continue;
                cells[i][j].setNearMines(cells[i][j].getNearMines() + 1);
            }
        }
    }

    private void cleanBoard() {
        this.cells = new Cell[size.getValue()][size.getValue()];
    }

    public boolean clickCell(int index) {
        int cellJ = index % size.getValue();
        int cellI = index / size.getValue();

        if (cells[cellI][cellJ].isClicked())
            return false;

        if (cells[cellI][cellJ].isFlaged())
            return false;

        cells[cellI][cellJ].setClicked(true);
        if (cells[cellI][cellJ].getType() == TypeEnum.MINE) {
            cells[cellI][cellJ].setType(TypeEnum.MINE_CLICKED);
            openLose();
        }

        openNearEmptyCells(cellI, cellJ);
        checkBoard(index);

        return true;
    }

    public boolean checkBoard(int index) {
        int cellJ = index % size.getValue();
        int cellI = index / size.getValue();

        for (int i = 0; i < size.getValue(); i++)
            for (int j = 0; j < size.getValue(); j++)
                if (cells[i][j].getType() == TypeEnum.NUMBER)
                    if (!cells[i][j].isClicked())
                        return false;

        openWin();
        return true;
    }

    private void openNearEmptyCells(int cellI, int cellJ) {
        if (cellI < 0 || cellI > size.getValue() || cellJ < 0 || cellJ > size.getValue())
            return;
        if (cells[cellI][cellJ].getType() != TypeEnum.NUMBER)
            return;

        cells[cellI][cellJ].setClicked(true);
        openNearEmptyCells(cellI - 1, cellJ - 1);
        openNearEmptyCells(cellI - 1, cellJ);
        openNearEmptyCells(cellI - 1, cellJ + 1);
        openNearEmptyCells(cellI, cellJ - 1);
        openNearEmptyCells(cellI, cellJ + 1);
        openNearEmptyCells(cellI + 1, cellJ - 1);
        openNearEmptyCells(cellI + 1, cellJ);
        openNearEmptyCells(cellI + 1, cellJ + 1);
    }

    private void openWin() {
        for (int i = 0; i < size.getValue(); i++) {
            for (int j = 0; j < size.getValue(); j++) {
                if (cells[i][j].getType() == TypeEnum.MINE)
                    cells[i][j].setFlaged(true);
                else if (cells[i][j].isFlaged())
                    cells[i][j].setType(TypeEnum.WRONG_FLAG);
            }
        }
    }

    private void openLose() {
        for (int i = 0; i < size.getValue(); i++) {
            for (int j = 0; j < size.getValue(); j++) {
                if (!cells[i][j].isFlaged())
                    cells[i][j].setClicked(true);
                else if (cells[i][j].getType() != TypeEnum.MINE)
                    cells[i][j].setType(TypeEnum.WRONG_FLAG);
            }
        }
    }

}
