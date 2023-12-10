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

public class Stats {


    public int getDestroyedBlockCount() {
        return destroyedBlockCount;
    }

    public void setDestroyedBlockCount(int destroyedBlockCount) {
        this.destroyedBlockCount = destroyedBlockCount;
    }

    private int destroyedBlockCount = 0;

    public int getHeart() {
        return heart;
    }

    public void setHeart(int heart) {
        this.heart = heart;
    }
    private int  heart    = 3;

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    private long time = 0;

    public long getHitTime() {
        return hitTime;
    }

    public void setHitTime(long hitTime) {
        this.hitTime = hitTime;
    }

    private long hitTime  = 0;


    public long getGoldTime() {
        return goldTime;
    }

    public void setGoldTime(long goldTime) {
        this.goldTime = goldTime;
    }

    private long goldTime = 0;


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

    public void showGameOver(final Main main, int num) {
        Platform.runLater(() -> {

            Sounds sounds = new Sounds();
            sounds.playSound("game-over-sound");
            sounds.stopBackgroundMusic();

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


    public void showWin(final Main main) {
        Platform.runLater(() -> {
            Label label = ScoreLabel.createLabel(GameConstants.WIN_MESSAGE.getStringValue(), 200, 250, main);
            label.setScaleX(2);
            label.setScaleY(2);

            Button restart = ScoreLabel.createButton(GameConstants.RESTART_MESSAGE.getStringValue(), 220, 300, main);
            restart.setOnAction(event -> main.restartGameLevel());

            main.root.getChildren().addAll(label, restart);
        });
    }
}
