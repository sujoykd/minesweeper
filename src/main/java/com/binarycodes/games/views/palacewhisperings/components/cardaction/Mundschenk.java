package com.binarycodes.games.views.palacewhisperings.components.cardaction;

import java.util.List;

import com.binarycodes.games.views.palacewhisperings.components.CardStackView;
import com.binarycodes.games.views.palacewhisperings.service.Card;
import com.binarycodes.games.views.palacewhisperings.service.CardType;
import com.binarycodes.games.views.palacewhisperings.service.Player;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class Mundschenk extends Dialog {

    private final Player player;
    private final List<Player> allPlayers;

    public Mundschenk(final Player player, final List<Player> allPlayers) {
        this.player = player;
        this.allPlayers = allPlayers;

        this.setCloseOnOutsideClick(false);
        this.setCloseOnEsc(false);

        this.init();
    }

    private void init() {
        final var layout = new VerticalLayout();

        layout.add(new Span("Choose a card to exchange"));
        final List<Card> cards = this.player.getDisplayedCards().stream().filter(card -> card.getType() != CardType.MUNDSCHENK).toList();
        final var playersDeck = new CardStackView(cards);
        layout.add(playersDeck);

        layout.add(new Span("Choose a card to exchange with"));

        this.allPlayers.stream()
                       .filter(p -> p != this.player)
                       .forEach(p -> {
                           final var stackView = new CardStackView(p.getDisplayedCards());
                           layout.add(stackView);
                       });

        this.add(layout);
    }
}
