package com.binarycodes.games.views.palacewhisperings.components;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import com.binarycodes.games.views.palacewhisperings.service.Card;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class CardStackView extends HorizontalLayout {
    private static final String SELECTED_CARD_CLASS_NAME = "card-selected";

    private final List<CardView> cardViews;

    public CardStackView(final List<Card> cards) {
        this(cards, null);
    }

    public CardStackView(final List<Card> cards, final Consumer<Card> listenerFn) {
        this.addClassName("card-stack-view");

        this.cardViews = cards.stream()
                              .map(card -> this.createCard(card, listenerFn))
                              .toList();
        this.cardViews.stream()
                      .map(cv -> (Component) cv)
                      .forEach(this::add);
    }

    private CardView createCard(final Card card, final Consumer<Card> listenerFn) {
        final var cardView = new CardView(card);

        if (listenerFn != null) {
            cardView.addClassName("card-clickable");
            cardView.addCardClickListener(listenerFn);
        }
        return cardView;
    }

    public void markSelectedCard(final Card card) {
        for (final var view : this.cardViews) {
            view.removeClassName(SELECTED_CARD_CLASS_NAME);
            if (view.getCard().equals(card)) {
                view.addClassName(SELECTED_CARD_CLASS_NAME);
            }
        }
    }

    public Optional<Card> getSelectedCard() {
        return this.cardViews.stream()
                             .filter(view -> view.hasClassName(SELECTED_CARD_CLASS_NAME))
                             .map(CardView::getCard)
                             .findFirst();
    }

    public void removeAllSelection() {
        this.cardViews.forEach(cv -> cv.removeClassName(SELECTED_CARD_CLASS_NAME));
    }
}
