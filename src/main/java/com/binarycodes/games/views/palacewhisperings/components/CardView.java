package com.binarycodes.games.views.palacewhisperings.components;

import java.util.function.Consumer;

import com.binarycodes.games.views.palacewhisperings.service.Card;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.shared.Registration;

public class CardView extends Image {

    private final Card card;

    public CardView(final Card card) {
        super(card.imagePath(), card.toString());
        this.card = card;
        this.addClassName("card-image-view");
    }

    public Registration addCardClickListener(final Consumer<Card> listenerFn) {
        return this.addClickListener(event -> {
            listenerFn.accept(this.card);
        });
    }

}
