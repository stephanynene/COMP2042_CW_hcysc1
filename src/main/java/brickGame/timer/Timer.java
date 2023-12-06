package brickGame.timer;

import brickGame.Main;
import brickGame.gameEngine.GameEngine;
import brickGame.stats.Stats;
import javafx.application.Platform;
import javafx.scene.control.Label;

public class Timer {

    private GameEngine gameEngine;

    public void setGameEngineTimer(GameEngine gameEngine){
        this.gameEngine = gameEngine;
    }
    private long gameTimeLimit; // Time limit for game

    public long getGameTimeLimit() {
        return gameTimeLimit;
    }

    public void setGameTimeLimit(long gameTimeLimit) {
        this.gameTimeLimit = gameTimeLimit;
    }
    private long gameStartTime; // Time when game started

    public long getGameStartTime() {
        return gameStartTime;
    }

    public void setGameStartTime(long gameStartTime) {
        this.gameStartTime = gameStartTime;
    }

    public void timeUpGameOver(final Main main) {
        Platform.runLater(() -> {
            System.out.println("timer");
            new Stats().showGameOver(main, 0);
            gameEngine.stop();
        });
    }

    public void updateCountdownTimer(long remainingTimeMillis, Label countdownLabel) {
        long remainingSeconds = remainingTimeMillis / 1000;

        // Update countdown timer UI element
        Platform.runLater(() -> countdownLabel.setText("Timer: " + remainingSeconds + "s"));
    }
}
