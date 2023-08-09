package com.binarycodes.games.views.tictactoe;

import com.binarycodes.games.util.Game;
import com.binarycodes.games.util.GameIcon;
import com.binarycodes.games.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Game(title = "TicTacToe", icon = GameIcon.TIC_TAC_TOE)
@Route(value = "tictactoe", layout = MainLayout.class)
public class TicTacToeView extends VerticalLayout {

    public TicTacToeView() {
        this.getStyle().setPadding("20px");
        this.setupNewGame();
    }

    private void setupNewGame() {
        this.removeAll();

        final var buttonLayout = new HorizontalLayout();
        buttonLayout.add(new Button("New Game", event -> {
            this.setupNewGame();
        }));

        this.add(buttonLayout, new TicTacToeGrid());
    }
}
