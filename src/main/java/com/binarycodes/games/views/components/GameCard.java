package com.binarycodes.games.views.components;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.orderedlayout.FlexLayout;

@JsModule("./component/game-card.ts")
@Tag("game-card")
public class GameCard extends FlexLayout {

    public GameCard(final String cardTitle) {
        this.update(cardTitle);
    }

    public void update(final String cardTitle) {
        this.getElement().setProperty("cardTitle", cardTitle);
    }
}
