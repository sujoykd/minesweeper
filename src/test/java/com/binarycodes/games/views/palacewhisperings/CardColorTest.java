package com.binarycodes.games.views.palacewhisperings;

import org.junit.jupiter.api.Test;

import com.binarycodes.games.views.palacewhisperings.service.CardColor;
import com.binarycodes.games.views.palacewhisperings.service.CardType;

class CardColorTest {

    @Test
    void test() {
        final var text = "convert original/%s.png -colorspace gray -fill \"%s\" -tint 100 %s.png";

        for (final var color : CardColor.values()) {
            for (final var type : CardType.values()) {
                if (type.isPlayerType()) {
                    final var imageName = type.name().toLowerCase();
                    final var colorName = color.name().toLowerCase();
                    final var finalName = imageName + "_" + colorName;
                    final var output = String.format(text, imageName, color.getTintShade(), finalName);
                    System.out.println(output);
                }
            }
        }
    }

}
