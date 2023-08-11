package com.binarycodes.games.views.palacewhisperings.service;

public class Card {
    private static final String IMAGE_NAME_FORMAT = "images/palastgefluster/%s.png";

    private final CardColor color;
    private final CardType type;

    public Card(final CardColor color, final CardType type) {
        this.color = color;
        this.type = type;
    }

    public CardColor getColor() {
        return this.color;
    }

    public CardType getType() {
        return this.type;
    }

    public String imagePath() {
        return IMAGE_NAME_FORMAT.formatted(this.toString());
    }

    @Override
    public String toString() {
        return this.type.name().toLowerCase() + "_" + this.color.name().toLowerCase();
    }
}
