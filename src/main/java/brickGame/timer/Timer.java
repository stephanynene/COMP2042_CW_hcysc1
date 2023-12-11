package brickGame.timer;

import brickGame.Main;
import brickGame.gameEngine.GameEngine;
import brickGame.stats.Stats;
import javafx.application.Platform;
import javafx.scene.control.Label;

public class Timer {    private GameEngine gameEngine;
    private long gameTimeLimit; // Time limit for game
    private long gameStartTime; // Time when game started
    private long elapsedTime;
    private long remainingSeconds;

    public void setGameEngineTimer(GameEngine gameEngine){
        this.gameEngine = gameEngine;
    }

    public long getGameTimeLimit() {
        return gameTimeLimit;
    }

    public void setGameTimeLimit(long gameTimeLimit) {
        this.gameTimeLimit = gameTimeLimit;
    }

    public long getGameStartTime() {
        return gameStartTime;
    }

    public void setGameStartTime(long gameStartTime) {
        this.gameStartTime = gameStartTime;
    }

    public long getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(long elapsedTime) {
        this.elapsedTime = elapsedTime;
    }


    public long getRemainingSeconds() {
        return remainingSeconds;
    }

    public void setRemainingSeconds(long remainingSeconds) {
        this.remainingSeconds = remainingSeconds;
    }



    public void timeUpGameOver(final Main main) {
        Platform.runLater(() -> {
            System.out.println("timer");
            new Stats().showGameOver(main, 0);
            gameEngine.stop();
        });
    }

    public void updateCountdownTimer(long remainingTimeMillis, Label countdownLabel) {
         remainingSeconds = remainingTimeMillis / 1000;

        // Update countdown timer UI element
        Platform.runLater(() -> countdownLabel.setText("Timer: " + remainingSeconds + "s"));
    }
}
