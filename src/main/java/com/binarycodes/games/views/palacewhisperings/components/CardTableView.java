package com.binarycodes.games.views.palacewhisperings.components;

import com.binarycodes.games.views.palacewhisperings.components.cardaction.Wachter;
import com.binarycodes.games.views.palacewhisperings.service.Card;
import com.binarycodes.games.views.palacewhisperings.service.GameController;
import com.binarycodes.games.views.palacewhisperings.service.Player;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class CardTableView extends VerticalLayout {

    public CardTableView(final GameController gameController) {
        this.setSpacing(false);
        this.setPadding(false);
        this.update(gameController);
    }

    public void update(final GameController gameController) {
        this.removeAll();

        for (final var player : gameController.getAllPlayers()) {
            final var deck = new PlayerDeck(player);
            deck.showDisplayOnly();
            this.add(deck);
        }
    }

    public Dialog nextAction(final Player player, final Card card) {
        return switch (card.getType()) {
            case HOFMARSCHALL -> null;
            case MUNDSCHENK -> null;
            case SCHATZMEISTER -> null;
            case WÄCHTER -> {
                // take back a card from display
                yield new Wachter(player);
            }
            case ZAUBERER -> null;
            case ZOFE -> null;
            default -> null;
        };
    }

}
