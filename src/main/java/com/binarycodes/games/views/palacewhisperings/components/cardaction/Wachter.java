package com.binarycodes.games.views.palacewhisperings.components.cardaction;

import com.binarycodes.games.views.palacewhisperings.components.CardStackView;
import com.binarycodes.games.views.palacewhisperings.service.CardType;
import com.binarycodes.games.views.palacewhisperings.service.Player;
import com.vaadin.flow.component.dialog.Dialog;

public class Wachter extends Dialog {

    public Wachter(final Player player) {
        final var pickList = player.getDisplayedCards().stream().filter(card -> card.getType() != CardType.WÄCHTER).toList();

        final var cardStack = new CardStackView(pickList, card -> {
            final var ok = player.takeCardFromDisplay(card);
            if (!ok) {
                throw new UnsupportedOperationException("Cannot take a card from display which is not in display!");
            } else {
                this.close();
            }
        });
        this.add(cardStack);
        this.setCloseOnOutsideClick(false);
        this.setCloseOnEsc(false);
    }

}
