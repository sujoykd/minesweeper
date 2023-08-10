package com.binarycodes.games.views.palacewhisperings;

import com.binarycodes.games.util.Game;
import com.binarycodes.games.util.GameIcon;
import com.binarycodes.games.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Game(title = "Palace Whisperings", icon = GameIcon.CARD_GAMES)
@Route(value = "palastgefluster", layout = MainLayout.class)
public class PalaceWhisperingsView extends VerticalLayout {

    private CardDeck deck;

    public PalaceWhisperingsView() {
        this.getStyle().setPadding("20px");
        this.setupNewGame();
    }

    private void setupNewGame() {
        this.deck = new CardDeck();

        this.removeAll();

        final var buttonLayout = new HorizontalLayout();
        buttonLayout.add(new Button("New Game", event -> {
            this.setupNewGame();
        }));

        final var gameSection = new VerticalLayout();
        for (int i = 1; i <= 5; i++) {
            final var name = "Player " + i;
            final var player = this.createPlayer(name, CardColor.values()[i - 1]);
            gameSection.add(new PlayerCardStack(player));
        }

        this.add(buttonLayout, gameSection);
    }

    private Player createPlayer(final String name, final CardColor color) {
        final var cards = this.deck.startingCardsForPlayer(color);
        return new Player(name, color, cards);
    }

}
