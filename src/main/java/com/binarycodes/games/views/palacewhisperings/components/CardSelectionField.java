package com.binarycodes.games.views.palacewhisperings.components;

import java.util.List;

import com.binarycodes.games.views.palacewhisperings.service.Card;
import com.vaadin.flow.component.customfield.CustomField;

public class CardSelectionField extends CustomField<Card> {
    private final CardStackView cardStackView;

    public CardSelectionField(final String label, final List<Card> cards) {
        this.setLabel(label);
        this.cardStackView = new CardStackView(cards, this::cardSelected);
        this.add(this.cardStackView);
    }

    private void cardSelected(final Card card) {
        this.cardStackView.markSelectedCard(card);
        this.updateValue();
    }

    @Override
    protected Card generateModelValue() {
        return this.cardStackView.getSelectedCard().orElse(null);
    }

    @Override
    protected void setPresentationValue(final Card card) {
        this.cardSelected(card);
    }

}
