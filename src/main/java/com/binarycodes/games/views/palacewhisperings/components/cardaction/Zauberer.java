package com.binarycodes.games.views.palacewhisperings.components.cardaction;

import com.binarycodes.games.views.palacewhisperings.service.GameController;
import com.binarycodes.games.views.palacewhisperings.service.Player;
import com.vaadin.flow.component.Component;

public class Zauberer extends ActionDialog {

    public Zauberer(final GameController gameController, final Player player) {
        super(gameController, player);
    }

    @Override
    public Component content() {
        return null;
    }

}
