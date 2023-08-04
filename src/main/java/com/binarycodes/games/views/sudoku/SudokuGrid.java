package com.binarycodes.games.views.sudoku;

import com.binarycodes.games.util.CommonGrid;

public class SudokuGrid extends CommonGrid<SudokuCell> {

    private static final String GRID_BORDER_STYLE = "4px solid green";
    private static final int ROWS = 9;
    private static final int COLUMNS = 9;

    public SudokuGrid() {
        super(ROWS, COLUMNS);

        final var generator = new SudokuGenerator();
        for (final var row : this.grid) {
            for (final var cell : row) {
                cell.enterNumber(generator.get(cell.getRowNum(), cell.getColNum()));

                if (cell.getColNum() % 3 == 2) {
                    cell.getStyle().set("border-right", GRID_BORDER_STYLE);
                }
                if (cell.getColNum() == 0) {
                    cell.getStyle().set("border-left", GRID_BORDER_STYLE);
                }

                if (cell.getRowNum() % 3 == 2) {
                    cell.getStyle().set("border-bottom", GRID_BORDER_STYLE);
                }
                if (cell.getRowNum() == 0) {
                    cell.getStyle().set("border-top", GRID_BORDER_STYLE);
                }
            }
        }
    }

    @Override
    protected void earlyInitialize() {
        // do nothing
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
