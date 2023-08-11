package com.binarycodes.games.views.palacewhisperings.components;

import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class MessageBar extends HorizontalLayout {

    public MessageBar() {
        this.setWidthFull();
        this.addClassName("message-bar");
        this.setVisible(false);
    }

    public void update(final String text) {
        this.removeAll();
        final var messageSpan = new Span(text);
        this.add(messageSpan);
        this.setVisible(true);
    }

}
