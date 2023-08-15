package com.binarycodes.games.util;

import java.util.HashMap;

import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import com.vaadin.flow.component.Component;

public final class GameUtil {

    public static HashMap<Class<? extends Component>, Game> findAllGames() {
        final ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
        provider.addIncludeFilter(new AnnotationTypeFilter(Game.class));

        final var beanDefinitions = provider.findCandidateComponents("com.binarycodes.games");
        final var gamesMap = new HashMap<Class<? extends Component>, Game>();
        for (final var bd : beanDefinitions) {
            try {
                final Class<?> clazz = Class.forName(bd.getBeanClassName());
                if (Component.class.isAssignableFrom(clazz)) {
                    final var annotation = clazz.getAnnotation(Game.class);
                    gamesMap.put(clazz.asSubclass(Component.class), annotation);
                }
            } catch (final ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return gamesMap;
    }
}
