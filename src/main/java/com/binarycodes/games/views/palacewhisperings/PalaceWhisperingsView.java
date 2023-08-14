package com.binarycodes.games.views.palacewhisperings;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.binarycodes.games.util.Game;
import com.binarycodes.games.util.GameIcon;
import com.binarycodes.games.views.MainLayout;
import com.binarycodes.games.views.palacewhisperings.components.CardTableView;
import com.binarycodes.games.views.palacewhisperings.components.MessageBar;
import com.binarycodes.games.views.palacewhisperings.components.PlayerView;
import com.binarycodes.games.views.palacewhisperings.service.Card;
import com.binarycodes.games.views.palacewhisperings.service.CardColor;
import com.binarycodes.games.views.palacewhisperings.service.GameController;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Game(title = "Palace Whisperings", icon = GameIcon.CARD_GAMES)
@Route(value = "palastgefluster", layout = MainLayout.class)
public class PalaceWhisperingsView extends VerticalLayout {

    private GameController gameController;
    private Map<CardColor, PlayerView> playersViewMap;
    private CardTableView tableView;
    private MessageBar messageBar;

    public PalaceWhisperingsView() {
        this.getStyle().setPadding("20px");
        this.setupNewGame();
    }

    private void setupNewGame() {
        this.gameController = new GameController();
        this.removeAll();

        final var buttonLayout = new HorizontalLayout();
        buttonLayout.add(new Button("New Game", event -> {
            this.setupNewGame();
        }));

        final var gameSection = new VerticalLayout();
        gameSection.setPadding(false);

        this.playersViewMap = this.gameController.createAllPlayers()
                                                 .stream()
                                                 .map(player -> {
                                                     final var playerView = new PlayerView(player);
                                                     playerView.addCardPlayedListener(event -> {
                                                         this.handleCardPlay(event.getSource(), event.getCard());
                                                     });
                                                     gameSection.add(playerView);
                                                     return playerView;
                                                 })
                                                 .collect(Collectors.toMap(view -> view.getPlayer().getColor(), Function.identity()));

        // pick a random player to start the game
        final var color = this.gameController.pickPlayerColorToStartGame();
        this.playersViewMap.get(color).setVisible(true);

        this.tableView = new CardTableView(this.gameController);

        final var viewSection = new HorizontalLayout();
        viewSection.setWidthFull();
        viewSection.setHeightFull();
        viewSection.add(gameSection, this.tableView);

        this.messageBar = new MessageBar();
        gameSection.add(this.messageBar);

        this.add(buttonLayout, this.messageBar, viewSection);
    }

    private void handleCardPlay(final PlayerView currentPlayerView, final Card card) {
        this.tableView.update();

        final var player = currentPlayerView.getPlayer();

        final var messageText = "%s played %s %s".formatted(player.getName(), card.getColor().name().toLowerCase(), StringUtils.capitalize(card.getType().name().toLowerCase()));
        this.messageBar.update(messageText);

        final var actionDialog = this.tableView.nextAction(player, card);

        actionDialog.ifPresentOrElse(dialog -> {
            dialog.open();
            dialog.addOpenedChangeListener(event -> {
                if (!event.isOpened()) {
                    this.tableView.update();
                    currentPlayerView.update();
                    this.moveToNextPlayer(currentPlayerView, card);
                }
            });
        }, () -> this.moveToNextPlayer(currentPlayerView, card));
    }

    private void moveToNextPlayer(final PlayerView currentPlayerView, final Card card) {
        final var player = currentPlayerView.getPlayer();
        final var nextPlayerColor = this.gameController.nextPlayerColor(player, card);
        currentPlayerView.setVisible(false);
        this.playersViewMap.get(nextPlayerColor).setVisible(true);
    }

}
