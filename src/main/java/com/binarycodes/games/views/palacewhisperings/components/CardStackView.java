package com.binarycodes.games.views.palacewhisperings.components;

import java.util.List;
import java.util.function.Consumer;

import com.binarycodes.games.views.palacewhisperings.service.Card;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class CardStackView extends HorizontalLayout {

    public CardStackView(final List<Card> cards, final Consumer<Card> listenerFn) {
        this.addClassName("card-stack-view");
        for (final var card : cards) {
            this.add(this.createCard(card, listenerFn));
        }
    }

    private Component createCard(final Card card, final Consumer<Card> listenerFn) {
        final var cardView = new CardView(card);

        if (listenerFn != null) {
            cardView.addCardClickListener(listenerFn);
        }
        return cardView;
    }

}
