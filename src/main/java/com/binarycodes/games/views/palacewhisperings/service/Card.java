package com.binarycodes.games.views.palacewhisperings.service;

import java.util.Objects;
import java.util.UUID;

public class Card {
    private static final String IMAGE_NAME_FORMAT = "images/palastgefluster/%s.png";

    private final UUID id;
    private final CardColor color;
    private final CardType type;

    public Card(final CardColor color, final CardType type) {
        this.id = UUID.randomUUID();
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

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof final Card other)) {
            return false;
        }
        return Objects.equals(this.id, other.id);
    }

}
