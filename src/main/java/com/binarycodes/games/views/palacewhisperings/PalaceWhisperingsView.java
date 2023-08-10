package com.binarycodes.games.views.palacewhisperings;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.binarycodes.games.util.Game;
import com.binarycodes.games.util.GameIcon;
import com.binarycodes.games.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Game(title = "Palace Whisperings", icon = GameIcon.CARD_GAMES)
@Route(value = "palastgefluster", layout = MainLayout.class)
public class PalaceWhisperingsView extends VerticalLayout {

    private static SecureRandom RANDOM = new SecureRandom();
    private static int MAX_PLAYERS = 5;

    private CardDeck deck;
    private Map<CardColor, PlayerCardStack> playersMap;

    public PalaceWhisperingsView() {
        this.getStyle().setPadding("20px");
        this.setupNewGame();
    }

    private void setupNewGame() {
        this.deck = new CardDeck();
        this.playersMap = new HashMap<>();

        this.removeAll();

        final var buttonLayout = new HorizontalLayout();
        buttonLayout.add(new Button("New Game", event -> {
            this.setupNewGame();
        }));

        final var gameSection = new VerticalLayout();
        for (int i = 1; i <= MAX_PLAYERS; i++) {
            final var name = "Player " + i;
            final var player = this.createPlayer(name, CardColor.values()[i - 1]);
            final var playerView = new PlayerCardStack(player);
            playerView.addCardPlayedListener(event -> {
                this.handleCardPlay(event.getSource(), event.getCard());
            });
            playerView.setVisible(false);
            gameSection.add(playerView);

            this.playersMap.put(player.getColor(), playerView);
        }

        // pick a random player to start the game
        final var indexOfPlayer = RANDOM.nextInt(0, this.playersMap.size());
        final var keys = new ArrayList<>(this.playersMap.keySet());
        this.playersMap.get(keys.get(indexOfPlayer)).setVisible(true);

        this.add(buttonLayout, gameSection);
    }

    private Player createPlayer(final String name, final CardColor color) {
        final var cards = this.deck.startingCardsForPlayer(color);
        return new Player(name, color, cards);
    }

    private void handleCardPlay(final PlayerCardStack currentPlayerView, final Card card) {
        final var player = currentPlayerView.getPlayer();

        Notification.show(player.getName() + " played " + card.toString());
        if (card.getType().hasNextAction()) {

            if (player.getColor() != card.getColor()) {
                // choose player for next turn
                if (card.getColor().isPlayerType()) {
                    currentPlayerView.setVisible(false);
                    this.playersMap.get(card.getColor()).setVisible(true);
                } else {
                    // player with minimum displayed cards get the next turn, otherwise the current
                    // player retains it
                    this.playersMap.entrySet().stream().min((e1, e2) -> {
                        final int displayCount1 = e1.getValue().getPlayer().getDisplayedCards().size();
                        final int displayCount2 = e2.getValue().getPlayer().getDisplayedCards().size();
                        return Integer.compare(displayCount1, displayCount2);
                    }).ifPresentOrElse(entry -> {
                        currentPlayerView.setVisible(false);
                        entry.getValue().setVisible(true);
                    }, null);
                }
            }

        }
    }

}
