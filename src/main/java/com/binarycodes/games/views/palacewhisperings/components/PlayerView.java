package com.binarycodes.games.views.palacewhisperings.components;

import com.binarycodes.games.views.palacewhisperings.CardPlayEvent;
import com.binarycodes.games.views.palacewhisperings.service.Card;
import com.binarycodes.games.views.palacewhisperings.service.GameController;
import com.binarycodes.games.views.palacewhisperings.service.Player;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.shared.Registration;

public class PlayerView extends VerticalLayout {

    private final Player player;
    private final PlayerDeck deck;
    private final GameController gameController;

    public PlayerView(final Player player, final GameController gameController) {
        this.player = player;
        this.gameController = gameController;

        this.setSpacing(false);
        this.setPadding(false);

        this.deck = new PlayerDeck(player);
        this.deck.showHand(this::cardPlayAction);

        this.add(this.deck);
        this.setVisible(false);
    }

    private void cardPlayAction(final Card card) {
        final boolean validPlay = this.player.playCard(this.gameController, card);
        this.deck.showHand(this::cardPlayAction);
        if (validPlay) {
            this.fireEvent(new CardPlayEvent(this, card));
        }
    }

    public Registration addCardPlayedListener(final ComponentEventListener<CardPlayEvent> listener) {
        return super.addListener(CardPlayEvent.class, listener);
    }

    public Player getPlayer() {
        return this.player;
    }

    public void update() {
        this.deck.showHand(this::cardPlayAction);
    }

}
