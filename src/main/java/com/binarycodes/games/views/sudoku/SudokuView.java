package com.binarycodes.games.views.sudoku;

import com.binarycodes.games.util.Game;
import com.binarycodes.games.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Game(title = "Sudoku", icon = VaadinIcon.GRID)
@Route(value = "sudoku", layout = MainLayout.class)
public class SudokuView extends VerticalLayout {

    public SudokuView() {
        this.getStyle().setPadding("20px");
        this.setupNewGame();
    }

    private void setupNewGame() {
        this.removeAll();

        final var buttonLayout = new HorizontalLayout();
        buttonLayout.add(new Button("New Game", event -> {
            this.setupNewGame();
        }));

        this.add(buttonLayout, new SudokuGrid());
    }
}
