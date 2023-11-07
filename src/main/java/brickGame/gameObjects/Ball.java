package brickGame.gameObjects;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;



import java.util.Random;

public class Ball extends Circle {

    public Circle getBall() {
        return ball;
    }

    public void setBall(Circle ball) {
        this.ball = ball;
    }

    private Circle ball;



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


    private int ballRadius = 10;

    public int sceneWidth = 500;
    private int sceneHeigt = 700;

    public void initBall(int level) {
        Random random = new Random();
        xBall = random.nextInt(sceneWidth) + 1;
        yBall = random.nextInt(sceneHeigt - 200) + ((level + 1) * Block.getHeight()) + 15;
        ball = new Circle();
        ball.setRadius(ballRadius);
        ball.setFill(new ImagePattern(new Image("ball.png")));
    }

    private ImagePattern fill;

    public void setFill(ImagePattern imagePattern) {
        this.fill = imagePattern;
    }

  //  public ImagePattern getFill() {
  //      return fill;
   // }


}
