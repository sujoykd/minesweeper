package com.binarycodes.games.views.palacewhisperings.components;

import java.util.Optional;

import com.binarycodes.games.views.palacewhisperings.components.cardaction.Hofmarschall;
import com.binarycodes.games.views.palacewhisperings.components.cardaction.Mundschenk;
import com.binarycodes.games.views.palacewhisperings.components.cardaction.Wachter;
import com.binarycodes.games.views.palacewhisperings.components.cardaction.Zofe;
import com.binarycodes.games.views.palacewhisperings.service.Card;
import com.binarycodes.games.views.palacewhisperings.service.GameController;
import com.binarycodes.games.views.palacewhisperings.service.Player;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class CardTableView extends VerticalLayout {

    private final GameController gameController;

    public CardTableView(final GameController gameController) {
        this.gameController = gameController;
        this.setSpacing(false);
        this.setPadding(false);
        this.update();
    }

    public void update() {
        this.removeAll();

        for (final var player : this.gameController.getAllPlayers()) {
            final var deck = new PlayerDeck(player);
            deck.showDisplayOnly();
            this.add(deck);
        }
    }

    public Optional<Dialog> nextAction(final Player player, final Card card) {
        final var hasAction = card.getType().hasNextAction(player, this.gameController);
        if (!hasAction) {
            return Optional.empty();
        }

        final var dialog = switch (card.getType()) {
            case HOFMARSCHALL -> new Hofmarschall(this.gameController, player);
            case MUNDSCHENK -> new Mundschenk(this.gameController, player);
            case SCHATZMEISTER -> null;
            case WÄCHTER -> new Wachter(this.gameController, player);
            case ZAUBERER -> null;
            case ZOFE -> new Zofe(this.gameController, player);
            default -> null;
        };
        return Optional.of(dialog);
    }

}
