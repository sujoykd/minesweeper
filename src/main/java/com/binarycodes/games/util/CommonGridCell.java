package com.binarycodes.games.util;

import java.util.Objects;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.theme.lumo.LumoUtility.AlignItems;
import com.vaadin.flow.theme.lumo.LumoUtility.Border;
import com.vaadin.flow.theme.lumo.LumoUtility.Display;
import com.vaadin.flow.theme.lumo.LumoUtility.JustifyContent;

public abstract class CommonGridCell extends Div {
    private static final String SIZE = "35px";

    private final int rowNum;
    private final int colNum;

    public CommonGridCell(final int rowNum, final int colNum) {
        this.rowNum = rowNum;
        this.colNum = colNum;

        this.getStyle().setHeight(SIZE);
        this.getStyle().setWidth(SIZE);
        this.getStyle().setCursor("pointer");
        this.addClassNames(Display.FLEX, JustifyContent.CENTER, AlignItems.CENTER);

        this.style();
    }

    protected void size(final String cssHeight, final String cssWidth) {
        this.getStyle().setHeight(cssHeight);
        this.getStyle().setWidth(cssWidth);
    }

    protected void style() {
        this.addClassNames(Border.ALL);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.colNum, this.rowNum);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof final CommonGridCell other)) {
            return false;
        }
        return this.colNum == other.colNum && this.rowNum == other.rowNum;
    }

    public int getRowNum() {
        return this.rowNum;
    }

    public int getColNum() {
        return this.colNum;
    }
}
