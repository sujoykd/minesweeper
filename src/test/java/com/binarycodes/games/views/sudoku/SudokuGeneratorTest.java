package com.binarycodes.games.views.sudoku;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SudokuGeneratorTest {

    @Test
    void test() {
        final var sudoku = Sudoku9x9.create();
        final var sudokuValidator = sudoku.isValid();
        Assertions.assertNull(sudokuValidator.invalidReason());
        Assertions.assertTrue(sudokuValidator.valid());
    }

}
