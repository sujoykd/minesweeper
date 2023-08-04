package com.binarycodes.games.views.minesweeper;

import com.binarycodes.games.util.CommonGridCell;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.icon.IconFactory;
import com.vaadin.flow.component.icon.VaadinIcon;

public class MineCell extends CommonGridCell {

    public static final IconFactory MINE_ICON = VaadinIcon.BOMB;
    public static final IconFactory FLAG_ICON = VaadinIcon.FLAG_CHECKERED;

    private final boolean hasMine;

    private boolean exploded;
    private boolean flagged;
    private boolean visited;

    public MineCell(final boolean hasMine, final int rowNum, final int colNum) {
        super(rowNum, colNum);

        this.hasMine = hasMine;

        this.exploded = false;
        this.flagged = false;
        this.visited = false;
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

    public void updateNeighbouringMineCount(final long numberOfMines) {
        this.add(new Text(String.valueOf(numberOfMines)));
    }

}
