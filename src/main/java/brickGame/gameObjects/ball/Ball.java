package brickGame.gameObjects.ball;
import brickGame.constants.GameConstants;
import brickGame.gameObjects.block.Block;
import javafx.scene.shape.Circle;



import java.util.Random;
/**
 * The Ball class represents the game ball the brick-breaking game.
 * It extends the JavaFX Circle class and includes additional properties and methods
 * to handle the ball's movement, the ball's state, collision detection, initialising it and visual representation.
 */
public class Ball extends Circle {
    private double xBall;
    private double yBall;
    private BallView ballView;
    private boolean goDownBall  = true;
    private boolean goRightBall  = true;
    private boolean colideToBreak = false; // Boolean, true when ball collides with Paddle
    private boolean colideToBreakAndMoveToRight = true;
    private boolean colideToRightWall = false;
    private boolean colideToLeftWall = false;
    private boolean colideToRightBlock          = false;
    private boolean colideToBottomBlock         = false;
    private boolean colideToLeftBlock           = false;
    private double velocity = 2.000;
    private double velocityX = 2.000;
    private double velocityY = 2.000;
    private boolean colideToTopBlock = false;
    /**
     * Constructs a new Ball object with the specified radius.
     *
     * @param radius The radius of the ball.
     */
    public Ball(double radius) {
        this.ballView = new BallView(radius);
    }

    /**
     * Sets xBall
     *
     * @param xBall
     */
    public void setxBall(double xBall) {
        this.xBall = xBall;
    }


    /**
     * Gets the current x-coordinate of the ball's position.
     *
     * @return The current x-coordinate of the ball.
     */
    public double getxBall() {
        return xBall;
    }

    /**
     * Sets the y-coordinate of the ball's position.
     *
     * @param yBall The new y-coordinate for the ball.
     */
    public void setyBall(double yBall) {
        this.yBall = yBall;
    }

    /**
     * Gets the current y-coordinate of the ball's position.
     *
     * @return The current y-coordinate of the ball.
     */
    public double getyBall() {
        return yBall;
    }

    /**
     * Checks if the ball is currently moving downward.
     *
     * @return True if the ball is moving downward, false otherwise.
     */
    public boolean isGoDownBall() {
        return goDownBall;
    }

    /**
     * Sets the direction of the ball's vertical movement.
     *
     * @param goDownBall True to make the ball move downward, false to stop downward movement.
     */
    public void setGoDownBall(boolean goDownBall) {
        this.goDownBall = goDownBall;
    }

    /**
     * Checks if the ball is currently moving to the right.
     *
     * @return True if the ball is moving to the right, false otherwise.
     */
    public boolean isGoRightBall() {
        return goRightBall;
    }

    /**
     * Sets the direction of the ball's horizontal movement.
     *
     * @param goRightBall True to make the ball move to the right, false to stop rightward movement.
     */
    public void setGoRightBall(boolean goRightBall) {
        this.goRightBall = goRightBall;
    }


    /**
     * Checks if the ball has collided with the paddle to trigger a break event.
     *
     * @return True if the ball has collided with the paddle, triggering a break event, false otherwise.
     */
    public boolean isColideToBreak() {
        return colideToBreak;
    }

    /**
     * Sets the collision status with the paddle to trigger or stop a break event.
     *
     * @param colideToBreak True to indicate a collision with the paddle, triggering a break event,
     *                      false to indicate no collision.
     */
    public void setColideToBreak(boolean colideToBreak) {
        this.colideToBreak = colideToBreak;
    }


    /**
     * Checks if the ball has collided with the paddle and should move to the right as a result.
     *
     * @return True if the ball has collided with the paddle and should move to the right, false otherwise.
     */
    public boolean isColideToBreakAndMoveToRight() {
        return colideToBreakAndMoveToRight;
    }

    /**
     * Sets the collision status with the paddle, indicating whether the ball should move to the right.
     *
     * @param colideToBreakAndMoveToRight True to indicate a collision with the paddle and move to the right,
     *                                     false to indicate no collision or stop moving to the right.
     */
    public void setColideToBreakAndMoveToRight(boolean colideToBreakAndMoveToRight) {
        this.colideToBreakAndMoveToRight = colideToBreakAndMoveToRight;
    }

    /**
     * Checks if the ball has collided with the right wall.
     *
     * @return True if the ball has collided with the right wall, false otherwise.
     */
    public boolean isColideToRightWall() {
        return colideToRightWall;
    }

    /**
     * Sets the collision status with the right wall.
     *
     * @param colideToRightWall True to indicate a collision with the right wall, false to indicate no collision.
     */
    public void setColideToRightWall(boolean colideToRightWall) {
        this.colideToRightWall = colideToRightWall;
    }

    /**
     * Checks if the ball has collided with the left wall.
     *
     * @return True if the ball has collided with the left wall, false otherwise.
     */
    public boolean isColideToLeftWall() {
        return colideToLeftWall;
    }

    /**
     * Sets the collision status with the left wall.
     *
     * @param colideToLeftWall True to indicate a collision with the left wall, false to indicate no collision.
     */
    public void setColideToLeftWall(boolean colideToLeftWall) {
        this.colideToLeftWall = colideToLeftWall;
    }


    /**
     * Checks if the ball has collided with a block on the right side.
     *
     * @return True if the ball has collided with a block on the right side, false otherwise.
     */
    public boolean isColideToRightBlock() {
        return colideToRightBlock;
    }

    /**
     * Sets the collision status with a block on the right side.
     *
     * @param colideToRightBlock True to indicate a collision with a block on the right side, false to indicate no collision.
     */
    public void setColideToRightBlock(boolean colideToRightBlock) {
        this.colideToRightBlock = colideToRightBlock;
    }

    /**
     * Checks if the ball has collided with a block at the bottom.
     *
     * @return True if the ball has collided with a block at the bottom, false otherwise.
     */
    public boolean isColideToBottomBlock() {
        return colideToBottomBlock;
    }

    /**
     * Sets the collision status with a block at the bottom.
     *
     * @param colideToBottomBlock True to indicate a collision with a block at the bottom, false to indicate no collision.
     */
    public void setColideToBottomBlock(boolean colideToBottomBlock) {
        this.colideToBottomBlock = colideToBottomBlock;
    }

    /**
     * Checks if the ball has collided with a block on the left side.
     *
     * @return True if the ball has collided with a block on the left side, false otherwise.
     */
    public boolean isColideToLeftBlock() {
        return colideToLeftBlock;
    }

    /**
     * Sets the collision status with a block on the left side.
     *
     * @param colideToLeftBlock True to indicate a collision with a block on the left side, false to indicate no collision.
     */
    public void setColideToLeftBlock(boolean colideToLeftBlock) {
        this.colideToLeftBlock = colideToLeftBlock;
    }

    /**
     * Checks if the ball has collided with a block at the top.
     *
     * @return True if the ball has collided with a block at the top, false otherwise.
     */
    public boolean isColideToTopBlock() {
        return colideToTopBlock;
    }

    /**
     * Sets the collision status with a block at the top.
     *
     * @param colideToTopBlock True to indicate a collision with a block at the top, false to indicate no collision.
     */
    public void setColideToTopBlock(boolean colideToTopBlock) {
        this.colideToTopBlock = colideToTopBlock;
    }

    /**
     * Sets the overall velocity of the ball.
     *
     * @param velocity The new overall velocity of the ball.
     */
    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    /**
     * Gets the current velocity of the ball in the X-axis direction.
     *
     * @return The current velocity of the ball in the X-axis direction.
     */
    public double getVelocityX() {
        return velocityX;
    }

    /**
     * Sets the velocity of the ball in the X-axis direction.
     *
     * @param velocityX The new velocity of the ball in the X-axis direction.
     */
    public void setVelocityX(double velocityX) {
        this.velocityX = velocityX;
    }

    /**
     * Gets the current velocity of the ball in the Y-axis direction.
     *
     * @return The current velocity of the ball in the Y-axis direction.
     */
    public double getVelocityY() {
        return velocityY;
    }

    /**
     * Sets the velocity of the ball in the Y-axis direction.
     *
     * @param velocityY The new velocity of the ball in the Y-axis direction.
     */
    public void setVelocityY(double velocityY) {
        this.velocityY = velocityY;
    }


    /**
     * Initializes the ball with default settings, placing it at the center of the game scene.
     * This method also sets the initial ball image and other properties.
     */
    public void initBall() {
        xBall = (double) GameConstants.SCENE_WIDTH.getIntValue() / 2;
        yBall = (double) GameConstants.SCENE_HEIGHT.getIntValue() / 2;

        ballView.setBallImage(GameConstants.NORMAL_BALL);

        // Set other properties of the circle
        this.setRadius(GameConstants.BALL_RADIUS.getIntValue());
        this.setCenterX(xBall);
        this.setCenterY(yBall);
    }

    /**
     * Gets the BallView associated with this Ball object.
     *
     * @return The BallView object representing the visual representation of the ball.
     */
    public BallView getBallView() {
        return ballView;
    }
}
