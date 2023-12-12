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

/**
 * PhysicsUpdater class responsible for handling physics updates in the game.
 * Implements the GameEngine.OnAction interface for callbacks during game loop.
 */
public class PhysicsUpdater implements GameEngine.OnAction {
    private Main game;
    private Ball ball;
    private Pane root;
    private BreakPaddle breakPaddle;
    private ConcretePhysicsEngine concretePhysicsEngine;
    private Stats stats;
    private InputHandler inputHandler;

    /**
     * Constructor for the PhysicsUpdater class.
     * @param game The main game instance.
     * @param ball The game ball.
     * @param root The JavaFX pane representing the game scene.
     * @param breakPaddle The BreakPaddle object.
     * @param concretePhysicsEngine The ConcretePhysicsEngine handling physics calculations.
     * @param stats The game statistics.
     * @param inputHandler The input handler for user interactions.
     */
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


    /**
     * Callback method for onPhysicsUpdate in the game loop.'
     * Contains many method calls
     */
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

    /**
     * - Update the positions and interactions of bonus items (Chocos) in the game.
     * - Synchronizes access to the list of Chocos and updates their positions and collisions.
     * - Iterates through the list of Chocos and performs the following actions for each Choco:
     *  Skips the Choco update if conditions from shouldSkipChocoUpdate method are met.
     *   - Handles collisions between the Choco and the game elements.
     *   - Updates the position of the Choco based on elapsed time.
     *   - Adds the Choco to the removal list if it has been taken.
     * - Removes the taken Chocos from the game.
     * - Removes Chocos from the UI on the JavaFX thread.
     */
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

    /**
     * - Handles the collision between a Choco and the BreakPaddle in the game.
     * - Shows a bonus label with appropriate messages based on the Choco type (good or bad).
     * - Plays corresponding sound effects for good or bad Chocos.
     * - Adjusts the game score based on the Choco type and updates the score label.
     * - Shows additional statistics messages for bonus or penalty scores.
     * - Marks the Choco as taken and hides it from the UI.
     */
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
            bonuslabel.showMessage("-1 heart! Penalty", choco.x, choco.y);
            stats.decreaseHeart();
            new Stats().show(choco.x, choco.y, -2, game);
        }

        // Mark choco as taken and hide it
        choco.taken = true;
        choco.choco.setVisible(false);
    }

    /**
     * - Updates position of a Choco on the screen based on its creation time and elapsed time.
     * - Uses Platform.runLater to safely update the Choco's position on the JavaFX thread.
     *
     * @param choco The Choco object to update its position.
     */
    private void updateChocoPosition(Bonus choco) {
        choco.y += ((stats.getTime() - choco.timeCreated) / 1000.0) + 1.0;
        Platform.runLater(() -> choco.choco.setY(choco.y));
    }

    /**
     * - Checks if Choco should be skipped during the update
     *
     * @param choco The Choco object to check.
     * @return True if the Choco should be skipped, false otherwise.
     */
    private boolean shouldSkipChocoUpdate(Bonus choco) {
        return choco.y > GameConstants.SCENE_HEIGHT.getIntValue() || choco.taken;
    }

    /**
     * - Checks if elapsed time since the last gold status is greater than 5000 milliseconds (5 seconds).
     * - Calls the resetGoldStatus method if the condition is met.
     */
    private void updateGoldStatus() {
        if (stats.getTime() - stats.getGoldTime() > 5000) {
            resetGoldStatus();
        }
    }

    /**
     * - Resets gold status, removing "goldRoot" style class and updating ball fill.
     * - Uses Platform.runLater to safely update the UI on the JavaFX thread.
     */
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
