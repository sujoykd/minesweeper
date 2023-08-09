package com.binarycodes.games.views.cards;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class CardStack extends HorizontalLayout {

    public CardStack() {
        this.setSpacing(false);
        this.setPadding(false);

        this.getStyle().setPadding("5em");

        for (int i = 1; i <= 13; i++) {
            this.add(this.createCard("Card " + i));
        }
    }

    private Component createCard(final String titleText) {
        return new GameCard(titleText);
    }

}
