package brickGame.gameObjects;
import brickGame.constants.GameConstants;
import javafx.scene.shape.Circle;



import java.util.Random;

public class Ball extends Circle {

    private double xBall;
    private double yBall;
    private BallView ballView;
    public Ball(double radius) {
        this.ballView = new BallView(radius);
    }
    public void setxBall(double xBall) {
        this.xBall = xBall;
    }
    public double getxBall() {
        return xBall;
    }
    public void setyBall(double yBall) {
        this.yBall = yBall;
    }
    public double getyBall() {
        return yBall;
    }

    public void initBall(int level) {
        Random random = new Random();
        xBall = random.nextInt(GameConstants.SCENE_WIDTH.getIntValue()) + 1;
        yBall = random.nextInt(GameConstants.SCENE_HEIGHT.getIntValue() - 200) + ((level + 1) * Block.getHeight()) + 15;

        ballView.setBallImage(GameConstants.NORMAL_BALL);

        // Set other properties of the Circle (Ball)
        this.setRadius(GameConstants.BALL_RADIUS.getIntValue());
        this.setCenterX(xBall);
        this.setCenterY(yBall);

    }

    public BallView getBallView() {
        return ballView;
    }
}
