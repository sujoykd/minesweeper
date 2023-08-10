package com.binarycodes.games.views.palacewhisperings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CardDeck {

    private final List<CardColor> colors;
    private final List<Card> playingCards;
    private final List<Card> kingCards;

    public CardDeck() {
        this.colors = List.of(CardColor.values()).stream().filter(CardColor::isPlayerType).collect(Collectors.toList());

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
        return CardColor.allPlayingCardColors()
                        .stream()
                        .flatMap(color -> Arrays.stream(CardType.values())
                                                .filter(CardType::isPlayerType)
                                                .map(type -> new Card(color, type)))
                        .toList();
    }

    public List<Card> startingCardsForPlayer(final CardColor color) {
        // remove color from remaining set
        this.colors.remove(color);

        final var draw = this.playingCards.subList(0, 6);
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
