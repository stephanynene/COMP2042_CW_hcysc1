package brickGame.gameObjects;
import brickGame.GameConstants;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;



import java.util.Random;

public class Ball extends Circle {

    private double xBall;
    public void setxBall(double xBall) {
        this.xBall = xBall;
    }
    public double getxBall() {
        return xBall;
    }

    private double yBall;
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
        this.setRadius(GameConstants.BALL_RADIUS.getIntValue());
        this.setFill(new ImagePattern(new Image("/ball.png")));
        this.setCenterX(xBall);
        this.setCenterY(yBall);
    }
}
