package brickGame.gameObjects.breakpaddle;
import brickGame.constants.GameConstants;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**

 The BreakPaddle class defines the behavior of the paddle within the game.
 It manages the movement and animation of the breakable paddle, using a Timeline animation
 for a smooth transition as the paddle moves to the left or right within game scene.
 */
public class BreakPaddle {
    private int halfBreakWidth = GameConstants.BREAK_WIDTH.getIntValue() / 2;
    private static final Duration ANIMATION_DURATION = Duration.millis(1);
    private BreakPaddleView breakPaddleView;
    private double xBreak = 0.0f;
    public Rectangle rect;
    private double centerBreakX;
    private double yBreak = 640.0f;
    /**
     * Gets the x-coordinate of the center of the breakable paddle.
     *
     * @return The x-coordinate of the center of the breakable paddle.
     */
    public double getCenterBreakX() {
        return centerBreakX;
    }

    /**
     * Sets the x-coordinate of the center of the breakable paddle.
     *
     * @param centerBreakX The new x-coordinate of the center of the breakable paddle.
     */
    public void setCenterBreakX(double centerBreakX) {
        this.centerBreakX = centerBreakX;
    }

    /**
     * Gets the y-coordinate of the breakable paddle.
     *
     * @return The y-coordinate of the breakable paddle.
     */
    public double getyBreak() {
        return yBreak;
    }

    /**
     * Sets the y-coordinate of the breakable paddle.
     *
     * @param yBreak The new y-coordinate of the breakable paddle.
     */
    public void setyBreak(double yBreak) {
        this.yBreak = yBreak;
    }

    /**
     * Sets the x-coordinate of the breakable paddle.
     *
     * @param xBreak The new x-coordinate of the breakable paddle.
     */
    public void setxBreak(double xBreak) {
        this.xBreak = xBreak;
    }

    /**
     * Gets the x-coordinate of the breakable paddle.
     *
     * @return The x-coordinate of the breakable paddle.
     */
    public double getxBreak() {
        return xBreak;
    }

    /**
     * Initialises the break paddle by creating a Rectangle object and setting it with a visual representation.
     * The initial position of the paddle is determined by the x and y coordinates.
     */
    public void initBreak() {
        rect = new Rectangle();
        breakPaddleView = new BreakPaddleView(rect, xBreak, yBreak);
    }

    /**
     * Initiates the movement of the break paddle to the right.
     * the paddle stays within the game scene width.
     * Uses the animateMovement method
     */
    public void moveRight() {
        animateMovement(GameConstants.BREAKPADDLE_SPEED.getIntValue(), GameConstants.SCENE_WIDTH.getIntValue() - GameConstants.BREAK_WIDTH.getIntValue());
    }

    /**
     * Initiates the movement of the break paddle to the left.
     * the paddle stays within the game scene width.
     * Uses the animateMovement method
     */
    public void moveLeft() {
        animateMovement(-GameConstants.BREAKPADDLE_SPEED.getIntValue(), GameConstants.SCENE_WIDTH.getIntValue() - GameConstants.BREAK_WIDTH.getIntValue());
    }


    /**
     * Initiates animation to move the breakable paddle horizontally within the specified range. in a smooth way
     * The animation adjusts the x-coordinate of the paddle with a given deltaX value.
     *
     * @param deltaX The change in the x-coordinate of the paddle during each animation frame.
     * @param maxX   The maximum x-coordinate value within which the paddle is allowed to move.
     *               The paddle's position stays within the range [0, maxX].
     */
    private void animateMovement(double deltaX, double maxX) {
        Timeline timeline = new Timeline(
                new KeyFrame(ANIMATION_DURATION, e -> {
                    xBreak = Math.min(Math.max(xBreak + deltaX, 0), maxX);
                    centerBreakX = xBreak + halfBreakWidth;
                    // Update the visual representation of the breakable paddle by x and y
                    updateBreakPaddleView();
                })
        );
        // Set the animation to run only once
        timeline.setCycleCount(1);
        // Start the animation
        timeline.play();
    }

    /**
     * Updates the visual representation of the paddle based on its current position.
     */
    private void updateBreakPaddleView() {
        breakPaddleView.updatePosition(xBreak, yBreak);
    }
}