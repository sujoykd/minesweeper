package com.binarycodes.games.views.sudoku;

public interface Sudoku {
    int get(final int row, final int col);

    void printSudoku();

    SudokuValidator isValid();
}
