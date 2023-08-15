package com.binarycodes.games.views.palacewhisperings.components.cardaction;

import com.binarycodes.games.views.palacewhisperings.components.CardSelectionField;
import com.binarycodes.games.views.palacewhisperings.components.CardSelectionGroupField;
import com.binarycodes.games.views.palacewhisperings.service.Card;
import com.binarycodes.games.views.palacewhisperings.service.CardType;
import com.binarycodes.games.views.palacewhisperings.service.GameController;
import com.binarycodes.games.views.palacewhisperings.service.Player;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class Mundschenk extends Dialog {

    private static final String LABEL_SELF = "Select the exchange from your display";
    private static final String LABEL_OTHERS = "Select the exchange from your display";

    private final GameController gameController;

    public Mundschenk(final Player player, final GameController gameController) {
        this.gameController = gameController;

        final var selfPickList = player.getDisplayedCards().stream().filter(card -> card.getType() != CardType.MUNDSCHENK).toList();
        final var otherPickList = gameController.getAllPlayers().stream().map(Player::getDisplayedCards).toList();

        final var selfField = new CardSelectionField(LABEL_SELF, selfPickList);
        final var othersField = new CardSelectionGroupField(LABEL_OTHERS, otherPickList);

        final var submitBtn = new Button("Done", event -> {
            this.submit(selfField.getValue(), othersField.getValue());
        });

        final var layout = new VerticalLayout(selfField, othersField, submitBtn);
        this.add(layout);

        this.setCloseOnOutsideClick(false);
        this.setCloseOnEsc(false);
    }

    private void submit(final Card selfCard, final Card othersCard) {
        this.gameController.swapDisplayedCard(selfCard, othersCard);
    }

}
