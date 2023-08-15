package com.binarycodes.games.views.palacewhisperings.components.cardaction;

import com.binarycodes.games.views.palacewhisperings.components.CardSelectionField;
import com.binarycodes.games.views.palacewhisperings.service.Card;
import com.binarycodes.games.views.palacewhisperings.service.GameController;
import com.binarycodes.games.views.palacewhisperings.service.Player;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class Zofe extends ActionDialog {
    private static final String LABEL = "Select the card to discard";

    public Zofe(final GameController gameController, final Player player) {
        super(gameController, player);
    }

    @Override
    public Component content() {
        final var field = new CardSelectionField(LABEL, this.getPlayer().getCards());
        final var submitBtn = new Button("Done", event -> {
            final var newCard = this.getGameController().drawCardFromDeck();
            this.submit(field.getValue(), newCard);
        });

        return new VerticalLayout(field, submitBtn);
    }

    private void submit(final Card dropCard, final Card card) {
        final var ok = this.getPlayer().replaceCardInHand(dropCard, card);
        if (!ok) {
            throw new UnsupportedOperationException("Cannot discard a card from hand which is not in hand!");
        } else {
            this.close();
        }
    }

}
