package com.binarycodes.games.views.sudoku;

import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SudokuGenerator {
    private final int[][] grid;

    public SudokuGenerator() {
        this.grid = new int[9][9];
        this.generate();
    }

    public int get(final int row, final int col) {
        if (row < 0 || row >= 9 || col < 0 || col >= 9) {
            return -1;
        }
        return this.grid[row][col];
    }

    public void printSudoku() {
        for (final int[] row : this.grid) {
            for (final int col : row) {
                System.out.print(col + " ");
            }
            System.out.println();
        }
    }

    public void generate() {
        final var list = IntStream.rangeClosed(1, 9).boxed().collect(Collectors.toList());

        // randomize
        Collections.shuffle(list);
        for (int i = 0; i < 9; i++) {
            this.grid[0][i] = list.get(i);
        }

        // shift 3
        Collections.rotate(list, -3);
        for (int i = 0; i < 9; i++) {
            this.grid[1][i] = list.get(i);
        }

        // shift 3
        Collections.rotate(list, -3);
        for (int i = 0; i < 9; i++) {
            this.grid[2][i] = list.get(i);
        }

        // shift 1
        Collections.rotate(list, -1);
        for (int i = 0; i < 9; i++) {
            this.grid[3][i] = list.get(i);
        }

        // shift 3
        Collections.rotate(list, -3);
        for (int i = 0; i < 9; i++) {
            this.grid[4][i] = list.get(i);
        }

        // shift 3
        Collections.rotate(list, -3);
        for (int i = 0; i < 9; i++) {
            this.grid[5][i] = list.get(i);
        }

        // shift 1
        Collections.rotate(list, -1);
        for (int i = 0; i < 9; i++) {
            this.grid[6][i] = list.get(i);
        }

        // shift 3
        Collections.rotate(list, -3);
        for (int i = 0; i < 9; i++) {
            this.grid[7][i] = list.get(i);
        }

        // shift 3
        Collections.rotate(list, -3);
        for (int i = 0; i < 9; i++) {
            this.grid[8][i] = list.get(i);
        }
    }

}
