package brickGame.controller;

import brickGame.constants.GameConstants;
import brickGame.Main;
import brickGame.gameEngine.GameEngine;
import brickGame.stats.Stats;
import brickGame.gameObjects.ball.Ball;
import brickGame.gameObjects.bonus.Bonus;
import brickGame.gameObjects.breakpaddle.BreakPaddle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class PhysicsUpdater implements GameEngine.OnAction {
    private Main game;
    private Ball ball;
    private Pane root;
    private Bonus bonus;
    private BreakPaddle breakPaddle;
    private ConcretePhysicsEngine concretePhysicsEngine;
    private Stats stats;
    private Label messageLabel;
    private Timeline chocoTimeline;

    public PhysicsUpdater(Main game, Ball ball, Pane root, ArrayList<Bonus> bonuses, BreakPaddle breakPaddle, ConcretePhysicsEngine concretePhysicsEngine, Stats stats) {
        this.game = game;
        this.ball = ball;
        this.root = root;
        this.breakPaddle = breakPaddle;
        this.concretePhysicsEngine = concretePhysicsEngine;
        this.stats = stats;

    }
    public void initUI() {
        messageLabel = new Label();
        root.getChildren().add(messageLabel);
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
        if (choco.y >= breakPaddle.getyBreak() && choco.y <= breakPaddle.getyBreak() + GameConstants.BREAK_WIDTH.getIntValue()
                && choco.x >= breakPaddle.getxBreak() && choco.x <= breakPaddle.getxBreak() + GameConstants.BREAK_WIDTH.getIntValue()) {
            handleChocoCollisionWithPaddle(choco);
        }
    }
    private void handleChocoCollisionWithPaddle(Bonus choco) {
//        System.out.println("You Got it and +3 score for you");
        String message = "Bonus! +3";

        // Display the message on the game screen
        showMessage(message, choco.x, choco.y);
        choco.taken = true;
        choco.choco.setVisible(false);
        game.setScore(game.getScore() +3 );
        new Stats().show(choco.x, choco.y, 3, game);
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
            ball.setFill(new ImagePattern(new Image("ball.png")));
            root.getStyleClass().remove("goldRoot");
            game.setGoldStatus(false);
        });
    }

    private void showMessage(String message, double x, double y) {
        // Set the message text and position
        messageLabel.setText(message);
//        messageLabel.setLayoutX(x);  // Adjust the position as needed
//        messageLabel.setLayoutY(y - 5);  // Adjust the position as needed

        // Center the message horizontally
        double labelWidth = messageLabel.prefWidth(-1);
        messageLabel.setLayoutX((root.getWidth() - labelWidth) / 2);

        // Center the message vertically
        messageLabel.setLayoutY(root.getHeight() / 2);


        // Set a timeline to remove the message after a certain duration
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1.5), event -> messageLabel.setText("")));
        timeline.setCycleCount(1);
        timeline.play();
    }

}
