package com.binarycodes.games.views.minesweeper;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Objects;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility.AlignItems;
import com.vaadin.flow.theme.lumo.LumoUtility.Border;
import com.vaadin.flow.theme.lumo.LumoUtility.Display;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import com.vaadin.flow.theme.lumo.LumoUtility.JustifyContent;
import com.vaadin.flow.theme.lumo.LumoUtility.Width;

public class MineGrid extends VerticalLayout {

    private static final SecureRandom RANDOM = new SecureRandom();

    private static final int ROWS = 10;
    private static final int COLUMNS = 10;
    private static final int MAX_MINES = RANDOM.nextInt(15, 25);

    private static final MineCell[][] GRID = new MineCell[ROWS][COLUMNS];

    private int mineCounter;
    private int flagCounter;

    private Text flagText;

    public MineGrid() {
        this.mineCounter = 0;
        this.flagCounter = 0;

        this.createMineGrid();
        this.add(this.statistics());
    }

    private HorizontalLayout statistics() {
        final var stats = new HorizontalLayout();
        stats.addClassNames(Gap.XLARGE);

        final var mineStats = new Div();
        mineStats.add(MineCell.MINE_ICON.create(), new Text(" - " + this.mineCounter));
        mineStats.addClassNames(Display.FLEX, JustifyContent.CENTER, AlignItems.CENTER);

        final var flagStats = new Div();
        this.flagText = new Text("");
        this.updateFlagText();
        flagStats.add(MineCell.FLAG_ICON.create(), this.flagText);
        flagStats.addClassNames(Display.FLEX, JustifyContent.CENTER, AlignItems.CENTER);

        final var showAllMinesBtn = new Button("Show All Mines", event -> {
            this.showAllMines();
        });

        stats.add(mineStats, flagStats, showAllMinesBtn);
        return stats;
    }

    private void updateFlagText() {
        this.flagText.setText(" - " + this.flagCounter);
    }

    private void createMineGrid() {
        final var verticalLayout = new VerticalLayout();
        verticalLayout.getStyle().setWidth("null");
        verticalLayout.addClassNames(Border.ALL, Width.AUTO);
        verticalLayout.setPadding(false);
        verticalLayout.setSpacing(false);

        for (int row = 0; row < ROWS; row++) {
            final var horizontalLayout = new HorizontalLayout();
            horizontalLayout.setPadding(false);
            horizontalLayout.setSpacing(false);

            for (int col = 0; col < COLUMNS; col++) {
                final var hasMine = this.mineCounter < MAX_MINES ? RANDOM.nextFloat(1f) < 0.2f : false;
                if (hasMine) {
                    this.mineCounter++;
                }
                GRID[row][col] = new MineCell(hasMine, row, col);
                GRID[row][col].addClickListener(this.cellClickListener());
                horizontalLayout.add(GRID[row][col]);
            }
            verticalLayout.add(horizontalLayout);
        }
        this.add(verticalLayout);
    }

    private void showAllMines() {
        for (final var row : GRID) {
            for (final var cell : row) {
                cell.hit(false);
            }
        }
        this.setEnabled(false);
    }

    private ComponentEventListener<ClickEvent<Div>> cellClickListener() {
        return event -> {
            final var cell = (MineCell) event.getSource();
            this.processClickOnMineCell(cell, event.isCtrlKey());
        };
    }

    private void processClickOnMineCell(final MineCell cell, final boolean toggleFlagMark) {
        // do nothing if cell is already exploded or visited or if it is flagged and we
        // are not trying to toggle the flag
        if (cell.isExploded() || (cell.isFlagged() && !toggleFlagMark) || cell.isVisited()) {
            return;
        }

        if (toggleFlagMark) {
            final var flagged = cell.toggleFlag();
            if (flagged) {
                this.flagCounter++;
            } else {
                this.flagCounter--;
            }
            this.updateFlagText();
        } else {
            cell.hit(true);
            if (cell.isExploded()) {
                this.showAllMines();
            } else {
                this.findMinesInAdjacentCells(cell);
            }
        }

    }

    private void findMinesInAdjacentCells(final MineCell cell) {
        final int row = cell.getRowNum();
        final int col = cell.getColNum();

        final var adjacentCells = new ArrayList<MineCell>();
        adjacentCells.add(this.getMineCellIfValid(row - 1, col - 1));
        adjacentCells.add(this.getMineCellIfValid(row - 1, col));
        adjacentCells.add(this.getMineCellIfValid(row - 1, col + 1));
        adjacentCells.add(this.getMineCellIfValid(row, col - 1));
        adjacentCells.add(this.getMineCellIfValid(row, col + 1));
        adjacentCells.add(this.getMineCellIfValid(row + 1, col - 1));
        adjacentCells.add(this.getMineCellIfValid(row + 1, col));
        adjacentCells.add(this.getMineCellIfValid(row + 1, col + 1));

        adjacentCells.removeIf(Objects::isNull);

        final var numberOfMines = adjacentCells.stream().filter(MineCell::isHasMine).count();
        if (numberOfMines == 0) {
            adjacentCells.stream().filter(c -> !c.isVisited()).forEach(c -> this.processClickOnMineCell(c, false));
        } else {
            cell.updateNeighbouringMineCount(numberOfMines);
        }
    }

    private MineCell getMineCellIfValid(final int row, final int col) {
        if (row >= 0 && row < ROWS && col >= 0 && col < COLUMNS) {
            return GRID[row][col];
        }
        return null;
    }

}
