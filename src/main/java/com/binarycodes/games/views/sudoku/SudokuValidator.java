package com.binarycodes.games.views.sudoku;

public class SudokuValidator {
    private final boolean valid;
    private final String invalidReason;

    public static SudokuValidator valid() {
        return new SudokuValidator(true, null);
    }

    public static SudokuValidator invalid(final String reason) {
        return new SudokuValidator(false, reason);
    }

    private SudokuValidator(final boolean valid, final String errorReason) {
        this.valid = valid;
        this.invalidReason = errorReason;
    }

    public boolean isValid() {
        return this.valid;
    }

    public String getInvalidReason() {
        return this.invalidReason;
    }

}
