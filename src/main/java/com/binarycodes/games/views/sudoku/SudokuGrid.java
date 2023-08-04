package com.binarycodes.games.views.sudoku;

import com.binarycodes.games.util.CommonGrid;

public class SudokuGrid extends CommonGrid<SudokuCell> {

    private static final int ROWS = 9;
    private static final int COLUMNS = 9;

    public SudokuGrid() {
        super(ROWS, COLUMNS);
    }

    @Override
    protected SudokuCell newCell(final int row, final int col) {
        return new SudokuCell(row, col);
    }

    @Override
    protected SudokuCell[][] newGrid(final int row, final int col) {
        return new SudokuCell[row][col];
    }

}
