package brickGame.gameObjects.breakpaddle;
import brickGame.constants.GameConstants;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class BreakPaddle {
    private int halfBreakWidth = GameConstants.BREAK_WIDTH.getIntValue() / 2;
    private static final Duration ANIMATION_DURATION = Duration.millis(1);
    private BreakPaddleView breakPaddleView;

    private double xBreak = 0.0f;
    public Rectangle rect;
    private double centerBreakX;
    private double yBreak = 640.0f;
    public double getCenterBreakX() {
        return centerBreakX;
    }

    public void setCenterBreakX(double centerBreakX) {
        this.centerBreakX = centerBreakX;
    }


    public double getyBreak() {
        return yBreak;
    }

    public void setyBreak(double yBreak) {
        this.yBreak = yBreak;
    }

    public void setxBreak(double xBreak) {
        this.xBreak = xBreak;
    }
    public double getxBreak() {
        return xBreak;
    }

    public void initBreak() {
        rect = new Rectangle();
        breakPaddleView = new BreakPaddleView(rect, xBreak, yBreak);
    }

    public void moveRight() {
        animateMovement(GameConstants.BREAKPADDLE_SPEED.getIntValue(), GameConstants.SCENE_WIDTH.getIntValue() - GameConstants.BREAK_WIDTH.getIntValue());
    }

    public void moveLeft() {
        animateMovement(-GameConstants.BREAKPADDLE_SPEED.getIntValue(), GameConstants.SCENE_WIDTH.getIntValue() - GameConstants.BREAK_WIDTH.getIntValue());
    }

    private void animateMovement(double deltaX, double maxX) {
        Timeline timeline = new Timeline(
                new KeyFrame(ANIMATION_DURATION, e -> {
                    xBreak = Math.min(Math.max(xBreak + deltaX, 0), maxX);
                    centerBreakX = xBreak + halfBreakWidth;
                    updateBreakPaddleView();
                })
        );
        timeline.setCycleCount(1);
        timeline.play();
    }

    private void updateBreakPaddleView() {
        breakPaddleView.updatePosition(xBreak, yBreak);
    }
}