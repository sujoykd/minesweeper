package com.binarycodes.games.views.palacewhisperings.service;

import java.util.ArrayList;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

public enum CardType {
    HOFNARR("Court Jester") {
        @Override
        public boolean cardHasNextAction(final Player player, final GameController gameController) {
            return false;
        }
    },
    MUNDSCHENK("Cup Bearer") {
        @Override
        public boolean cardHasNextAction(final Player player, final GameController gameController) {
            // exchange a card in display with others display without forcing a palace whisper

            final var playerCardsToExchange = player.getDisplayedCards()
                                                    .stream()
                                                    .filter(card -> card.getType() != this)
                                                    .toList();
            final var playersForExchange = gameController.getAllPlayers()
                                                         .stream()
                                                         .filter(p -> !Objects.equals(p, player))
                                                         .filter(p -> p.getDisplayedCards().size() > 0)
                                                         .toList();

            if (playerCardsToExchange.size() > 0 && playersForExchange.size() > 0) {
                // anyone has a card which the player does not have and is thus eligible for swapping

                return playersForExchange.stream().anyMatch(p -> {
                    final var list = new ArrayList<>(p.getDisplayedCards());
                    list.removeAll(playerCardsToExchange);
                    return list.size() > 0;
                });
            }

            return false;
        }
    },
    WÄCHTER("Custodian") {
        @Override
        public boolean cardHasNextAction(final Player player, final GameController gameController) {
            // take back a card from display

            return player.getDisplayedCards().stream().anyMatch(card -> card.getType() != this);
        }
    },
    ZOFE("Lady's Maid") {
        @Override
        public boolean cardHasNextAction(final Player player, final GameController gameController) {
            // discard a card from hand and draw a new one

            return !player.getCards().isEmpty();
        }
    },
    ZAUBERER("Magician") {
        @Override
        public boolean cardHasNextAction(final Player player, final GameController gameController) {
            return false;
        }
    },
    HOFMARSCHALL("Marshall") {
        @Override
        public boolean cardHasNextAction(final Player player, final GameController gameController) {
            return true;
        }
    },
    SCHATZMEISTER("Treasurer") {
        @Override
        public boolean cardHasNextAction(final Player player, final GameController gameController) {
            return false;
        }
    };

    private final String role;

    CardType(final String role) {
        this.role = role;
    }

    public String getRole() {
        return this.role;
    }

    public boolean isKingType() {
        return this != HOFMARSCHALL;
    }

    public boolean isBlockedByKing() {
        return this == HOFNARR;
    }

    public String displayName() {
        return StringUtils.capitalize(this.name().toLowerCase());
    }

    protected abstract boolean cardHasNextAction(final Player player, final GameController gameController);

    public boolean hasNextAction(final Player player, final GameController gameController) {
        final var isSetAsKing = gameController.getKingCardInEffect().map(king -> king.getType() == this).orElse(false);
        if (isSetAsKing && this.isBlockedByKing()) {
            return false;
        }
        return this.cardHasNextAction(player, gameController);
    }

}
