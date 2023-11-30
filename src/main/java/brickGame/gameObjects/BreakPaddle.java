package brickGame.gameObjects;
import brickGame.constants.GameConstants;
import javafx.scene.shape.Rectangle;

public class BreakPaddle {

    private BreakPaddleView breakPaddleView;
    public void setxBreak(double xBreak) {
        this.xBreak = xBreak;
    }
    public double getxBreak() {
        return xBreak;
    }
    private double xBreak = 0.0f;

    public double getCenterBreakX() {
        return centerBreakX;
    }

    public void setCenterBreakX(double centerBreakX) {
        this.centerBreakX = centerBreakX;
    }

    private double centerBreakX;

    public double getyBreak() {
        return yBreak;
    }

    public void setyBreak(double yBreak) {
        this.yBreak = yBreak;
    }
    private double yBreak = 640.0f;
    private int halfBreakWidth = GameConstants.BREAK_WIDTH.getIntValue() / 2;

    public Rectangle rect;

    public BreakPaddleView getBreakPaddleView() {
        return breakPaddleView;
    }

    public void initBreak() {
        rect = new Rectangle();
        breakPaddleView = new BreakPaddleView(rect, xBreak, yBreak);
    }

    public void moveRight() {
        // Adjust speed as needed
        double speed = 10.0;
        xBreak = Math.min(xBreak + speed, GameConstants.SCENE_WIDTH.getIntValue() - GameConstants.BREAK_WIDTH.getIntValue());
        centerBreakX = xBreak + halfBreakWidth;
        updateBreakPaddleView();
    }

    public void moveLeft() {
        // Adjust speed as needed
        double speed = 10.0;
        xBreak = Math.max(xBreak - speed, 0);
        centerBreakX = xBreak + halfBreakWidth;
        updateBreakPaddleView();
    }

    private void updateBreakPaddleView() {
        breakPaddleView.updatePosition(xBreak, yBreak);
    }
}