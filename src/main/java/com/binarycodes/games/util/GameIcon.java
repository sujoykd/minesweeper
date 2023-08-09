package com.binarycodes.games.util;

import com.vaadin.flow.component.html.Image;

public enum GameIcon {
    CARD_GAMES("card-games.png"),
    MINESWEEPER("bomb.png"),
    SUDOKU("sudoku.png"),
    TIC_TAC_TOE("tic-tac-toe.png");

    private final String imagePath;

    GameIcon(final String imagePath) {
        this.imagePath = "icons/gameicons/" + imagePath;
    }

    public Image create() {
        final var image = new Image(this.imagePath, this.name());
        image.getStyle().setHeight("32px");
        image.getStyle().setWidth("32px");
        return image;
    }

}
