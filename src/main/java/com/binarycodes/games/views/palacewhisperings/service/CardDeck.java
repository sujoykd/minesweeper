package com.binarycodes.games.views.palacewhisperings.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class CardDeck {

    private static final int PLAYER_DEAL_CARD_SIZE = 6;

    private final List<CardColor> playingColors;
    private final List<Card> playingCards;
    private final List<Card> kingCards;

    public CardDeck(final List<CardColor> playingColors) {
        this.playingColors = playingColors;

        this.playingCards = new ArrayList<>(this.createPlayingCards());
        Collections.shuffle(this.playingCards);

        this.kingCards = new ArrayList<>(this.createKingCards());
        Collections.shuffle(this.kingCards);
    }

    private List<Card> createKingCards() {
        return Arrays.stream(CardType.values())
                     .filter(CardType::isKingType)
                     .map(type -> new Card(CardColor.kingCardColor(), type))
                     .toList();
    }

    private List<Card> createPlayingCards() {
        // two cards of each type of BROWN
        return Stream.concat(this.playingColors.stream(), Stream.of(CardColor.BROWN, CardColor.BROWN))
                     .flatMap(color -> Arrays.stream(CardType.values())
                                             .filter(CardType::isPlayerType)
                                             .map(type -> new Card(color, type)))
                     .toList();
    }

    public List<Card> startingCardsForPlayer() {
        final var draw = this.playingCards.subList(0, PLAYER_DEAL_CARD_SIZE);
        final var playerCards = new ArrayList<>(draw);
        draw.clear();
        Collections.shuffle(this.playingCards);
        return playerCards;
    }

    public int remainingCardsInDeck() {
        return this.playingCards.size();
    }

    public Card draw() {
        return this.playingCards.remove(0);
    }

    public Card drawKing() {
        final var drawnCard = this.kingCards.get(0);
        Collections.rotate(this.kingCards, -1);
        return drawnCard;
    }
}
