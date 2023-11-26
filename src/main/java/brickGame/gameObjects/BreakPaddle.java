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
