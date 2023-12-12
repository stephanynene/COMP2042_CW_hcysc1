package brickGame.timer;

import brickGame.Main;
import brickGame.gameEngine.GameEngine;
import brickGame.stats.Stats;
import javafx.application.Platform;
import javafx.scene.control.Label;

/**
 * The Timer class represents the timer functionality in the game, tracking elapsed time,
 * game time limit, and updating the countdown timer UI element.
 *
 * Used to manage time-related aspects of the game, including tracking
 * the elapsed time since the game started, the remaining time until the game ends, and handling
 * the game over event when the time limit is reached.
 * <
 */
public class Timer {    private GameEngine gameEngine;
    private long gameTimeLimit; // Time limit for game
    private long gameStartTime; // Time when game started
    private long elapsedTime;
    private long remainingSeconds;

    /**
     * Sets the game engine timer for tracking elapsed time.
     *
     * @param gameEngine The game engine instance.
     */
    public void setGameEngineTimer(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
    }

    /**
     * Gets the game time limit.
     *
     * @return The game time limit in seconds.
     */
    public long getGameTimeLimit() {
        return gameTimeLimit;
    }

    /**
     * Sets the game time limit.
     *
     * @param gameTimeLimit The game time limit in seconds.
     */
    public void setGameTimeLimit(long gameTimeLimit) {
        this.gameTimeLimit = gameTimeLimit;
    }

    /**
     * Gets the game start time.
     *
     * @return The timestamp when the game started.
     */
    public long getGameStartTime() {
        return gameStartTime;
    }

    /**
     * Sets the game start time.
     *
     * @param gameStartTime The timestamp when the game started.
     */
    public void setGameStartTime(long gameStartTime) {
        this.gameStartTime = gameStartTime;
    }

    /**
     * Gets the elapsed time since the game started.
     *
     * @return The elapsed time in milliseconds.
     */
    public long getElapsedTime() {
        return elapsedTime;
    }

    /**
     * Sets the elapsed time since the game started.
     *
     * @param elapsedTime The elapsed time in milliseconds.
     */
    public void setElapsedTime(long elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    /**
     * Gets the remaining seconds until the game ends.
     *
     * @return The remaining seconds.
     */
    public long getRemainingSeconds() {
        return remainingSeconds;
    }

    /**
     * Sets the remaining seconds until the game ends.
     *
     * @param remainingSeconds The remaining seconds.
     */
    public void setRemainingSeconds(long remainingSeconds) {
        this.remainingSeconds = remainingSeconds;
    }

    /**
     * Handles the game over event when time is up.
     * Makes the game engine stop
     *
     * @param main The main game instance.
     */
    public void timeUpGameOver(final Main main) {
        Platform.runLater(() -> {
            new Stats().showGameOver(main, 0);
            gameEngine.stop();
        });
    }

    /**
     * Updates the countdown timer UI element.
     *
     * @param remainingTimeMillis The remaining time in milliseconds.
     * @param countdownLabel      The label to display the countdown.
     */
    public void updateCountdownTimer(long remainingTimeMillis, Label countdownLabel) {
         remainingSeconds = remainingTimeMillis / 1000;

        // Update countdown timer UI element
        Platform.runLater(() -> countdownLabel.setText("Timer: " + remainingSeconds + "s"));
    }
}
