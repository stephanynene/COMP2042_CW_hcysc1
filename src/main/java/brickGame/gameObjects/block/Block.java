package brickGame.gameObjects.block;


import brickGame.constants.GameConstants;
import javafx.scene.paint.Color;
import java.io.Serializable;

import static brickGame.constants.GameConstants.*;
/**
 * Block class represents a block in the game.
 * Part of game's grid, can have different colors, types, and durability. Blocks can be destroyed by the ball depending on .
 *
 * Original source code
 * {@linkplain <a href="https://github.com/kooitt/CourseworkGame/blob/master/src/main/java/brickGame/Block.java</a>}
 */

public class Block implements Serializable {
    private static int paddingTop = BLOCK_HEIGHT.getIntValue() * 2;
    // Instance variables
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

    /**
     * Constructs new Block with specified parameters.
     *
     * @param row        The row index of the block in the grid.
     * @param column     The column index of the block in the grid.
     * @param color      The color of block.
     * @param type       The type of block.
     * @param durability The durability of the block (0 means will break on impact).
     */
    public Block(int row, int column, Color color, int type, int durability) {
        this.row = row;
        this.column = column;
        this.color = color;
        this.type = type;
        this.durability = durability;
        draw();
    }


    /**
     * Draws the block based on color, type, size (width, height),
     * x is the x-coordinate of the block in the game grid, calculated using width in enum class GameConstants
     * y isis the y-coordinate of the block in the game grid, calculated using
     * height of block in enum class GameConstants
     *
     * Created using BlockView class
     * Called for construction of block in Block() and used in Board class
     */
    private void draw() {
        x = (column * BLOCK_WIDTH.getIntValue()) + PADDING_H.getIntValue();
        y = (row * BLOCK_HEIGHT.getIntValue()) + paddingTop;

        blockView = new BlockView(x, y, BLOCK_WIDTH.getIntValue(), BLOCK_HEIGHT.getIntValue(), color, type);
    }

    /**
     * Checks if ball has hit the block and returns the type of hit.
     * Logic for collision detection between the ball and the block, considering
     * the positions and dimensions of both entities.
     * Return indicates side of the block that the ball has hit, if there has ben any hit
     *
     * @param xBall The x-coordinate of the ball.
     * @param yBall The y-coordinate of the ball.
     * @return The type of hit (HIT_RIGHT, HIT_LEFT, HIT_TOP, HIT_BOTTOM, or NO_HIT).
     *         Returns NO_HIT if the block is already destroyed.
     *
     * Collision detection is based on the bounding boxes of the ball and the block.
     * It alculates the positions of the ball and the block at each side (left,
     * right, top, bottom) and checks for overlaps. If overlap is detected, it determines
     * the specific side of the block that the ball has hit.
     *
     * Possible return values:
     * - HIT_RIGHT: The ball has hit the right side of the block.
     * - HIT_LEFT: The ball has hit the left side of the block.
     * - HIT_TOP: The ball has hit the top side of the block.
     * - HIT_BOTTOM: The ball has hit the bottom side of the block.
     * - NO_HIT: The block is already destroyed, or no collision has occurred.
     */
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

    /**
     * Gets the padding at the top used for positioning blocks in the game grid.
     *
     * @return The padding at the top.
     */
    public static int getPaddingTop() {
        return paddingTop;
    }

    /**
     * Gets the horizontal padding used for positioning blocks in the game grid.
     *
     * @return The horizontal padding.
     */
    public static int getPaddingH() {
        return PADDING_H.getIntValue();
    }

    /**
     * Gets the height of a block.
     *
     * @return The height of a block.
     */
    public static int getHeight() {
        return GameConstants.BLOCK_HEIGHT.getIntValue();
    }

    /**
     * Gets the width of a block.
     *
     * @return The width of a block.
     */
    public static int getWidth() {
        return GameConstants.BLOCK_WIDTH.getIntValue();
    }

    /**
     * Gets the BlockView associated with this Block object.
     *
     * @return The BlockView object representing the visual representation of the block.
     */
    public BlockView getBlockView() {
        return blockView;
    }

    /**
     * Gets the durability of the block.
     *
     * @return The durability of the block.
     */
    public int getDurability() {
        return durability;
    }

    /**
     * Sets the durability of the block.
     *
     * @param durability The new durability value to be set.
     */
    public void setDurability(int durability) {
        this.durability = durability;
    }


}
