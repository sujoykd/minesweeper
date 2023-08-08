package com.binarycodes.games.views.sudoku;

import com.binarycodes.games.util.CommonGrid;

public class SudokuGrid extends CommonGrid<SudokuCell> {

    private static final int SIZE = 9;
    private Sudoku sudoku;

    public SudokuGrid() {
        super(SIZE, SIZE);
    }

    @Override
    protected void earlyInitialize() {
        this.sudoku = Sudoku9x9.create();
    }

    @Override
    protected SudokuCell newCell(final int row, final int col) {
        final var cell = new SudokuCell(row, col);
        cell.enterNumber(this.sudoku.get(cell.getRowNum(), cell.getColNum()));
        return cell;
    }

    @Override
    protected SudokuCell[][] newGrid(final int row, final int col) {
        return new SudokuCell[row][col];
    }

}
