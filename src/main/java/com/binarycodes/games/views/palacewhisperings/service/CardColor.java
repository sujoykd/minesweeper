package com.binarycodes.games.views.palacewhisperings.service;

public enum CardColor {
    RED("#b5201b"),
    YELLOW("yellow"),
    GREEN("green"),
    BLUE("blue"),
    PURPLE("purple"),
    BROWN("#522a0c");

    private final String tintShade;

    CardColor(final String tintShade) {
        this.tintShade = tintShade;
    }

    public boolean isPlayerType() {
        return this != BROWN;
    }

    public static CardColor kingCardColor() {
        return BROWN;
    }

    public String getTintShade() {
        return this.tintShade;
    }

}
