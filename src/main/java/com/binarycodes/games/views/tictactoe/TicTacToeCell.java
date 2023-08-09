package com.binarycodes.games.views.tictactoe;

import com.binarycodes.games.util.CommonGridCell;
import com.vaadin.flow.component.Text;

public class TicTacToeCell extends CommonGridCell {
    private static final String SIZE = "60px";
    private static final String GRID_BORDER_STYLE = "4px solid green";

    private boolean visited;
    private char marking;

    public TicTacToeCell(final int rowNum, final int colNum) {
        super(rowNum, colNum);
        this.visited = false;
    }

    public void enterNumber(final int number) {
        this.add(new Text(String.valueOf(number)));
    }

    @Override
    protected void style() {
        this.size(SIZE, SIZE);

        if (this.getColNum() != 0) {
            this.getStyle().set("border-left", GRID_BORDER_STYLE);
        }

        if (this.getRowNum() != 0) {
            this.getStyle().set("border-top", GRID_BORDER_STYLE);
        }
    }

    public boolean mark(final char markingChar) {
        if (!this.visited) {
            this.marking = markingChar;
            this.add(String.valueOf(markingChar));
            this.visited = true;

            return true;
        }
        return false;
    }

    public boolean isVisited() {
        return this.visited;
    }

    public char getMarking() {
        return this.marking;
    }

    public void markComplete() {
        this.getStyle().setColor("green");
        this.getStyle().set("font-weight", "bold");
    }

}
