package com.binarycodes.games.views.palacewhisperings.components.cardaction;

import com.binarycodes.games.views.palacewhisperings.service.GameController;
import com.binarycodes.games.views.palacewhisperings.service.Player;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.dialog.Dialog;

public abstract class ActionDialog extends Dialog {

    private final GameController gameController;
    private final Player player;

    public ActionDialog(final GameController gameController, final Player player) {
        this.gameController = gameController;
        this.player = player;

        this.setCloseOnOutsideClick(false);
        this.setCloseOnEsc(false);
        this.add(this.content());
    }

    public GameController getGameController() {
        return this.gameController;
    }

    public Player getPlayer() {
        return this.player;
    }

    public abstract Component content();

}
