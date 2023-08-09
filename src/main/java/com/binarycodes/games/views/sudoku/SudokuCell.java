package com.binarycodes.games.views.sudoku;

import com.binarycodes.games.util.CommonGridCell;
import com.vaadin.flow.component.Text;

public class SudokuCell extends CommonGridCell {
    private static final String GRID_BORDER_STYLE = "4px solid green";

    public SudokuCell(final int rowNum, final int colNum) {
        super(rowNum, colNum);
    }

    public void enterNumber(final int number) {
        this.add(new Text(String.valueOf(number)));
    }

    @Override
    protected void style() {
        super.style();

        if (this.getColNum() % 3 == 2) {
            this.getStyle().set("border-right", GRID_BORDER_STYLE);
        }
        if (this.getColNum() == 0) {
            this.getStyle().set("border-left", GRID_BORDER_STYLE);
        }

        if (this.getRowNum() % 3 == 2) {
            this.getStyle().set("border-bottom", GRID_BORDER_STYLE);
        }
        if (this.getRowNum() == 0) {
            this.getStyle().set("border-top", GRID_BORDER_STYLE);
        }
    }

}
