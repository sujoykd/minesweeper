package com.binarycodes.games.views.palacewhisperings;

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

}
