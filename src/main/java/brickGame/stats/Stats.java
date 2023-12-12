package brickGame.stats;

import brickGame.Sounds;
import brickGame.constants.GameConstants;
import brickGame.Main;
import brickGame.labels.ScoreLabel;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;

/**
 * Stats class has properties and methods about the game statistics.
 * Original class implementation
 * {@linkplain <a href="https://github.com/kooitt/CourseworkGame/blob/master/src/main/java/brickGame/Score.java">Score.java</a>} file.
 */
public class Stats {
    private int destroyedBlockCount = 0;
    private int  heart    = 0;
    private long time = 0;
    private long hitTime  = 0;
    private long goldTime = 0;

    /**
     * Gets the count of destroyed blocks.
     *
     * @return The count of destroyed blocks.
     */
    public int getDestroyedBlockCount() {
        return destroyedBlockCount;
    }

    /**
     * Sets the count of destroyed blocks.
     *
     * @param destroyedBlockCount The count of destroyed blocks.
     */
    public void setDestroyedBlockCount(int destroyedBlockCount) {
        this.destroyedBlockCount = destroyedBlockCount;
    }

    /**
     * Gets the current heart count.
     *
     * @return The current heart count.
     */
    public int getHeart() {
        return heart;
    }

    /**
     * Sets the heart count.
     *
     * @param heart The heart count to set.
     */
    public void setHeart(int heart) {
        this.heart = heart;
    }

    /**
     * Decreases the heart count by 1.
     */
    public void decreaseHeart() {
        this.heart -= 1;
    }

    /**
     * Increases the heart count by 1.
     */
    public void increaseHeart() {
        this.heart += 1;
    }

    /**
     * Gets the current game time.
     *
     * @return The current game time.
     */
    public long getTime() {
        return time;
    }

    /**
     * Sets the game time.
     *
     * @param time The game time to set.
     */
    public void setTime(long time) {
        this.time = time;
    }

    /**
     * Gets the time of the last hit.
     *
     * @return The time of the last hit.
     */
    public long getHitTime() {
        return hitTime;
    }

    /**
     * Sets the time of the last hit.
     *
     * @param hitTime The time of the last hit.
     */
    public void setHitTime(long hitTime) {
        this.hitTime = hitTime;
    }

    /**
     * Gets the time when gold status was last updated.
     *
     * @return The time when gold status was last updated.
     */
    public long getGoldTime() {
        return goldTime;
    }

    /**
     * @param goldTime
     * sets goldTime
     */
    public void setGoldTime(long goldTime){
        this.goldTime = goldTime;
    }

    /**
     * Shows score label on the screen with scale and opacity animation.
     *
     * @param x     The x-coordinate of the label.
     * @param y     The y-coordinate of the label.
     * @param score The score to display.
     * @param main  The Main class instance.
     */
    public void show(final double x, final double y, int score, final Main main) {
        String sign = (score >= 0) ? "+" : "";
        Label label = ScoreLabel.createLabel(sign + score, x, y, main);
        Platform.runLater(() -> main.root.getChildren().add(label));

        Timeline timeline = new Timeline();
        for (int i = 0; i < 21; i++) {
            int finalI = i;
            KeyFrame keyFrame = new KeyFrame(Duration.millis(i * 15), event -> {
                label.setScaleX(finalI);
                label.setScaleY(finalI);
                label.setOpacity((20 - finalI) / 20.0);
            });
            timeline.getKeyFrames().add(keyFrame);
        }
        timeline.play();
    }

    /**
     * Shows message label on the screen with scale and opacity animation.
     *
     * @param message The message to display.
     * @param main    The Main class instance.
     */
    public void showMessage(String message, final Main main) {
        Label label = ScoreLabel.createLabel(message, 220, 340, main);
        Platform.runLater(() -> main.root.getChildren().add(label));


        Timeline timeline = new Timeline();
        for (int i = 0; i < 30; i++) {
            int finalI = i;
            KeyFrame keyFrame = new KeyFrame(Duration.millis(i * 20), event -> {
                label.setScaleX(Math.abs(finalI - 15));
                label.setScaleY(Math.abs(finalI - 15));
                label.setOpacity((30 - finalI) / 30.0);
            });
            timeline.getKeyFrames().add(keyFrame);
        }

        timeline.setOnFinished(event -> {
            label.setOpacity(0.0); // opacity is  zero when the animation is finished
        });

        timeline.play();

    }

    /**
     * Shows the game over message on the screen along with a restart button.
     *
     * @param main The Main class instance.
     * @param num  The identifier for game over or times up message.
     */
    public void showGameOver(final Main main, int num) {
        Platform.runLater(() -> {

            Sounds.playSound("game-over-sound");
            Sounds.stopBackgroundMusic();

            String text;
            if (num == 1) {
                text = GameConstants.GAME_OVER_MESSAGE.getStringValue();
            } else {
                text = GameConstants.TIMES_UP_MESSAGE.getStringValue();
            }
            Label label = ScoreLabel.createLabel(text, 200, 250, main);
            label.setScaleX(2);
            label.setScaleY(2);

            Button restart = ScoreLabel.createButton(GameConstants.RESTART_MESSAGE.getStringValue(), 220, 300, main);
            restart.setOnAction(event -> main.restartGameLevel());

            main.root.getChildren().addAll(label, restart);
        });
    }


    /**
     * Shows the win message on the screen along with the final score and a restart button.
     * Restart button makes the game start again
     *
     * @param main The Main class instance.
     */
    public void showWin(final Main main) {
        Platform.runLater(() -> {
            Label label = ScoreLabel.createLabel(GameConstants.WIN_MESSAGE.getStringValue(), 200, 220, main);
            label.setScaleX(2);
            label.setScaleY(2);

            Label finalScore = ScoreLabel.createLabel("Your Score: " + String.valueOf(main.getScore()), 200, 250, main);
            finalScore.setScaleX(2);
            finalScore.setScaleY(2);
            Button restart = ScoreLabel.createButton(GameConstants.RESTART_MESSAGE.getStringValue(), 220, 300, main);
            restart.setOnAction(event -> main.restartGameLevel());

            main.root.getChildren().addAll(label, restart, finalScore);
        });
    }
}
