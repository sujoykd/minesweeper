package com.binarycodes.games.views.minesweeper;

import com.binarycodes.games.util.Game;
import com.binarycodes.games.util.GameIcon;
import com.binarycodes.games.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

@Game(title = "MineSweeper", icon = GameIcon.MINESWEEPER)
@Route(value = "mine", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class MineSweeperView extends VerticalLayout {

    public MineSweeperView() {
        this.getStyle().setPadding("20px");
        this.setupNewGame();
    }

    private void setupNewGame() {
        this.removeAll();

        final var buttonLayout = new HorizontalLayout();
        buttonLayout.add(new Button("New Game", event -> {
            this.setupNewGame();
        }));

        this.add(buttonLayout, new MineGrid());
    }

}
