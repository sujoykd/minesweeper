package com.binarycodes.games.views.palacewhisperings.components;

import com.binarycodes.games.views.palacewhisperings.service.Player;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class PlayerNameView extends HorizontalLayout {

    public PlayerNameView(final Player player) {
        final var displaySpan = new Span(player.getName());

        final var colorBadge = new Div();
        colorBadge.addClassName("card-player-color-badge");
        colorBadge.getStyle().set("background-color", player.getColor().getTintShade());

        this.add(displaySpan, colorBadge);
        this.addClassName("card-player-name-badge");
    }

}
