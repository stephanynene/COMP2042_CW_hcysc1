package brickGame.gameObjects;


import brickGame.GameConstants;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.Serializable;

public class Block implements Serializable {
    private static Block block = new Block(-1, -1, Color.TRANSPARENT, 99);

    public int row;
    public int column;


    public boolean isDestroyed = false;

    private Color color;
    public int type;

    public int x;
    public int y;

    private int width = 100;
    private int height = 30;
    private int paddingTop = height * 2;
    private int paddingH = 50;
    public Rectangle rect;


    public Block(int row, int column, Color color, int type) {
        this.row = row;
        this.column = column;
        this.color = color;
        this.type = type;

        draw();
    }

    private void draw() {
        x = (column * width) + paddingH;
        y = (row * height) + paddingTop;

        rect = new Rectangle();
        rect.setWidth(width);
        rect.setHeight(height);
        rect.setX(x);
        rect.setY(y);

        if (type == GameConstants.BLOCK_CHOCO.getIntValue()) {
            Image image = new Image("choco.jpg");
            ImagePattern pattern = new ImagePattern(image);
            rect.setFill(pattern);
        } else if (type == GameConstants.BLOCK_HEART.getIntValue()) {
            Image image = new Image("heart.jpg");
            ImagePattern pattern = new ImagePattern(image);
            rect.setFill(pattern);
        } else if (type == GameConstants.BLOCK_STAR.getIntValue()) {
            Image image = new Image("star.jpg");
            ImagePattern pattern = new ImagePattern(image);
            rect.setFill(pattern);
        } else {
            rect.setFill(color);
        }

    }


    public int checkHitToBlock(double xBall, double yBall) {

        if (isDestroyed) {
            return GameConstants.NO_HIT.getIntValue();
        }

        if (xBall >= x && xBall <= x + width && yBall == y + height) {
            return GameConstants.HIT_BOTTOM.getIntValue();
        }

        if (xBall >= x && xBall <= x + width && yBall == y) {
            return GameConstants.HIT_TOP.getIntValue();
        }

        if (yBall >= y && yBall <= y + height && xBall == x + width) {
            return GameConstants.HIT_RIGHT.getIntValue();
        }

        if (yBall >= y && yBall <= y + height && xBall == x) {
            return GameConstants.HIT_LEFT.getIntValue();
        }

        return GameConstants.NO_HIT.getIntValue();
    }

    public static int getPaddingTop() {
        return block.paddingTop;
    }

    public static int getPaddingH() {
        return block.paddingH;
    }

    public static int getHeight() {
        return block.height;
    }

    public static int getWidth() {
        return block.width;
    }

}
