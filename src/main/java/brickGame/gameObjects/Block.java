package brickGame.gameObjects;


import brickGame.GameConstants;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.Serializable;

import static brickGame.GameConstants.BLOCK_HEIGHT;
import static brickGame.GameConstants.BLOCK_WIDTH;
import static brickGame.GameConstants.PADDING_H;


public class Block implements Serializable {
    private static Block block = new Block(-1, -1, Color.TRANSPARENT, 99);

    public int row;
    public int column;

    public boolean isDestroyed = false;

    private Color color;
    public int type;

    public int x;
    public int y;

    private static int paddingTop = BLOCK_HEIGHT.getIntValue() * 2;
    public Rectangle rect;


    public Block(int row, int column, Color color, int type) {
        this.row = row;
        this.column = column;
        this.color = color;
        this.type = type;

        draw();
    }

    private void draw() {
        x = (column * BLOCK_WIDTH.getIntValue()) + PADDING_H.getIntValue();
        y = (row * BLOCK_HEIGHT.getIntValue()) + paddingTop;

        rect = new Rectangle();
        rect.setWidth(BLOCK_WIDTH.getIntValue());
        rect.setHeight(BLOCK_HEIGHT.getIntValue());
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

        if (xBall >= x && xBall <= x + GameConstants.BLOCK_WIDTH.getIntValue() && yBall == y + BLOCK_HEIGHT.getIntValue()) {
            return GameConstants.HIT_BOTTOM.getIntValue();
        }

        if (xBall >= x && xBall <= x + GameConstants.BLOCK_WIDTH.getIntValue() && yBall == y) {
            return GameConstants.HIT_TOP.getIntValue();
        }

        if (yBall >= y && yBall <= y + BLOCK_HEIGHT.getIntValue() && xBall == x + GameConstants.BLOCK_WIDTH.getIntValue()) {
            return GameConstants.HIT_RIGHT.getIntValue();
        }

        if (yBall >= y && yBall <= y + BLOCK_HEIGHT.getIntValue() && xBall == x) {
            return GameConstants.HIT_LEFT.getIntValue();
        }

        return GameConstants.NO_HIT.getIntValue();
    }

    public static int getPaddingTop() {
        return paddingTop;
    }

    public static int getPaddingH() {
        return PADDING_H.getIntValue();
    }

    public static int getHeight() {
        return BLOCK_HEIGHT.getIntValue();
    }

    public static int getWidth() {
        return GameConstants.BLOCK_WIDTH.getIntValue();
    }

}
