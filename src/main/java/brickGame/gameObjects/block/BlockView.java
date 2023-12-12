package brickGame.gameObjects.block;

import brickGame.constants.GameConstants;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
/**
 * The BlockView class represents the visual representation of a block in the game, including its size, and fill image.
 * It provides methods to set the appearance of the block based on its type and color.
 */
public class BlockView {

    private Rectangle rect;
    /**
     * Gets the Rectangle object representing the visual aspect of the block.
     *
     * @return The Rectangle object associated with the BlockView.
     */
    public Rectangle getRect() {
        return rect;
    }

    /**
     * Constructs a BlockView with specified coordinates, dimensions, color, and type.
     *
     * @param x      The x-coordinate of the block.
     * @param y      The y-coordinate of the block.
     * @param width  The width of the block.
     * @param height The height of the block.
     * @param color  The color of the block (used when no specific image is assigned).
     * @param type   The type of the block, determining its appearance.
     */
    public BlockView(double x, double y, double width, double height, Color color, int type) {
        rect = new Rectangle();
        rect.setWidth(width);
        rect.setHeight(height);
        rect.setX(x);
        rect.setY(y);

        setOnImageType(type, color);
    }


    /**
     * Sets the fill of the block's rectangle using a specified image path.
     *
     * @param imagePath The path to the image used as the fill for the block.
     */
    private void setBlockImage(String imagePath) {
        Image image = new Image(imagePath);
        ImagePattern pattern = new ImagePattern(image);
        rect.setFill(pattern);
    }

    /**
     * Determines type of the block and sets its appearance according to block type, specified in the GameConstants enum class.
     * If the type is recognised, it sets the block's fill using a predefined image.
     * If the type is not recognised, it sets the block's fill using the specified color.
     *
     * @param type  The type of the block, used to determine what image to fill with
     * @param color The colour of the block (used when no specific image is assigned).
     */
    private void setOnImageType(int type, Color color) {
        if (type == GameConstants.BLOCK_CHOCO.getIntValue()) {
            setBlockImage(GameConstants.CHOCO_BLOCK_IMG.getStringValue());
        } else if (type == GameConstants.BLOCK_HEART.getIntValue()) {
            setBlockImage(GameConstants.HEART_BLOCK_IMG.getStringValue());
        } else if (type == GameConstants.BLOCK_STAR.getIntValue()) {
            setBlockImage(GameConstants.STAR_BLOCK_IMG.getStringValue());
        } else if (type == GameConstants.BLOCK_STURDY.getIntValue()) {
            setBlockImage(GameConstants.STURDY_BLOCK_IMG.getStringValue());
        } else if (type == GameConstants.BLOCK_THUNDER.getIntValue()) {
            setBlockImage(GameConstants.THUNDER_BLOCK_IMG.getStringValue());
        } else {
            rect.setFill(color);
        }
    }
}