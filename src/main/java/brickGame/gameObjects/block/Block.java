package brickGame.gameObjects.block;


import brickGame.constants.GameConstants;
import javafx.scene.paint.Color;
import java.io.Serializable;

import static brickGame.constants.GameConstants.*;


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
    private int durability; // 0 means will break on impact

    public Block(int row, int column, Color color, int type, int durability) {
        this.row = row;
        this.column = column;
        this.color = color;
        this.type = type;
        this.durability = durability;
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

        double leftBall = xBall;
        double topBall =  yBall;
        double rightBall = xBall + BALL_RADIUS.getIntValue();
        double botBall = yBall + BALL_RADIUS.getIntValue();

        double leftBlock = x;
        double topBlock = y;
        double rightBlock = x + BLOCK_WIDTH.getIntValue();
        double botBlock = y + BLOCK_HEIGHT.getIntValue();

        // collision with bottom of block
        if (topBall <= botBlock && botBall >= topBlock && rightBall >= leftBlock && leftBall <= rightBlock) {

            // collision with right
            if (leftBall <= rightBlock && rightBall > rightBlock) {
                return HIT_RIGHT.getIntValue();
            }
            // collision with left
            else if (rightBall >= leftBlock && leftBall < leftBlock) {
                return HIT_LEFT.getIntValue();
            }
            // collision with top
            else if (botBall >= topBlock && topBall < topBlock) {
                return HIT_TOP.getIntValue();
            }
            // collision with bot
            else if (topBall <= botBlock && botBall >= botBlock) {
                return HIT_BOTTOM.getIntValue();
            }
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
    public int getDurability() {
        return durability;
    }
    public void setDurability(int durability) {
        this.durability = durability;
    }

}
