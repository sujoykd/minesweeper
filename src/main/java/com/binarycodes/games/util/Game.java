package com.binarycodes.games.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.vaadin.flow.router.PageTitle;

import jakarta.validation.OverridesAttribute;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@PageTitle("")
public @interface Game {
    
    @OverridesAttribute(constraint = PageTitle.class, name = "value")
    String title();
    
    GameIcon icon();
}