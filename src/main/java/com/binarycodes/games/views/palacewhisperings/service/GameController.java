package com.binarycodes.games.views.palacewhisperings.service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class GameController {

    private static SecureRandom RANDOM = new SecureRandom();
    private static int MIN_PLAYERS = 3;
    private static int MAX_PLAYERS = 5;

    private CardDeck deck;
    private List<Player> allPlayers;
    private final List<CardColor> playingColors;

    public GameController() {
        this.playingColors = new ArrayList<>(MAX_PLAYERS);
    }

    public List<Player> createAllPlayers() {
        final var numberOfPlayers = RANDOM.nextInt(MIN_PLAYERS, MAX_PLAYERS);

        // pick the playing colors
        for (int i = 1; i <= numberOfPlayers; i++) {
            this.playingColors.add(CardColor.values()[i - 1]);
        }

        // create the playing deck
        this.deck = new CardDeck(this.playingColors);

        this.allPlayers = this.playingColors.stream().map(color -> {
            final var name = "Player " + color.name().toLowerCase();
            return this.createPlayer(name, color);
        }).toList();

        return this.allPlayers;

    }

    private Player createPlayer(final String name, final CardColor color) {
        final var cards = this.deck.startingCardsForPlayer();
        return new Player(name, color, cards);
    }

    public CardColor nextPlayerColor(final Player player, final Card card) {
        // choose player for next turn
        CardColor nextColor = player.getColor();
        if (player.getColor() != card.getColor()) {
            if (card.getColor().isPlayerType()) {
                nextColor = card.getColor();
            } else {
                // player with minimum displayed cards get the next turn, otherwise the current
                // player retains it
                nextColor = this.allPlayers.stream().min((e1, e2) -> {
                    final int displayCount1 = e1.getDisplayedCards().size();
                    final int displayCount2 = e2.getDisplayedCards().size();
                    return Integer.compare(displayCount1, displayCount2);
                }).map(Player::getColor).orElseGet(player::getColor);
            }
        }
        return nextColor;
    }

    public CardColor pickPlayerColorToStartGame() {
        final var indexOfPlayer = RANDOM.nextInt(0, this.allPlayers.size());
        return this.allPlayers.get(indexOfPlayer).getColor();
    }

    public List<Player> getAllPlayers() {
        return this.allPlayers;
    }

}
