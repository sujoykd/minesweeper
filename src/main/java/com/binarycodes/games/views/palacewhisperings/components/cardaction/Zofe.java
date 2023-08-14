package com.binarycodes.games.views.palacewhisperings.components.cardaction;

import com.binarycodes.games.views.palacewhisperings.components.CardSelectionField;
import com.binarycodes.games.views.palacewhisperings.service.Card;
import com.binarycodes.games.views.palacewhisperings.service.GameController;
import com.binarycodes.games.views.palacewhisperings.service.Player;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class Zofe extends Dialog {

    private static final String LABEL = "Select the card to discard";
    private final Player player;

    public Zofe(final Player player, final GameController gameController) {
        this.player = player;

        final var field = new CardSelectionField(LABEL, player.getCards());
        final var submitBtn = new Button("Done", event -> {
            final var newCard = gameController.drawCardFromDeck();
            this.submit(field.getValue(), newCard);
        });

        final var layout = new VerticalLayout(field, submitBtn);
        this.add(layout);

        this.setCloseOnOutsideClick(false);
        this.setCloseOnEsc(false);
    }

    private void submit(final Card dropCard, final Card card) {
        final var ok = this.player.replaceCardInHand(dropCard, card);
        if (!ok) {
            throw new UnsupportedOperationException("Cannot discard a card from hand which is not in hand!");
        } else {
            this.close();
        }
    }

}
