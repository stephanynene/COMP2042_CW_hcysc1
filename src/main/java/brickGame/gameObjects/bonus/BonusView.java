package brickGame.gameObjects.bonus;

import brickGame.constants.GameConstants;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
/**
 * BonusView class represents the visual representation of a bonus object in the game.
 * Creates the graphical representation of a bonus on the game board and sets up a rectangular shape (choco) with a specified size
 * and position, filling it with an image, specified in GameConstants enum class
 * based on whether the bonus is considered "good" or "bad."
 *
 */
public class BonusView {

    /**
     * Constructs new BonusView object with the specific properties.
     *
     * This constructor initialises a rectangular shape (choco) with fixed size and position.
     * It determines the image to be used based on whether the bonus is considered "good" or "bad" (from GameConstants enum class)
     * and fills the rectangular shape with the corresponding image.
     *
     *
     * @param choco  The rectangular shape representing the bonus.
     * @param x      The x-coordinate of the bonus on the game board.
     * @param y      The y-coordinate of the bonus on the game board.
     * @param isGood A flag indicating whether the bonus is considered "good" (true) or "bad" (false).
     */
    public BonusView(Rectangle choco, double x, double y, boolean isGood) {
        choco.setWidth(30);
        choco.setHeight(30);
        choco.setX(x);
        choco.setY(y);

        String url;
        if (isGood) {
            // image for good bonus
            url = GameConstants.GOOD_BONUS_IMG.getStringValue();
        } else {
            // image for bad bonus
            url = GameConstants.BAD_BONUS_IMG.getStringValue();
        }
        choco.setFill(new ImagePattern(new Image(url)));
    }
}
