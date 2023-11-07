package brickGame.gameObjects;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class BreakPaddle {

    public double xBreak = 0.0f;
    private double centerBreakX;
    public double yBreak = 640.0f;
    private int breakWidth = 130;
    private int breakHeight = 30;
    private int halfBreakWidth = breakWidth / 2;

    public int sceneWidth = 500;
    private int sceneHeigt = 700;
    private static int LEFT  = 1;
    private static int RIGHT = 2;

    public Rectangle rect;




    //Paddle initialisation
    public void initBreak() {
        rect = new Rectangle();
        rect.setWidth(breakWidth);
        rect.setHeight(breakHeight);
        rect.setX(xBreak);
        rect.setY(yBreak);

        ImagePattern pattern = new ImagePattern(new Image("block.jpg"));
        rect.setFill(pattern);
    }

    // Paddle code
//    public void move(final int direction) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                int sleepTime = 4;
//                for (int i = 0; i < 30; i++) {
//                    if (xBreak == (sceneWidth - breakWidth) && direction == RIGHT) {
//                        return;
//                    }
//                    if (xBreak == 0 && direction == LEFT) {
//                        return;
//                    }
//                    if (direction == RIGHT) {
//                        xBreak++;
//                    } else {
//                        xBreak--;
//                    }
//                    centerBreakX = xBreak + halfBreakWidth;
//                    try {
//                        Thread.sleep(sleepTime);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    if (i >= 20) {
//                        sleepTime = i;
//                    }
//                }
//            }
//        }).start();
//    }

//    Split into two methods
    public void moveRight() {
        //System.out.println("Move right");
        new Thread(new Runnable() {

            @Override
            public void run() {
                int sleepTime = 4;
                for (int i = 0; i < 30; i++) {
                    if (xBreak == (sceneWidth - breakWidth)) {
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
