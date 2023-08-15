package com.binarycodes.games.views.palacewhisperings.components.cardaction;

import com.binarycodes.games.views.palacewhisperings.components.CardView;
import com.binarycodes.games.views.palacewhisperings.service.GameController;
import com.binarycodes.games.views.palacewhisperings.service.Player;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class Hofmarschall extends ActionDialog {
    
    public Hofmarschall(final GameController gameController, final Player player) {
        super(gameController, player);
    }
    
    @Override
    public Component content() {
        final var kingCard = this.getGameController().newKing();
        final var cardView = new CardView(kingCard);
        final var submitBtn = new Button("Ok", event -> {
            this.close();
        });
        return new VerticalLayout(cardView, submitBtn);
    }
    
}
