package com.binarycodes.games.views.palacewhisperings.components.cardaction;

import com.binarycodes.games.views.palacewhisperings.components.CardSelectionField;
import com.binarycodes.games.views.palacewhisperings.components.CardSelectionGroupField;
import com.binarycodes.games.views.palacewhisperings.service.Card;
import com.binarycodes.games.views.palacewhisperings.service.CardType;
import com.binarycodes.games.views.palacewhisperings.service.GameController;
import com.binarycodes.games.views.palacewhisperings.service.Player;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class Mundschenk extends ActionDialog {

    private static final String LABEL_SELF = "Select the exchange from your display";
    private static final String LABEL_OTHERS = "Select the exchange from your display";

    public Mundschenk(final GameController gameController, final Player player) {
        super(gameController, player);
    }

    @Override
    public Component content() {
        final var selfPickList = this.getPlayer()
                                     .getDisplayedCards()
                                     .stream()
                                     .filter(card -> card.getType() != CardType.MUNDSCHENK)
                                     .toList();
        final var otherPickList = this.getGameController()
                                      .getAllPlayers()
                                      .stream()
                                      .filter(p -> !p.equals(this.getPlayer()))
                                      .map(Player::getDisplayedCards)
                                      .toList();

        final var selfField = new CardSelectionField(LABEL_SELF, selfPickList);
        final var othersField = new CardSelectionGroupField(LABEL_OTHERS, otherPickList);

        final var submitBtn = new Button("Done", event -> {
            this.submit(selfField.getValue(), othersField.getValue());
        });

        return new VerticalLayout(selfField, othersField, submitBtn);
    }

    private void submit(final Card selfCard, final Card othersCard) {
        this.getGameController().swapDisplayedCard(selfCard, othersCard);
        this.close();
    }
}
