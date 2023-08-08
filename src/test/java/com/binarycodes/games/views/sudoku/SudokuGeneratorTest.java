package com.binarycodes.games.views.sudoku;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SudokuGeneratorTest {

    @Test
    void test() {
        try {
            Sudoku9x9.create();
        } catch (final RuntimeException ex) {
            Assertions.fail(ex.getMessage());
        }
    }

}
