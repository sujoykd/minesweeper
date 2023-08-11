package com.binarycodes.games.views.palacewhisperings.components;

import java.util.function.Consumer;

import com.binarycodes.games.views.palacewhisperings.service.Card;
import com.binarycodes.games.views.palacewhisperings.service.Player;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class PlayerDeck extends VerticalLayout {

    private final Player player;
    private final HorizontalLayout deckArea;

    public PlayerDeck(final Player player) {
        this.player = player;
        this.setSpacing(false);
        this.setPadding(false);
        this.addClassName("card-player-stack");

        this.add(new PlayerNameView(this.player));

        this.deckArea = new HorizontalLayout();
        this.deckArea.getStyle().set("gap", "15em");

        this.add(this.deckArea);
    }

    public void showHand(final Consumer<Card> cardPlayFn) {
        this.removeClassName("card-player-stack-displayed-view");
        this.addClassName("card-player-stack-inhand-view");
        this.showDeck(false, true, cardPlayFn);
    }

    public void showDisplayOnly() {
        this.removeClassName("card-player-stack-inhand-view");
        this.addClassName("card-player-stack-displayed-view");
        this.showDeck(true, false, null);
    }

    private void showDeck(final boolean showDisplayed, final boolean showHand, final Consumer<Card> cardPlayFn) {
        this.deckArea.removeAll();

        if (showHand) {
            final var cardsInHand = new CardStackView(this.player.getCards(), cardPlayFn);
            cardsInHand.addClassName("card-inhand-view");
            this.deckArea.add(cardsInHand);
        }

        if (showDisplayed) {
            final var dispalyedCards = new CardStackView(this.player.getDisplayedCards(), null);
            dispalyedCards.addClassName("card-displayed-view");
            this.deckArea.add(dispalyedCards);
        }
    }

}
