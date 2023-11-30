package brickGame.controller;

import brickGame.Main;
import brickGame.constants.GameConstants;
import brickGame.gameEngine.GameEngine;
import brickGame.gameObjects.Ball;
import brickGame.gameObjects.Block;
import brickGame.gameObjects.Bonus;
import brickGame.gameObjects.BreakPaddle;
import brickGame.scoring.Score;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class ElementsUpdater implements GameEngine.OnAction {

    private Main game;
    private BreakPaddle breakPaddle;
    private Ball ball;
    private ConcretePhysicsEngine concretePhysicsEngine;
    private Pane root;

    private Timeline chocoListTimeline;

    public ElementsUpdater(Main game, BreakPaddle breakPaddle, Ball ball, ConcretePhysicsEngine concretePhysicsEngine, Pane root) {
        this.game = game;
        this.breakPaddle = breakPaddle;
        this.ball = ball;
        this.concretePhysicsEngine = concretePhysicsEngine;
        this.root = root;

        chocoListTimeline = new Timeline(new KeyFrame(Duration.millis(16), event -> updateChocoList()));
        chocoListTimeline.setCycleCount(Timeline.INDEFINITE);
        chocoListTimeline.play();
    }

    public void onUpdate() {
        Platform.runLater(this::updateUI);
        if (isBallWithinBounds()) {
            handleBlockCollisions();
        }
    }


    private void updateUI() {
        game.updateScoreLabel(game.getScore());
        game.updateHeartLabel(game.getHeart());

        // Update positions of UI elements
        breakPaddle.rect.setX(breakPaddle.getxBreak());
        breakPaddle.rect.setY(breakPaddle.getyBreak());
        ball.getBallView().setCenterX(ball.getxBall());
        ball.getBallView().setCenterY(ball.getyBall());

        // Update positions of bonus item
        updateChocoList();
    }

    // Check if ball is within vertical game bounds
    private boolean isBallWithinBounds() {
        double paddingTop = Block.getPaddingTop();
        double ballY = ball.getyBall();
        double blockHeight = Block.getHeight();
        int level = game.getLevel();

        return ballY >= paddingTop && ballY <= (blockHeight * (level + 1)) + paddingTop;
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
                }
            }
        }
    }


    private void handleBlockHit(Block block) {
        // Show score, hide block, and update game state
        new Score().show(block.x, block.y, 1, game);
        block.getBlockView().getRect().setVisible(false);
        block.isDestroyed = true;
        game.setDestroyedBlockCount(game.getDestroyedBlockCount() + 1);
        concretePhysicsEngine.resetCollideFlags();

        // Handle different block types
        if (block.type == GameConstants.BLOCK_CHOCO.getIntValue()) {
            handleChocoBlockHit(block);
        } else if (block.type == GameConstants.BLOCK_STAR.getIntValue()) {
            handleStarBlockHit();
        } else if (block.type == GameConstants.BLOCK_HEART.getIntValue()) {
            game.setHeart(game.getHeart() + 1);
        }
    }

    // Handle hit to choco block
    private void handleChocoBlockHit(Block block) {
        final Bonus choco = new Bonus(block.row, block.column);
        choco.timeCreated = game.getTime();

        // Use synchronized block to add choco to UI and update game state
        synchronized (game.getChocos()) {
            Platform.runLater(() -> root.getChildren().add(choco.choco));
            game.getChocos().add(choco);
        }
    }


    private void handleStarBlockHit() {
        Platform.runLater(() -> {

            game.setGoldTime(game.getTime());
            ImagePattern imagePattern = new ImagePattern(new Image("goldball.png"));
            ball.setFill(imagePattern);
            Image goldBallImage = new Image("goldball.png");
            root.getStyleClass().add("goldRoot");
            game.setGoldStatus(true);

            // Use Animation Timeline to reset gold status after a certain duration
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
            ball.setFill(new ImagePattern(new Image("ball.png")));
            root.getStyleClass().remove("goldRoot");
            game.setGoldStatus(false);
        });
    }
    // Update position of chocos
//    private void updateChocoList() {
//        synchronized (game.getChocos()) {
//            Iterator<Bonus> iterator = game.getChocos().iterator();
//            while (iterator.hasNext()) {
//                Bonus choco = iterator.next();
//                if (choco.taken) {
//                    // Remove taken choco from the game
//                    iterator.remove();
//                    // Remove choco from the UI on the JavaFX thread
//                    Platform.runLater(() -> root.getChildren().remove(choco.choco));
//                }
//            }
//        }
//    }

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
