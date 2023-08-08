package com.binarycodes.games.views.sudoku;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Sudoku9x9 implements Sudoku {
    private static final int SIZE = 9;
    private final int[][] grid;

    public static Sudoku create() {
        return new Sudoku9x9();
    }

    private Sudoku9x9() {
        final var sudokuGrid = this.generate();
        final SudokuValidator validation = this.validateSudoku(sudokuGrid);
        if (validation.valid()) {
            this.grid = sudokuGrid;
        } else {
            throw new RuntimeException(validation.invalidReason());
        }
    }

    @Override
    public int get(final int row, final int col) {
        if (this.validCoordinate(row, col)) {
            return this.grid[row][col];
        }
        return -1;
    }

    private boolean validCoordinate(final int row, final int col) {
        if (row < 0 || row >= SIZE || col < 0 || col >= SIZE) {
            return false;
        }
        return true;
    }

    @Override
    public void printSudoku() {
        for (final int[] row : this.grid) {
            for (final int col : row) {
                System.out.print(col);
            }
            System.out.println();
        }
    }

    private SudokuValidator validateSudoku(final int[][] sudokuGrid) {
        if (sudokuGrid.length != SIZE) {
            final var reason = "Incorrect length of grid. Expected %d, got %d".formatted(SIZE, sudokuGrid.length);
            return SudokuValidator.notOk(reason);
        }

        final int[] referenceArray = IntStream.rangeClosed(1, SIZE).toArray();

        // validate all rows
        for (int row = 0; row < SIZE; row++) {
            final int[] rowArray = sudokuGrid[row];

            if (!this.arrayEquals(rowArray, referenceArray)) {
                final var reason = "Invalid data in row %d. Expected %s, got %s".formatted(row, Arrays.toString(referenceArray), Arrays.toString(rowArray));
                return SudokuValidator.notOk(reason);
            }
        }

        // validate all columns
        for (int col = 0; col < SIZE; col++) {
            final int column = col;
            final int[] colArray = Arrays.stream(sudokuGrid).map(arr -> arr[column]).mapToInt(Integer::intValue).toArray();

            if (!this.arrayEquals(colArray, referenceArray)) {
                final var reason = "Invalid data in col %d. Expected %s, got %s".formatted(column, Arrays.toString(referenceArray), Arrays.toString(colArray));
                return SudokuValidator.notOk(reason);
            }
        }

        // validate all submatrices
        final var subMatrices = this.sudokuInnerSubMatrixArrays(sudokuGrid);
        for (int sub = 0; sub < SIZE; sub++) {
            final var subMatrix = subMatrices[sub];

            if (!this.arrayEquals(subMatrix, referenceArray)) {
                final var reason = "Invalid data in sub matrix %d. Expected %s, got %s".formatted(sub, Arrays.toString(referenceArray), Arrays.toString(subMatrix));
                return SudokuValidator.notOk(reason);
            }
        }
        return SudokuValidator.ok();
    }

    private int[][] sudokuInnerSubMatrixArrays(final int[][] sudokuGrid) {
        final int[][] subArrays = new int[SIZE][];

        // top three submatrix
        subArrays[0] = this.subMatrixArray(sudokuGrid, 0, 0, 2, 2);
        subArrays[1] = this.subMatrixArray(sudokuGrid, 0, 3, 2, 5);
        subArrays[2] = this.subMatrixArray(sudokuGrid, 0, 6, 2, 8);

        // middle three submatrix
        subArrays[3] = this.subMatrixArray(sudokuGrid, 3, 0, 5, 2);
        subArrays[4] = this.subMatrixArray(sudokuGrid, 3, 3, 5, 5);
        subArrays[5] = this.subMatrixArray(sudokuGrid, 3, 6, 5, 8);

        // bottom three submatrix
        subArrays[6] = this.subMatrixArray(sudokuGrid, 6, 0, 8, 2);
        subArrays[7] = this.subMatrixArray(sudokuGrid, 6, 3, 8, 5);
        subArrays[8] = this.subMatrixArray(sudokuGrid, 6, 6, 8, 8);

        return subArrays;
    }

    private int[] subMatrixArray(final int grid[][], final int rowStart, final int colStart, final int rowEnd, final int colEnd) {
        if (!this.validCoordinate(rowStart, colStart) || !this.validCoordinate(rowEnd, colEnd)) {
            return null;
        }

        final int numberOfElements = ((colEnd - colStart) + 1) * ((rowEnd - rowStart) + 1);
        if (numberOfElements != SIZE) {
            return null;
        }

        final int[] subArray = new int[SIZE];

        int count = 0;
        for (int i = rowStart; i <= rowEnd; i++) {
            for (int j = colStart; j <= colEnd; j++) {
                subArray[count++] = grid[i][j];
            }
        }

        return subArray;
    }

    private boolean arrayEquals(final int[] arrayA, final int[] arrayB) {
        if (arrayA == null || arrayB == null) {
            return false;
        }

        if (arrayA.length != arrayB.length) {
            return false;
        }

        final var listA = IntStream.of(arrayA).boxed().collect(Collectors.toList());
        final var listB = IntStream.of(arrayB).boxed().collect(Collectors.toList());
        if (!listA.containsAll(listB)) {
            return false;
        }

        return true;
    }

    private int[][] generate() {
        final int[][] sudokuGrid = new int[SIZE][SIZE];

        final var list = IntStream.rangeClosed(1, SIZE).boxed().collect(Collectors.toList());

        // randomize
        Collections.shuffle(list);
        sudokuGrid[0] = list.stream().mapToInt(Integer::intValue).toArray();

        // shift 3
        Collections.rotate(list, -3);
        sudokuGrid[1] = list.stream().mapToInt(Integer::intValue).toArray();

        // shift 3
        Collections.rotate(list, -3);
        sudokuGrid[2] = list.stream().mapToInt(Integer::intValue).toArray();

        // shift 1
        Collections.rotate(list, -1);
        sudokuGrid[3] = list.stream().mapToInt(Integer::intValue).toArray();

        // shift 3
        Collections.rotate(list, -3);
        sudokuGrid[4] = list.stream().mapToInt(Integer::intValue).toArray();

        // shift 3
        Collections.rotate(list, -3);
        sudokuGrid[5] = list.stream().mapToInt(Integer::intValue).toArray();

        // shift 1
        Collections.rotate(list, -1);
        sudokuGrid[6] = list.stream().mapToInt(Integer::intValue).toArray();

        // shift 3
        Collections.rotate(list, -3);
        sudokuGrid[7] = list.stream().mapToInt(Integer::intValue).toArray();

        // shift 3
        Collections.rotate(list, -3);
        sudokuGrid[8] = list.stream().mapToInt(Integer::intValue).toArray();

        return sudokuGrid;
    }

}
