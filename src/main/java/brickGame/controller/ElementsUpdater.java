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
import java.util.Random;
/**
 * The ElementsUpdater class manages the updating of game elements such as the UI, block collisions, and bonuses.
 */
public class ElementsUpdater implements GameEngine.OnAction {

    private Main game;
    private BreakPaddle breakPaddle;
    private Ball ball;
    private ConcretePhysicsEngine concretePhysicsEngine;
    private Pane root;
    private Stats stats;


    private Timeline chocoListTimeline;

    /**
     * Constructs an instance of the ElementsUpdater class.
     *
     * @param game                 The main game instance.
     * @param breakPaddle          The BreakPaddle instance.
     * @param ball                 The Ball instance.
     * @param concretePhysicsEngine The ConcretePhysicsEngine instance.
     * @param root                 The JavaFX root pane.
     * @param stats                The Stats instance.
     */
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

    /**
     * Handles the update of UI elements and block collisions, by calling the handBlockCollisions method
     */
    public void onUpdate() {
        Platform.runLater(this::updateUI);
        handleBlockCollisions();

    }

    /**
     * Updates the UI elements like:
     * score, heart labels, BreakPaddle, and Ball position
     */
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

    /**
     * Handles collisions between the ball and blocks.
     * Synchronizes access to the game's blocks list.
     *
     *  Increases the score on block hit.
     *  Handles specific block hit based on the hit code.
     *
     * iterates through a synchronized copy of the game's blocks,
     * checks for collisions with the ball
     * processes the collisions by adjusting score and handling specific block hits based on the hit code
     * Sets booleans accordingly
     */
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
                        concretePhysicsEngine.resetCollideFlags();
                        ball.setGoDownBall(false);
                        return;
                    }
                    // bot collision
                    if (hitCode == GameConstants.HIT_BOTTOM.getIntValue()) {
                        ball.setGoDownBall(true);
                        return;
                    }
                    // right collision
                    if (hitCode == GameConstants.HIT_RIGHT.getIntValue()) {
                        ball.setGoRightBall(true);
                        return;
                    }
                    // left collision
                    if (hitCode == GameConstants.HIT_LEFT.getIntValue()) {
                        ball.setGoRightBall(false);
                        return;
                    }
                }
            }
        }
    }

    /**
     * Handles impact of the ball hitting a block.
     * Shows the score, hides the block, updates the game state.
     *
     * - If the block's durability is less than or equal to 0:
     *   - Hides the block's rectangle view.
     *   - Marks the block as destroyed.
     *   - Increments the destroyed block count in game statistics.
     *   - Resets collision flags in the concrete physics engine.
     * - If the block's durability is greater than 0:
     *   - Decrements the block's durability.
     *
     * Plays a bounce sound effect for the normal block
     * Handles specific actions based on the type of block:
     *   - Choco Block - Directs to handleChocoBlockHit(block);
     *   - Star Block: - Directs to handleStarBlockHit()
     *   - Heart Block: Increments the player's heart count.
     *   - Sturdy Block: Plays a sturdy sound effect.
     *   - Thunder Block - Directs to handleThunderBlockHit()
     *
     * @param block The block that was hit by the ball.
     */
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
            stats.increaseHeart();
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

    /**
     * Handles the impact of the ball hitting a Choco Block.
     * Determines if the bonus obtained from the Choco Block is good bonus or not.
     * Creates a Bonus object representing the Choco Block's bonus.
     * Adds bonus to the UI and updates the game state.
     *
     * @param block The Choco Block that was hit by the ball.
     */
    private void handleChocoBlockHit(Block block) {
        boolean isGood = determineIfGoodBonus();

        final Bonus choco = new Bonus(block.row, block.column, isGood);
        choco.timeCreated = stats.getTime();

        // Use synchronized block to add choco to UI and update game state
        synchronized (game.getChocos()) {
            Platform.runLater(() -> root.getChildren().add(choco.choco));
            game.getChocos().add(choco);
        }
    }

    /**
     * Determines if a bonus is good or bad (true or false randomly).
     *
     * @return True if it's a good bonus, false otherwise.
     */
    private boolean determineIfGoodBonus() {
            return new Random().nextBoolean(); // Returns true or false randomly
    }

    /**
     * Handles hit to a thunder block in the game.
     * Increases the game's score by 10 points.
     * Displays label with the text "Thunder Block +10 points" at coordinates (200, 300) on the game's root.
     * Animates screen shaking effect using a Timeline with KeyFrames.
     * Resets  translation properties of the game's root after the animation.
     * Adds a PauseTransition for 2 seconds to wait before removing the thunder label from the UI.
     */
    private void handleThunderBlockHit() {
        // Increase the game's score by 10 points

        game.setScore(game.getScore() + 10);

        double originalTranslateX = root.getTranslateX();
        double originalTranslateY = root.getTranslateY();

        Label thunderLabel = new Label("Thunder Block +10 points");
        thunderLabel.setTranslateX(200);
        thunderLabel.setTranslateY(300);
        root.getChildren().add(thunderLabel);

        // Animate a screen shaking effect using a Timeline
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0.1), new KeyValue(root.translateXProperty(), originalTranslateX - 5)),
                new KeyFrame(Duration.seconds(0.2), new KeyValue(root.translateYProperty(), originalTranslateY + 5)),
                new KeyFrame(Duration.seconds(0.3), new KeyValue(root.translateXProperty(), originalTranslateX + 5)),
                new KeyFrame(Duration.seconds(0.4), new KeyValue(root.translateYProperty(), originalTranslateY - 5)),
                new KeyFrame(Duration.seconds(0.5), new KeyValue(root.translateXProperty(), originalTranslateX))
        );

        // Set an event handler when the animation finishes
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

    /**
     * Handles the hit to a star block in the game.
     * Plays the "star-block-sound" sound effect.
     * Sets the gold time in the game's statistics to the current time.
     * Sets the ball's image to the gold ball image.
     * Adds the "goldRoot" style class to the game's root element.
     * Updates the gold status boolto true.
     * Increases the ball's velocity by doubling both X and Y components.
     * Uses an Animation Timeline to reset the gold status after a certain duration.
     */
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

    /**
     * Resets the gold status in the game.
     * Removes "goldRoot" style class from the game's root element.
     * Adjusts ball's velocity back to normal values.
     * Sets  ball's image back to the normal ball image.
     * Clears the "goldRoot" style class from the game root.
     * Updates game's gold status bool to false.
     */
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

    /**
     * Updates  list of Choco bonuses in the game.
     * Iterates through list of Choco bonuses and removes those that have been taken.
     * Removes  corresponding UI elements from  JavaFX thread.
     */
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
