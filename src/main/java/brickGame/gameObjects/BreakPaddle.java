package brickGame.gameObjects;
import brickGame.GameConstants;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class BreakPaddle {


    public void setxBreak(double xBreak) {
        this.xBreak = xBreak;
    }
    public double getxBreak() {
        return xBreak;
    }
    private double xBreak = 0.0f;
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



    //Paddle initialisation
    public void initBreak() {
        rect = new Rectangle();
        rect.setWidth(GameConstants.BREAK_WIDTH.getIntValue());
        rect.setHeight(GameConstants.BREAK_HEIGHT.getIntValue());
        rect.setX(xBreak);
        rect.setY(yBreak);

        ImagePattern pattern = new ImagePattern(new Image("block.jpg"));
        rect.setFill(pattern);
    }


//    Split into two methods
    public void moveRight() {
        //System.out.println("Move right");
        new Thread(new Runnable() {

            @Override
            public void run() {
                int sleepTime = 4;
                for (int i = 0; i < 30; i++) {
                    if (xBreak == (GameConstants.SCENE_WIDTH.getIntValue() - GameConstants.BREAK_WIDTH.getIntValue())) {
                        return;
                    }
                    xBreak++;
                    centerBreakX = xBreak + halfBreakWidth;
                    try {
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (i >= 20) {
                        sleepTime = i;
                    }
                }
            }
        }).start();
    }



     public void moveLeft() {
      //   System.out.println("Move left");
        new Thread(new Runnable() {
            @Override
            public void run() {
                int sleepTime = 4;
                for (int i = 0; i < 30; i++) {
                    if (xBreak == 0) {
                        return;
                    }
                    xBreak--;
                    centerBreakX = xBreak + halfBreakWidth;
                    try {
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (i >= 20) {
                        sleepTime = i;
                    }
                }
            }
        }).start();
    }


}
