package com.binarycodes.games.views.palacewhisperings;

import com.vaadin.flow.component.ComponentEvent;

public class CardPlayEvent extends ComponentEvent<PlayerCardStack> {

    private final Card card;

    public CardPlayEvent(final PlayerCardStack source, final Card card) {
        super(source, true);
        this.card = card;
    }

    public Card getCard() {
        return this.card;
    }

}
