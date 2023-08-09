package com.binarycodes.games.views.tictactoe;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import com.binarycodes.games.util.CommonGrid;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.html.Div;

public class TicTacToeGrid extends CommonGrid<TicTacToeCell> {

    private static final SecureRandom RANDOM = new SecureRandom();

    private static final int SIZE = 3;
    private static final char USER_MARK = 'X';
    private static final char AI_MARK = 'O';

    private int visitCounter;

    public TicTacToeGrid() {
        super(SIZE, SIZE, false);
    }

    @Override
    protected void earlyInitialize() {
        this.visitCounter = 0;
    }

    @Override
    protected TicTacToeCell newCell(final int row, final int col) {
        final var cell = new TicTacToeCell(row, col);
        cell.addClickListener(this.cellClickListener());
        return cell;
    }

    @Override
    protected TicTacToeCell[][] newGrid(final int row, final int col) {
        return new TicTacToeCell[row][col];
    }

    private ComponentEventListener<ClickEvent<Div>> cellClickListener() {
        return event -> {
            final var cell = (TicTacToeCell) event.getSource();
            this.mark(cell, USER_MARK, true);
        };
    }

    private void aiPlay() {
        final TicTacToeCell play = this.findOptimumCellToPlay();
        this.mark(play, AI_MARK, false);
    }

    private TicTacToeCell findOptimumCellToPlay() {
        TicTacToeCell blockUserCell = null;

        final List<TicTacToeCell> unPlayedCells = new ArrayList<>();

        for (final var rows : this.grid) {
            for (final var cell : rows) {
                final var lines = this.allLinesPassingThrough(cell);

                final var found = this.checkForPossibleWin(lines, AI_MARK);
                if (found != null) {
                    return found;
                }

                // no point in checking if one already found but do not return here since
                // priority is to find winning spot
                if (blockUserCell == null) {
                    blockUserCell = this.checkForPossibleWin(lines, USER_MARK);
                }

                if (!cell.isVisited()) {
                    unPlayedCells.add(cell);
                }
            }
        }

        if (blockUserCell != null) {
            return blockUserCell;
        }

        return unPlayedCells.get(RANDOM.nextInt(0, unPlayedCells.size()));
    }

    private TicTacToeCell checkForPossibleWin(final List<List<TicTacToeCell>> lines, final char marking) {
        return lines.stream()
                    .filter(line -> line.size() == SIZE)
                    .filter(line -> line.stream()
                                        .filter(item -> item.isVisited() && item.getMarking() == marking)
                                        .count() == (SIZE - 1))
                    .findFirst()
                    .map(line -> line.stream()
                                     .filter(item -> !item.isVisited())
                                     .findFirst()
                                     .orElse(null))
                    .orElse(null);
    }

    private void endGame() {
        this.setEnabled(false);
    }

    private void mark(final TicTacToeCell cell, final char marking, final boolean fromUser) {
        final boolean ok = cell.mark(marking);
        if (ok) {
            this.visitCounter++;

            final var lines = this.allLinesPassingThrough(cell);
            final var winningLine = lines.stream()
                                         .filter(line -> {
                                             final var count = line.stream()
                                                                   .filter(item -> item.isVisited() && item.getMarking() == marking)
                                                                   .count();
                                             return count == SIZE;
                                         })
                                         .findFirst();

            winningLine.ifPresentOrElse(line -> {
                line.forEach(TicTacToeCell::markComplete);
                this.endGame();
            }, () -> {
                if (this.visitCounter == SIZE * SIZE) {
                    this.endGame();
                } else if (fromUser) {
                    this.aiPlay();
                }
            });
        }
    }

}
