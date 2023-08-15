package com.binarycodes.games.views.palacewhisperings.service;

import java.util.Objects;
import java.util.UUID;

public class Card {
    private static final String IMAGE_NAME_FORMAT = "images/palastgefluster/%s.png";

    private final UUID id;
    private final CardColor color;
    private final CardType type;
    private final boolean king;

    public Card(final CardColor color, final CardType type, final boolean king) {
        this.id = UUID.randomUUID();
        this.color = color;
        this.type = type;
        this.king = king;
    }

    public CardColor getColor() {
        return this.color;
    }

    public CardType getType() {
        return this.type;
    }

    public String imagePath() {
        if (this.king) {
            final var name = "king_" + this.type.name().toLowerCase();
            return IMAGE_NAME_FORMAT.formatted(name);
        }
        final var name = this.type.name().toLowerCase() + "_" + this.color.name().toLowerCase();
        return IMAGE_NAME_FORMAT.formatted(name);
    }

    public boolean isKing() {
        return this.king;
    }

    @Override
    public String toString() {
        return (this.king ? "king_" : "") + this.type.name().toLowerCase() + "_" + this.color.name().toLowerCase();
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
