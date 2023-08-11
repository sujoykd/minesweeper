package com.binarycodes.games.views.palacewhisperings.service;

public enum CardType {
    HOFNARR("Court Jester"),
    MUNDSCHENK("Cup Bearer"),
    WÄCHTER("Custodian"),
    ZOFE("Lady's Maid"),
    ZAUBERER("Magician"),
    HOFMARSCHALL("Marshall"),
    SCHATZMEISTER("Treasurer"),
    KÖNIG("King");

    private final String role;

    CardType(final String role) {
        this.role = role;
    }

    public String getRole() {
        return this.role;
    }

    public boolean isPlayerType() {
        return this != KÖNIG;
    }

    public boolean isKingType() {
        return this != HOFMARSCHALL;
    }

    public boolean hasNextAction(final Player player) {
        return switch (this) {
            case HOFMARSCHALL -> false;
            case HOFNARR -> false;
            case KÖNIG -> false;
            case MUNDSCHENK -> false;
            case SCHATZMEISTER -> false;
            case WÄCHTER -> {
                final var cardsAvailableToTakeBack = player.getDisplayedCards().stream().anyMatch(card -> card.getType() != WÄCHTER);
                yield cardsAvailableToTakeBack;
            }
            case ZAUBERER -> false;
            case ZOFE -> false;
            default -> false;
        };
    }

}