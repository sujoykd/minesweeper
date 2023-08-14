package com.binarycodes.games.views.palacewhisperings.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Player {
    private final String name;

    private final CardColor color;
    private final List<Card> cards;

    private final List<Card> displayedCards;

    public Player(final String name, final CardColor color, final List<Card> cards) {
        this.name = name;
        this.color = color;
        this.cards = cards;

        this.displayedCards = new ArrayList<>();
    }

    public boolean playCard(final Card card) {
        if (!this.isCardTypeDisplayed(card)) {
            this.cards.remove(card);
            this.displayedCards.add(card);
            return true;
        }
        return false;
    }

    private boolean isCardTypeDisplayed(final Card playCard) {
        return this.displayedCards.stream()
                                  .filter(card -> card.getType() == playCard.getType())
                                  .findAny()
                                  .isPresent();
    }

    private boolean isCardTypeInHand(final Card playCard) {
        return this.cards.stream()
                         .filter(card -> card.getType() == playCard.getType())
                         .findAny()
                         .isPresent();
    }

    public CardColor getColor() {
        return this.color;
    }

    public String getName() {
        return this.name;
    }

    public List<Card> getDisplayedCards() {
        return this.displayedCards;
    }

    public List<Card> getCards() {
        return this.cards;
    }

    public boolean takeCardFromDisplay(final Card card) {
        if (this.isCardTypeDisplayed(card)) {
            this.displayedCards.remove(card);
            this.cards.add(card);
            return true;
        }
        return false;
    }

    public boolean replaceCardInHand(final Card dropCard, final Card newCard) {
        if (this.isCardTypeInHand(dropCard)) {
            this.cards.remove(dropCard);
            this.cards.add(newCard);
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof final Player other)) {
            return false;
        }
        return Objects.equals(this.name, other.name);
    }

}
