package com.binarycodes.games.views.sudoku;

import com.binarycodes.games.util.CommonGridCell;
import com.vaadin.flow.component.Text;

public class SudokuCell extends CommonGridCell {

    public SudokuCell(final int rowNum, final int colNum) {
        super(rowNum, colNum);
    }

    public void enterNumber(final int number) {
        this.add(new Text(String.valueOf(number)));
    }

}
