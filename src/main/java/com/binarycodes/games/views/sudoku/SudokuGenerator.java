package com.binarycodes.games.views.sudoku;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SudokuGenerator {
    private final int sudokuSize;
    private final int[][] grid;
    
    public static SudokuGenerator for9x9() {
        return new SudokuGenerator(9);
    }
    
    private SudokuGenerator(final int size) {
        if (size != 9) {
            throw new IllegalArgumentException("This generator can only generate sudoku of size 9x9");
        }
        this.sudokuSize = size;
        this.grid = this.generate();
    }
    
    public int get(final int row, final int col) {
        if (this.validCoordinate(row, col)) {
            return this.grid[row][col];
        }
        return -1;
    }
    
    private boolean validCoordinate(final int row, final int col) {
        if (row < 0 || row >= this.sudokuSize || col < 0 || col >= this.sudokuSize) {
            return false;
        }
        return true;
    }
    
    public void printSudoku() {
        for (final int[] row : this.grid) {
            for (final int col : row) {
                System.out.print(col);
            }
            System.out.println();
        }
    }
    
    private SudokuValidator validateSudoku(final int[][] grid) {
        if (grid.length != this.sudokuSize) {
            final var reason = "Incorrect length of grid. Expected %d, got %d".formatted(this.sudokuSize, grid.length);
            return SudokuValidator.invalid(reason);
        }
        
        final int[] referenceArray = IntStream.rangeClosed(1, this.sudokuSize).toArray();
        
        // validate all rows
        for (int row = 0; row < this.sudokuSize; row++) {
            final int[] rowArray = grid[row];
            
            if (!this.arrayEquals(rowArray, referenceArray)) {
                final var reason = "Invalid data in row %d. Expected %s, got %s".formatted(row, Arrays.toString(referenceArray), Arrays.toString(rowArray));
                return SudokuValidator.invalid(reason);
            }
        }
        
        // validate all columns
        for (int col = 0; col < this.sudokuSize; col++) {
            final int column = col;
            final int[] colArray = Arrays.stream(grid).map(arr -> arr[column]).mapToInt(Integer::intValue).toArray();
            
            if (!this.arrayEquals(colArray, referenceArray)) {
                final var reason = "Invalid data in col %d. Expected %s, got %s".formatted(column, Arrays.toString(referenceArray), Arrays.toString(colArray));
                return SudokuValidator.invalid(reason);
            }
        }
        
        // validate all submatrices
        final var subMatrices = this.sudokuInnerSubMatrixArrays();
        for (int sub = 0; sub < this.sudokuSize; sub++) {
            final var subMatrix = subMatrices[sub];
            
            if (!this.arrayEquals(subMatrix, referenceArray)) {
                final var reason = "Invalid data in sub matrix %d. Expected %s, got %s".formatted(sub, Arrays.toString(referenceArray), Arrays.toString(subMatrix));
                return SudokuValidator.invalid(reason);
            }
        }
        return SudokuValidator.valid();
    }
    
    private int[][] sudokuInnerSubMatrixArrays() {
        final int[][] subArrays = new int[9][];
        
        // top three submatrix
        subArrays[0] = this.subMatrixArray(this.grid, 0, 0, 2, 2);
        subArrays[1] = this.subMatrixArray(this.grid, 0, 3, 2, 5);
        subArrays[2] = this.subMatrixArray(this.grid, 0, 6, 2, 8);
        
        // middle three submatrix
        subArrays[3] = this.subMatrixArray(this.grid, 3, 0, 5, 2);
        subArrays[4] = this.subMatrixArray(this.grid, 3, 3, 5, 5);
        subArrays[5] = this.subMatrixArray(this.grid, 3, 6, 5, 8);
        
        // bottom three submatrix
        subArrays[6] = this.subMatrixArray(this.grid, 6, 0, 8, 2);
        subArrays[7] = this.subMatrixArray(this.grid, 6, 3, 8, 5);
        subArrays[8] = this.subMatrixArray(this.grid, 6, 6, 8, 8);
        
        return subArrays;
    }
    
    private int[] subMatrixArray(final int grid[][], final int rowStart, final int colStart, final int rowEnd, final int colEnd) {
        if (!this.validCoordinate(rowStart, colStart) || !this.validCoordinate(rowEnd, colEnd)) {
            return null;
        }
        
        final int numberOfElements = ((colEnd - colStart) + 1) * ((rowEnd - rowStart) + 1);
        if (numberOfElements != this.sudokuSize) {
            return null;
        }
        
        final int[] subArray = new int[this.sudokuSize];
        
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
        final int[][] sudokuGrid = new int[this.sudokuSize][this.sudokuSize];
        
        final var list = IntStream.rangeClosed(1, this.sudokuSize).boxed().collect(Collectors.toList());
        
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
    
    public SudokuValidator isValid() {
        return this.validateSudoku(this.grid);
    }
    
}
