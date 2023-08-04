package com.binarycodes.games.views.minesweeper;

import java.util.Objects;

import org.vaadin.lineawesome.LineAwesomeIcon;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.theme.lumo.LumoUtility.AlignItems;
import com.vaadin.flow.theme.lumo.LumoUtility.Border;
import com.vaadin.flow.theme.lumo.LumoUtility.Display;
import com.vaadin.flow.theme.lumo.LumoUtility.JustifyContent;

public class MineCell extends Div {

    public static final LineAwesomeIcon MINE_ICON = LineAwesomeIcon.BOMB_SOLID;
    public static final LineAwesomeIcon FLAG_ICON = LineAwesomeIcon.FLAG_CHECKERED_SOLID;
    private static final String SIZE = "35px";

    private final boolean hasMine;
    private final int rowNum;
    private final int colNum;

    private boolean exploded;
    private boolean flagged;
    private boolean visited;

    public MineCell(final boolean hasMine, final int rowNum, final int colNum) {
        this.hasMine = hasMine;
        this.rowNum = rowNum;
        this.colNum = colNum;

        this.exploded = false;
        this.flagged = false;
        this.visited = false;

        this.getStyle().setHeight(SIZE);
        this.getStyle().setWidth(SIZE);
        this.getStyle().setCursor("pointer");
        this.addClassNames(Border.ALL, Display.FLEX, JustifyContent.CENTER, AlignItems.CENTER);
    }

    public void hit(final boolean fromGamePlay) {
        this.visited = true;
        if (this.hasMine) {
            if (this.flagged) {
                this.removeAll();
                this.addMineWithColor("green");
            }

            if (!this.exploded && !this.flagged) {
                this.exploded = true;
                this.addMineWithColor("red");
                if (fromGamePlay) {
                    this.getStyle().setBackground("black");
                }
            }

        } else if (fromGamePlay) {
            this.getStyle().setBackground("gray");
        }
    }

    private void addMineWithColor(final String color) {
        final var mine = MINE_ICON.create();
        mine.getStyle().setColor(color);
        this.add(mine);
    }

    public boolean toggleFlag() {
        if (!this.exploded) {
            if (!this.flagged) {
                this.flagged = true;
                final var mine = FLAG_ICON.create();
                mine.getStyle().setColor("red");
                this.add(mine);
            } else {
                this.flagged = false;
                this.removeAll();
            }
        }
        return this.flagged;
    }

    public boolean isExploded() {
        return this.exploded;
    }

    public boolean isFlagged() {
        return this.flagged;
    }

    public boolean isHasMine() {
        return this.hasMine;
    }

    public boolean isVisited() {
        return this.visited;
    }

    public int getRowNum() {
        return this.rowNum;
    }

    public int getColNum() {
        return this.colNum;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.colNum, this.rowNum);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof final MineCell other)) {
            return false;
        }
        return this.colNum == other.colNum && this.rowNum == other.rowNum;
    }

    public void updateNeighbouringMineCount(final long numberOfMines) {
        this.add(new Text(String.valueOf(numberOfMines)));
    }

    public void markToDebug() {
        this.getStyle().setBackground("yellow");
    }

}
