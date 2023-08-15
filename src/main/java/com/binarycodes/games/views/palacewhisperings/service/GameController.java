package com.binarycodes.games.views.palacewhisperings.service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GameController {

    private static SecureRandom RANDOM = new SecureRandom();
    private static int MIN_PLAYERS = 3;
    private static int MAX_PLAYERS = 5;

    private CardDeck deck;
    private List<Player> allPlayers;
    private final List<CardColor> playingColors;
    private final Map<Card, Player> cardPlayerMap;

    private Optional<Card> kingCardInEffect;

    public GameController() {
        this.playingColors = new ArrayList<>(MAX_PLAYERS);
        this.cardPlayerMap = new HashMap<>(MAX_PLAYERS * CardDeck.PLAYER_DEAL_CARD_SIZE);
        this.kingCardInEffect = Optional.empty();
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
        final var player = new Player(name, color, cards);
        final var map = cards.stream().collect(Collectors.toMap(Function.identity(), card -> player));
        this.cardPlayerMap.putAll(map);
        return player;
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

    public Card drawCardFromDeck() {
        return this.deck.draw();
    }

    public void swapDisplayedCard(final Card selfCard, final Card othersCard) {
        if (!this.cardPlayerMap.containsKey(selfCard) || !this.cardPlayerMap.containsKey(othersCard)) {
            throw new UnsupportedOperationException("Illegal card. Player for the card not found.");
        }

        final var player = this.cardPlayerMap.get(selfCard);
        final var other = this.cardPlayerMap.get(selfCard);

        // cannot force a palace whisper by swapping display cards
        final var playerError = player.getDisplayedCards()
                                      .stream()
                                      .filter(card -> card.getType() != selfCard.getType())
                                      .anyMatch(card -> card.getType() == othersCard.getType());
        final var otherError = other.getDisplayedCards()
                                    .stream()
                                    .filter(card -> card.getType() != othersCard.getType())
                                    .anyMatch(card -> card.getType() == selfCard.getType());

        if (playerError || otherError) {
            throw new UnsupportedOperationException("Forcing a palace whisper is not allowed");
        }

        // remove cards from corresponding players
        player.getDisplayedCards().remove(selfCard);
        other.getDisplayedCards().remove(othersCard);

        // change card ownership
        this.cardPlayerMap.put(selfCard, other);
        this.cardPlayerMap.put(othersCard, player);

        // allocate cards to players
        player.getDisplayedCards().add(othersCard);
        other.getDisplayedCards().add(selfCard);
    }

    public Card newKing() {
        final var kingCard = this.deck.drawKing();
        this.kingCardInEffect = Optional.of(kingCard);
        return kingCard;
    }

    public Optional<Card> getKingCardInEffect() {
        return this.kingCardInEffect;
    }

    public boolean canPlay(final Card card) {
        return this.kingCardInEffect.map(king -> {
            if (king.getType() != card.getType()) {
                return true;
            }
            return !card.getType().isBlockedByKing();
        }).orElse(true);
    }

}
