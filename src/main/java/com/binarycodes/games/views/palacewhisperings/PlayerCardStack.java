package com.binarycodes.games.views.palacewhisperings;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.shared.Registration;

public class PlayerCardStack extends VerticalLayout {

    private final Player player;
    private final HorizontalLayout deckArea;

    public PlayerCardStack(final Player player) {
        this.player = player;

        this.setSpacing(false);
        this.setPadding(false);
        this.addClassName("card-player-stack");

        this.showName();

        this.deckArea = new HorizontalLayout();
        this.deckArea.getStyle().set("gap", "3em");
        this.add(this.deckArea);

        this.showDeck();
    }

    private void showDeck() {
        this.deckArea.removeAll();

        final var cardsInHand = new HorizontalLayout();
        for (final var card : this.player.getCards()) {
            cardsInHand.add(this.createCard(card, false));
        }

        final var dispalyedCards = new HorizontalLayout();
        for (final var card : this.player.getDisplayedCards()) {
            dispalyedCards.add(this.createCard(card, true));
        }

        final var deck = new HorizontalLayout();
        deck.getStyle().set("gap", "15em");
        deck.add(cardsInHand, dispalyedCards);

        this.deckArea.add(deck);
    }

    private Component createCard(final Card card, final boolean display) {
        final var cardImage = new Image(card.imagePath(), card.toString());
        if (!display) {
            cardImage.addClassName("palast-card");
        }
        cardImage.getStyle().setHeight("300px");
        cardImage.getStyle().setWidth("200px");

        cardImage.addClickListener(event -> {
            final boolean validPlay = this.player.playCard(card);
            this.showDeck();
            if (validPlay) {
                this.fireEvent(new CardPlayEvent(this, card));
            }
        });

        return cardImage;
    }

    private void showName() {
        final var displaySpan = new Span(this.player.getName());

        final var colorBadge = new Div();
        colorBadge.addClassName("card-player-color-badge");
        colorBadge.getStyle().set("background-color", this.player.getColor().getTintShade());

        final var display = new HorizontalLayout(displaySpan, colorBadge);
        display.addClassName("card-player-name-badge");

        this.add(display);
    }

    protected Registration addCardPlayedListener(final ComponentEventListener<CardPlayEvent> listener) {
        return super.addListener(CardPlayEvent.class, listener);
    }

    public Player getPlayer() {
        return this.player;
    }

}
