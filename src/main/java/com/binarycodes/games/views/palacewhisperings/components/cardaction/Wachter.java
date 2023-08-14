package com.binarycodes.games.views.palacewhisperings.components.cardaction;

import com.binarycodes.games.views.palacewhisperings.components.CardSelectionField;
import com.binarycodes.games.views.palacewhisperings.service.Card;
import com.binarycodes.games.views.palacewhisperings.service.CardType;
import com.binarycodes.games.views.palacewhisperings.service.Player;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class Wachter extends Dialog {

    private static final String LABEL = "Select the card to take back";
    private final Player player;

    public Wachter(final Player player) {
        this.player = player;

        final var pickList = player.getDisplayedCards().stream().filter(card -> card.getType() != CardType.WÄCHTER).toList();

        final var field = new CardSelectionField(LABEL, pickList);
        final var submitBtn = new Button("Done", event -> {
            this.submit(field.getValue());
        });

        final var layout = new VerticalLayout(field, submitBtn);
        this.add(layout);

        this.setCloseOnOutsideClick(false);
        this.setCloseOnEsc(false);
    }

    private void submit(final Card card) {
        final var ok = this.player.takeCardFromDisplay(card);
        if (!ok) {
            throw new UnsupportedOperationException("Cannot take a card from display which is not in display!");
        } else {
            this.close();
        }
    }

}
