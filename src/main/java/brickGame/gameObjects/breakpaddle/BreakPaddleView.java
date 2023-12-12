package brickGame.gameObjects.breakpaddle;
import brickGame.constants.GameConstants;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
/**
 * The BreakPaddleView class represents visuals of the breakable paddle in the game.
 * It extends the Rectangle class and is responsible for displaying the breakable paddle on the game scene.
 */
public class BreakPaddleView extends Rectangle {
    /**
     * Constructs a visual representation of the break paddle using a Rectangle with an associated image pattern.
     * The initial position of the paddle is determined by the provided x and y coordinates.
     *
     * @param rect The Rectangle object representing the breakable paddle.
     * @param x    The initial x-coordinate of the breakable paddle.
     * @param y    The initial y-coordinate of the breakable paddle.
     */
    public BreakPaddleView(Rectangle rect, double x, double y) {
        rect.setWidth(GameConstants.BREAK_WIDTH.getIntValue());
        rect.setHeight(GameConstants.BREAK_HEIGHT.getIntValue());
        rect.setX(x);
        rect.setY(y);

        ImagePattern pattern = new ImagePattern(new Image(GameConstants.BREAKPADDLE_IMG.getStringValue()));
        rect.setFill(pattern);
    }
    /**
     * Updates the position of the break paddle to the coordinates.
     *
     * @param x The new x-coordinate of the breakable paddle.
     * @param y The new y-coordinate of the breakable paddle.
     */
    public void updatePosition(double x, double y) {
        this.setX(x);
        this.setY(y);
    }
}
