package com.binarycodes.games.views.sudoku;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SudokuGeneratorTest {

    @Test
    void test() {
        final var sudoku = SudokuGenerator.for9x9();
        final var sudokuValidator = sudoku.isValid();
        Assertions.assertNull(sudokuValidator.getInvalidReason());
        Assertions.assertTrue(sudokuValidator.isValid());
    }

}
