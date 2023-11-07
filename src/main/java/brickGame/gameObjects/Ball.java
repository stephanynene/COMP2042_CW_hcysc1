package brickGame.gameObjects;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.util.Random;

public class Ball {

    private Circle ball;
    private double xBall;
    private double yBall;
    private int ballRadius = 10;

    public int sceneWidth = 500;
    private int sceneHeigt = 700;
    private void initBall(int level) {
        Random random = new Random();
        xBall = random.nextInt(sceneWidth) + 1;
        yBall = random.nextInt(sceneHeigt - 200) + ((level + 1) * Block.getHeight()) + 15;
        ball = new Circle();
        ball.setRadius(ballRadius);
        ball.setFill(new ImagePattern(new Image("ball.png")));
    }
}
