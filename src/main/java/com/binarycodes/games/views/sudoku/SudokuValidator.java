package com.binarycodes.games.views.sudoku;

public record SudokuValidator(boolean valid, String invalidReason) {
    public static SudokuValidator notOk(final String invalidReason) {
        return new SudokuValidator(false, invalidReason);
    }

    public static SudokuValidator ok() {
        return new SudokuValidator(true, null);
    }

}
