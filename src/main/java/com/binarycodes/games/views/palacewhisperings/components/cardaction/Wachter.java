package com.binarycodes.games.views.palacewhisperings.components.cardaction;

import com.binarycodes.games.views.palacewhisperings.components.CardSelectionField;
import com.binarycodes.games.views.palacewhisperings.service.Card;
import com.binarycodes.games.views.palacewhisperings.service.CardType;
import com.binarycodes.games.views.palacewhisperings.service.GameController;
import com.binarycodes.games.views.palacewhisperings.service.Player;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class Wachter extends ActionDialog {

    private static final String LABEL = "Select the card to take back";

    public Wachter(final GameController gameController, final Player player) {
        super(gameController, player);
    }

    @Override
    public Component content() {
        final var pickList = this.getPlayer().getDisplayedCards().stream().filter(card -> card.getType() != CardType.WÄCHTER).toList();

        final var field = new CardSelectionField(LABEL, pickList);
        final var submitBtn = new Button("Done", event -> {
            this.submit(field.getValue());
        });

        return new VerticalLayout(field, submitBtn);
    }

    private void submit(final Card card) {
        final var ok = this.getPlayer().takeCardFromDisplay(card);
        if (!ok) {
            throw new UnsupportedOperationException("Cannot take a card from display which is not in display!");
        } else {
            this.close();
        }
    }

}
