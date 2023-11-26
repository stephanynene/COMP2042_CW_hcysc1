package brickGame.gameObjects;


import brickGame.constants.GameConstants;
import javafx.scene.paint.Color;
import java.io.Serializable;

import static brickGame.constants.GameConstants.BLOCK_HEIGHT;
import static brickGame.constants.GameConstants.BLOCK_WIDTH;
import static brickGame.constants.GameConstants.PADDING_H;


public class Block implements Serializable {
    private static int paddingTop = BLOCK_HEIGHT.getIntValue() * 2;
    private BlockView blockView;

    public int row;
    public int column;

    public boolean isDestroyed = false;

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    private Color color;
    public int type;

    public int x;
    public int y;

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

        blockView = new BlockView(x, y, BLOCK_WIDTH.getIntValue(), BLOCK_HEIGHT.getIntValue(), color, type);
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

    public BlockView getBlockView(){
        return blockView;
    }

}
