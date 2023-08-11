package com.binarycodes.games.views.palacewhisperings;

import com.binarycodes.games.views.palacewhisperings.components.PlayerView;
import com.binarycodes.games.views.palacewhisperings.service.Card;
import com.vaadin.flow.component.ComponentEvent;

public class CardPlayEvent extends ComponentEvent<PlayerView> {

    private final Card card;

    public CardPlayEvent(final PlayerView source, final Card card) {
        super(source, true);
        this.card = card;
    }

    public Card getCard() {
        return this.card;
    }

}
