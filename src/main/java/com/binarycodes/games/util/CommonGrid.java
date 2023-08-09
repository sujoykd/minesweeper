package com.binarycodes.games.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility.Border;
import com.vaadin.flow.theme.lumo.LumoUtility.Width;

public abstract class CommonGrid<T extends CommonGridCell> extends VerticalLayout {

    protected final T[][] grid;
    private final int rows;
    private final int columns;

    public CommonGrid(final int rows, final int columns, final boolean hasBorder) {
        this.rows = rows;
        this.columns = columns;
        this.earlyInitialize();
        this.grid = this.newGrid(rows, columns);
        this.createGridStructure(hasBorder);
    }

    protected abstract void earlyInitialize();

    private void createGridStructure(final boolean hasBorder) {
        final var verticalLayout = new VerticalLayout();
        verticalLayout.getStyle().setWidth("null");
        verticalLayout.addClassNames(Width.AUTO);
        verticalLayout.addClassNames(hasBorder ? Border.ALL : Border.NONE);
        verticalLayout.setPadding(false);
        verticalLayout.setSpacing(false);

        for (int row = 0; row < this.rows; row++) {
            final var horizontalLayout = new HorizontalLayout();
            horizontalLayout.setPadding(false);
            horizontalLayout.setSpacing(false);

            for (int col = 0; col < this.columns; col++) {

                this.grid[row][col] = this.newCell(row, col);
                horizontalLayout.add(this.grid[row][col]);
            }
            verticalLayout.add(horizontalLayout);
        }
        this.add(verticalLayout);
    }

    protected abstract T newCell(int row, int col);

    protected abstract T[][] newGrid(int row, int col);

    protected List<T> findAdjacentCells(final T cell) {
        final int row = cell.getRowNum();
        final int col = cell.getColNum();

        final var adjacentCells = new ArrayList<T>();
        adjacentCells.add(this.getCellIfValid(row - 1, col - 1));
        adjacentCells.add(this.getCellIfValid(row - 1, col));
        adjacentCells.add(this.getCellIfValid(row - 1, col + 1));
        adjacentCells.add(this.getCellIfValid(row, col - 1));
        adjacentCells.add(this.getCellIfValid(row, col + 1));
        adjacentCells.add(this.getCellIfValid(row + 1, col - 1));
        adjacentCells.add(this.getCellIfValid(row + 1, col));
        adjacentCells.add(this.getCellIfValid(row + 1, col + 1));

        adjacentCells.removeIf(Objects::isNull);
        return adjacentCells;
    }

    private T getCellIfValid(final int row, final int col) {
        if (row >= 0 && row < this.rows && col >= 0 && col < this.columns) {
            return this.grid[row][col];
        }
        return null;
    }

    protected List<List<T>> allLinesPassingThrough(final T cell) {
        final int cellRow = cell.getRowNum();
        final int cellCol = cell.getColNum();

        final List<T> vertical = List.of(this.grid).stream().map(arr -> arr[cellCol]).toList();
        final List<T> horizontal = List.of(this.grid[cellRow]);

        final List<T> leftDiagonal = new ArrayList<>();
        final List<T> rightDiagonal = new ArrayList<>();

        for (int rowNum = 0; rowNum < this.rows; rowNum++) {
            final int rowDiff = Math.abs(rowNum - cellRow);

            final int leftDiagonalCol = rowNum <= cellRow ? cellCol - rowDiff : cellCol + rowDiff;
            final int rightDiagonalCol = rowNum <= cellRow ? cellCol + rowDiff : cellCol - rowDiff;

            leftDiagonal.add(this.getCellIfValid(rowNum, leftDiagonalCol));
            rightDiagonal.add(this.getCellIfValid(rowNum, rightDiagonalCol));
        }

        leftDiagonal.removeIf(Objects::isNull);
        rightDiagonal.removeIf(Objects::isNull);

        return List.of(vertical, horizontal, rightDiagonal, leftDiagonal);
    }

}
