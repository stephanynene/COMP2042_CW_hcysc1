package brickGame.gameObjects.ball;

import brickGame.constants.GameConstants;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
/**
 * The BallView class represents the visual representation of a ball in the game.
 * It extends the JavaFX Circle class and includes methods to customise the appearance
 * of the ball using different images.
 */
public class BallView extends Circle {
    /**
     * Constructs a new BallView with the specified radius.
     *
     * @param radius The radius of the ball.
     */
    public BallView(double radius) {
        super(radius);
    }

    /**
     * Sets the image of the ball based on the provided ball type.
     *
     * @param ballType The type of ball determining the image to be used.
     *                 Should be one of the constants from the GameConstants enum.
     */
    public void setBallImage(GameConstants ballType) {
        this.setFill(new ImagePattern(new Image(ballType.getStringValue())));
    }
}