package com.binarycodes.games.views.palacewhisperings.components;

import java.util.ArrayList;
import java.util.List;

import com.binarycodes.games.views.palacewhisperings.service.Card;
import com.vaadin.flow.component.customfield.CustomField;

public class CardSelectionGroupField extends CustomField<Card> {
    private final List<CardStackView> cardStackViews;

    public CardSelectionGroupField(final String label, final List<List<Card>> cardsList) {
        this.cardStackViews = new ArrayList<>();
        this.setLabel(label);

        for (final var cards : cardsList) {
            final var stack = new CardStackView(cards, this::cardSelected);
            this.cardStackViews.add(stack);
            this.add(stack);
        }
    }

    private void cardSelected(final Card card) {
        this.cardStackViews.forEach(stack -> stack.markSelectedCard(card));
        this.updateValue();
    }

    @Override
    protected Card generateModelValue() {
        for (final var stack : this.cardStackViews) {
            final var card = stack.getSelectedCard();
            if (card.isPresent()) {
                return card.get();
            }
        }
        return null;
    }

    @Override
    protected void setPresentationValue(final Card card) {
        this.cardSelected(card);
    }

}
