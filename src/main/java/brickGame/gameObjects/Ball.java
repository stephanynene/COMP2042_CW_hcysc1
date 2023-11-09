package brickGame.gameObjects;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;



import java.util.Random;

public class Ball extends Circle {

 //   private Circle ball;
    public int sceneWidth = 500;
    private int sceneHeigt = 700;
//    public Circle getBall() {
//        return ball;
//    }

//    public void setBall(Circle ball) {
//        this.ball = ball;
//    }


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


    public int getBallRadius() {
        return ballRadius;
    }

    public void setBallRadius(int ballRadius) {
        this.ballRadius = ballRadius;
    }

    private int ballRadius = 10;



    public void initBall(int level) {
        Random random = new Random();
        xBall = random.nextInt(sceneWidth) + 1;
        yBall = random.nextInt(sceneHeigt - 200) + ((level + 1) * Block.getHeight()) + 15;
        this.setRadius(ballRadius);
        this.setFill(new ImagePattern(new Image("/ball.png")));
        this.setCenterX(xBall);
        this.setCenterY(yBall);
    }

    private ImagePattern fill;

    public void setFill(ImagePattern imagePattern) {
        this.fill = imagePattern;
    }

  //  public ImagePattern getFill() {
  //      return fill;
   // }


}
