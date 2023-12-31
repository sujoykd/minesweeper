package com.binarycodes.games.views.minesweeper;

import java.security.SecureRandom;

import com.binarycodes.games.util.CommonGrid;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility.AlignItems;
import com.vaadin.flow.theme.lumo.LumoUtility.Display;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import com.vaadin.flow.theme.lumo.LumoUtility.JustifyContent;

public class MineGrid extends CommonGrid<MineCell> {

    private static final SecureRandom RANDOM = new SecureRandom();

    private static final int ROWS = 10;
    private static final int COLUMNS = 10;
    private static final int MAX_MINES = RANDOM.nextInt(15, 25);

    private int mineCounter;
    private int flagCounter;
    private int visitCounter;

    private Text flagText;

    public MineGrid() {
        super(ROWS, COLUMNS, true);

        this.add(this.statistics());
    }

    @Override
    protected void earlyInitialize() {
        this.mineCounter = 0;
        this.flagCounter = 0;
        this.visitCounter = 0;
    }

    @Override
    protected MineCell newCell(final int row, final int col) {
        final var hasMine = this.mineCounter < MAX_MINES ? RANDOM.nextFloat(1f) < 0.2f : false;
        if (hasMine) {
            this.mineCounter++;
        }

        final var cell = new MineCell(hasMine, row, col);
        cell.addClickListener(this.cellClickListener());
        return cell;
    }

    @Override
    protected MineCell[][] newGrid(final int row, final int col) {
        return new MineCell[row][col];
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
        showAllMinesBtn.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        stats.add(mineStats, flagStats, showAllMinesBtn);
        return stats;
    }

    private void updateFlagText() {
        this.flagText.setText(" - " + this.flagCounter);
    }

    private void showAllMines() {
        for (final var row : this.grid) {
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
            if (this.visitCounter == (ROWS * COLUMNS) - this.mineCounter) {
                this.showAllMines();
            }
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
            this.visitCounter++;
            if (cell.isExploded()) {
                this.showAllMines();
            } else {
                this.findMinesInAdjacentCells(cell);
            }
        }
    }

    private void findMinesInAdjacentCells(final MineCell cell) {
        final var adjacentCells = this.findAdjacentCells(cell);

        final var numberOfMines = adjacentCells.stream().filter(MineCell::isHasMine).count();
        if (numberOfMines == 0) {
            adjacentCells.stream().filter(c -> !c.isVisited()).forEach(c -> this.processClickOnMineCell(c, false));
        } else {
            cell.updateNeighbouringMineCount(numberOfMines);
        }
    }

}
