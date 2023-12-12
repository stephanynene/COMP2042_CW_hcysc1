package brickGame.controller;

import brickGame.Main;
import brickGame.Sounds;
import brickGame.constants.GameConstants;
import brickGame.gameEngine.GameEngine;
import brickGame.gameObjects.ball.Ball;
import brickGame.gameObjects.block.Block;
import brickGame.gameObjects.bonus.Bonus;
import brickGame.stats.Stats;
import brickGame.gameObjects.breakpaddle.BreakPaddle;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class ElementsUpdater implements GameEngine.OnAction {

    private Main game;
    private BreakPaddle breakPaddle;
    private Ball ball;
    private ConcretePhysicsEngine concretePhysicsEngine;
    private Pane root;
    private Stats stats;


    private Timeline chocoListTimeline;

    public ElementsUpdater(Main game, BreakPaddle breakPaddle, Ball ball, ConcretePhysicsEngine concretePhysicsEngine, Pane root, Stats stats) {
        this.game = game;
        this.breakPaddle = breakPaddle;
        this.ball = ball;
        this.concretePhysicsEngine = concretePhysicsEngine;
        this.root = root;
        this.stats = stats;

        chocoListTimeline = new Timeline(new KeyFrame(Duration.millis(16), event -> updateChocoList()));
        chocoListTimeline.setCycleCount(Timeline.INDEFINITE);
        chocoListTimeline.play();
    }

    public void onUpdate() {
        Platform.runLater(this::updateUI);
        handleBlockCollisions();

    }


    private void updateUI() {
        game.updateScoreLabel(game.getScore());
        game.updateHeartLabel(stats.getHeart());

        // Update positions of UI elements
        breakPaddle.rect.setX(breakPaddle.getxBreak());
        breakPaddle.rect.setY(breakPaddle.getyBreak());
        ball.getBallView().setCenterX(ball.getxBall());
        ball.getBallView().setCenterY(ball.getyBall());

        // Update positions of bonus item
        updateChocoList();
    }


    private void handleBlockCollisions() {
        // Synchronize access to the game's blocks list
        synchronized (game.getBlocks()) {
            List<Block> blocksCopy = new ArrayList<>(game.getBlocks());
            for (Block block : blocksCopy) {
                int hitCode = block.checkHitToBlock(ball.getxBall(), ball.getyBall());
                if (hitCode != GameConstants.NO_HIT.getIntValue()) {
                    // Increase the score and handle the specific block hit
                    game.setScore(game.getScore() + 1);
                    handleBlockHit(block);

                    // top collision
                    if (hitCode == GameConstants.HIT_TOP.getIntValue()) {
//                        System.out.println("top");
                        concretePhysicsEngine.resetCollideFlags();
                        ball.setGoDownBall(false);
                        return;
                    }
                    // bot collision
                    if (hitCode == GameConstants.HIT_BOTTOM.getIntValue()) {
//                        System.out.println("bot");
                        ball.setGoDownBall(true);
                        return;
                    }
                    // right collision
                    if (hitCode == GameConstants.HIT_RIGHT.getIntValue()) {
//                        System.out.println("right");
                        ball.setGoRightBall(true);
                        return;
                    }
                    // left collision
                    if (hitCode == GameConstants.HIT_LEFT.getIntValue()) {
//                        System.out.println("left");
                        ball.setGoRightBall(false);
                        return;
                    }
                }
            }
        }
    }


    private void handleBlockHit(Block block) {
        // Show score, hide block, and update game state
        new Stats().show(block.x, block.y, 1, game);
        if (block.getDurability() <= 0) {
            block.getBlockView().getRect().setVisible(false);
            block.isDestroyed = true;
            stats.setDestroyedBlockCount(stats.getDestroyedBlockCount() + 1);
            concretePhysicsEngine.resetCollideFlags();
        } else {
            block.setDurability(block.getDurability() - 1);
        }

        Sounds.playBounceSound();


        // Handle different block types
        if (block.type == GameConstants.BLOCK_CHOCO.getIntValue()) {
            handleChocoBlockHit(block);
        } else if (block.type == GameConstants.BLOCK_STAR.getIntValue()) {
            Sounds.stopBounceSound();
            handleStarBlockHit();
        } else if (block.type == GameConstants.BLOCK_HEART.getIntValue()) {
            stats.setHeart(stats.getHeart() + 1);
            Sounds.playSound("gain-heart-sound");
        } else if (block.type == GameConstants.BLOCK_STURDY.getIntValue()) {
            Sounds.stopBounceSound();
            Sounds.playSound("sturdy-sound");
        } else if (block.type == GameConstants.BLOCK_THUNDER.getIntValue()) {
            Sounds.stopBounceSound();
            Sounds.playSound("thunder-sound");
            handleThunderBlockHit();
        }
    }

    // Handle hit to choco block
    private void handleChocoBlockHit(Block block) {
        final Bonus choco = new Bonus(block.row, block.column);
        choco.timeCreated = stats.getTime();

        // Use synchronized block to add choco to UI and update game state
        synchronized (game.getChocos()) {
            Platform.runLater(() -> root.getChildren().add(choco.choco));
            game.getChocos().add(choco);
        }
    }


    private void handleThunderBlockHit() {
        game.setScore(game.getScore() + 10);

        double originalTranslateX = root.getTranslateX();
        double originalTranslateY = root.getTranslateY();

        Label thunderLabel = new Label("Thunder Block +10 points");
        thunderLabel.setTranslateX(200);
        thunderLabel.setTranslateY(300);
        root.getChildren().add(thunderLabel);

        // Animate a screen shaking
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0.1), new KeyValue(root.translateXProperty(), originalTranslateX - 5)),
                new KeyFrame(Duration.seconds(0.2), new KeyValue(root.translateYProperty(), originalTranslateY + 5)),
                new KeyFrame(Duration.seconds(0.3), new KeyValue(root.translateXProperty(), originalTranslateX + 5)),
                new KeyFrame(Duration.seconds(0.4), new KeyValue(root.translateYProperty(), originalTranslateY - 5)),
                new KeyFrame(Duration.seconds(0.5), new KeyValue(root.translateXProperty(), originalTranslateX))
        );

        timeline.setOnFinished(event -> {
            root.setTranslateX(originalTranslateX);
            root.setTranslateY(originalTranslateY);

            // PauseTransition for 2 seconds
            PauseTransition pause = new PauseTransition(Duration.seconds(2));
            pause.setOnFinished(pauseEvent -> {
                root.getChildren().remove(thunderLabel);
            });
            pause.play();
        });

        timeline.play();
    }




    private void handleStarBlockHit() {
        Platform.runLater(() -> {
            Sounds.playSound("star-block-sound");

            stats.setGoldTime(stats.getTime());
            ball.getBallView().setBallImage(GameConstants.GOLD_BALL);
            root.getStyleClass().add("goldRoot");
            game.setGoldStatus(true);
            ball.setVelocityX(ball.getVelocityX() * 2);
            ball.setVelocityY(ball.getVelocityY() * 2);

            // Use Animation Timeline to reset gold status after certain duration
            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(5000), event -> {
                resetGoldStatus();
            }));
            timeline.setCycleCount(1);  // Make sure it runs only once
            timeline.play();
        });
    }

    private void resetGoldStatus() {
        // Reset gold status
        Platform.runLater(() -> {
            if (root != null) {
                root.getStyleClass().remove("goldRoot");
            }
            ball.setVelocityX(ball.getVelocityX() / 2);
            ball.setVelocityY(ball.getVelocityY() / 2);
            ball.getBallView().setBallImage(GameConstants.NORMAL_BALL);
            root.getStyleClass().remove("goldRoot");
            game.setGoldStatus(false);
        });
    }

    private void updateChocoList() {
        synchronized (game.getChocos()) {
            List<Bonus> chocos = game.getChocos();
            for (int i = chocos.size() - 1; i >= 0; i--) {
                Bonus choco = chocos.get(i);
                if (choco.taken) {
                    // Remove taken choco from the game
                    chocos.remove(i);
                    // Remove choco from the UI on the JavaFX thread
                    Platform.runLater(() -> root.getChildren().remove(choco.choco));
                }
            }
        }
    }



    @Override
    public void onInit() {

    }

    @Override
    public void onPhysicsUpdate() {

    }

    @Override
    public void onTime(long time) {

    }
}
