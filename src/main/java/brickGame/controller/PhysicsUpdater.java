package brickGame.controller;

import brickGame.Sounds;
import brickGame.input.InputHandler;
import brickGame.labels.BonusLabel;
import brickGame.constants.GameConstants;
import brickGame.Main;
import brickGame.gameEngine.GameEngine;
import brickGame.stats.Stats;
import brickGame.gameObjects.ball.Ball;
import brickGame.gameObjects.bonus.Bonus;
import brickGame.gameObjects.breakpaddle.BreakPaddle;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;

import java.util.ArrayList;
import java.util.List;

public class PhysicsUpdater implements GameEngine.OnAction {
    private Main game;
    private Ball ball;
    private Pane root;
    private BreakPaddle breakPaddle;
    private ConcretePhysicsEngine concretePhysicsEngine;
    private Stats stats;
    private InputHandler inputHandler;

    public PhysicsUpdater(Main game, Ball ball, Pane root, ArrayList<Bonus> bonuses, BreakPaddle breakPaddle, ConcretePhysicsEngine concretePhysicsEngine, Stats stats, InputHandler inputHandler) {
        this.game = game;
        this.ball = ball;
        this.root = root;
        this.breakPaddle = breakPaddle;
        this.concretePhysicsEngine = concretePhysicsEngine;
        this.stats = stats;
        this.inputHandler = inputHandler;

    }

    @Override
    public void onUpdate() {

    }

    @Override
    public void onInit() {

    }

    public void onPhysicsUpdate() {
        game.checkDestroyedCount();
        concretePhysicsEngine.setPhysicsToBall();
        inputHandler.movePaddle();
        updateGoldStatus();
        updateChocos();

    }

    @Override
    public void onTime(long time) {

    }

    private void updateChocos() {
        synchronized (game.getChocos()) {
            List<Bonus> chocos = game.getChocos();
            List<Bonus> chocosToRemove = new ArrayList<>();

            for (int i = 0; i < chocos.size(); i++) {
                Bonus choco = chocos.get(i);
                if (shouldSkipChocoUpdate(choco)) {
                    continue;
                }
                handleChocoCollision(choco);
                updateChocoPosition(choco);

                if (choco.taken) {
                    chocosToRemove.add(choco);
                }
            }

            // Remove taken chocos from the game
            chocos.removeAll(chocosToRemove);

            // Remove chocos from the UI on the JavaFX thread
            Platform.runLater(() -> {
                for (Bonus choco : chocosToRemove) {
                    root.getChildren().remove(choco.choco);
                }
            });
        }
    }
    private void handleChocoCollision(Bonus choco) {
        if (choco.y >= breakPaddle.getyBreak() - 20 && choco.y <= breakPaddle.getyBreak()
                && choco.x >= breakPaddle.getxBreak() && choco.x <= breakPaddle.getxBreak() + GameConstants.BREAK_WIDTH.getIntValue()) {
            handleChocoCollisionWithPaddle(choco);
        }
    }
    private void handleChocoCollisionWithPaddle(Bonus choco) {

        BonusLabel bonuslabel = new BonusLabel(root);

        // Check if bonus is good or bad
        if (choco.isGood()) {
            Sounds.playSound("bonus-sound");
            bonuslabel.showMessage("+3! Bonus", choco.x, choco.y);
            game.setScore(game.getScore() + 3);
            new Stats().show(choco.x, choco.y, 3, game);
        } else {
            Sounds.playSound("lose-heart-sound");
            bonuslabel.showMessage("-2! Penalty", choco.x, choco.y);
            game.setScore(game.getScore() - 2);
            new Stats().show(choco.x, choco.y, -2, game);
        }

        // Mark choco as taken and hide it
        choco.taken = true;
        choco.choco.setVisible(false);
    }


    private void updateChocoPosition(Bonus choco) {
        choco.y += ((stats.getTime() - choco.timeCreated) / 1000.0) + 1.0;
        Platform.runLater(() -> choco.choco.setY(choco.y));
    }

    private boolean shouldSkipChocoUpdate(Bonus choco) {
        return choco.y > GameConstants.SCENE_HEIGHT.getIntValue() || choco.taken;
    }

    private void updateGoldStatus() {
        if (stats.getTime() - stats.getGoldTime() > 5000) {
            resetGoldStatus();
        }
    }

    private void resetGoldStatus() {
        Platform.runLater(() -> {
            if (root != null) {
                root.getStyleClass().remove("goldRoot");
            }
            ball.setFill(new ImagePattern(new Image("ball.jpg")));
            root.getStyleClass().remove("goldRoot");
            game.setGoldStatus(false);
        });
    }
}
