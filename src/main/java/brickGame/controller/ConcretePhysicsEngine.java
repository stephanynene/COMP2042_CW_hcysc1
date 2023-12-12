package brickGame.controller;

import brickGame.Sounds;
import brickGame.constants.GameConstants;
import brickGame.Main;
import brickGame.gameEngine.GameEngine;
import brickGame.stats.Stats;
import brickGame.gameObjects.ball.Ball;
import brickGame.gameObjects.breakpaddle.BreakPaddle;
/**
 * Concrete implementation of the PhysicsEngine interface.
 * Manages physics interactions between game elements like the ball and BreakPaddle.
 */
public class ConcretePhysicsEngine implements PhysicsEngine {

    private Main game;
    private Ball ball;
    private BreakPaddle breakPaddle;
    private GameEngine gameEngine;
    private Stats stats;

    /**
     * Constructs a ConcretePhysicsEngine instance.
     *
     * @param game        The main game instance.
     * @param ball        The ball object in the game.
     * @param breakPaddle The BreakPaddle object in the game.
     * @param stats       The game statistics.
     */
    public ConcretePhysicsEngine(Main game, Ball ball, BreakPaddle breakPaddle, Stats stats) {
        this.game = game;
        this.ball = ball;
        this.breakPaddle = breakPaddle;
        this.stats = stats;
    }

    /**
     * @param gameEngine
     * Sets the game engine
     */
    public void setPEGameEngine(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
    }

    /**
     * Calls various methods
     */
    public void setPhysicsToBall() {
        calculateVelocity();
        ballPositioning();
        boundaryCollisons();
        handleBreakCollision();
        breakCollisonDirection();
        wallCollisons();
        ballCollision();
    }

    /**
     * Checks various conditions related to ball collisions with different game elements.
     * Adjusts ball movement by setting different booleans based on collision conditions.
     */
    public void ballCollision(){
        if (ball.isColideToRightBlock()) {
            ball.setGoRightBall(true);
        }

        if (ball.isColideToLeftBlock()) {
            ball.setGoRightBall(true);
        }

        if (ball.isColideToTopBlock()) {
            ball.setGoDownBall(false);
        }

        if (ball.isColideToBottomBlock()) {
            ball.setGoDownBall(true);
        }
    }

    /**
     * Checks conditions based on whether colliding to left or right wall
     * if left, set go right boolean to be true
     * and vise versa
     */
    public void wallCollisons(){

        if (ball.isColideToRightWall()) {
            ball.setGoRightBall(false);
        }

        if (ball.isColideToLeftWall()) {
            ball.setGoRightBall(true);
        }

    }

    /**
     * Calculates velocity of ball based on time and hit time
     */
    public void calculateVelocity() {
        ball.setVelocity(((stats.getTime() - stats.getHitTime()) / 1000.000) + 2.000);
    }

    /**
     * Handles boundary collisions for the ball.
     * Checks if the ball hits the top, bottom, right, or left boundary of the scene.
     * Adjusts the ball behavior and triggers respective sound effects .
     *
     * If ball hits the top boundary it resets collision flags, sets the ball to move downward,
     * and plays a bounce sound effect.
     *
     * If the ball goes beyond the bottom boundary, it plays a bounce sound effect,
     * stops the ball from moving downward, and handles heart decrement and game over conditions if necessary.
     *
     * If the ball hits the right boundary, it plays a bounce sound effect, resets collision flags,
     * and sets the "colideToRightWall" flag.
     *
     * If the ball hits the left boundary, it plays a bounce sound effect, resets collision flags,
     * and sets the "colideToLeftWall" flag.
     */    public void boundaryCollisons(){

        // Check if  ball hits the top boundary
        if (ball.getyBall() <= 0) {
            //vX = 1.000;
            resetCollideFlags();
            ball.setGoDownBall(true);
            Sounds.playBounceSound();
            return;

        }
        // Check if the ball goes beyond the bottom boundary
        if (ball.getyBall() >= GameConstants.SCENE_HEIGHT.getIntValue()) {
            Sounds.playBounceSound();
            ball.setGoDownBall(false);
            if (!game.isGoldStatus()) {
                stats.decreaseHeart();
                Sounds.playSound("lose-heart-sound");

                new Stats().show(GameConstants.SCENE_WIDTH.getIntValue() / 2, GameConstants.SCENE_HEIGHT.getIntValue() / 2, -1, game);
                if (stats.getHeart() == 0) {
                    new Stats().showGameOver(game, 1);
                        gameEngine.stop();
                    return;
                }
            }
        }

        // Check if the ball hits the right boundary
        if (ball.getxBall() >= GameConstants.SCENE_WIDTH.getIntValue()) {
            Sounds.playBounceSound();
            resetCollideFlags();
            ball.setColideToRightWall(true);
        }

        // Check if the ball hits the left boundary
        if (ball.getxBall() <= 0) {
            Sounds.playBounceSound();

            resetCollideFlags();
            ball.setColideToLeftWall(true);
        }
    }


    /**
     * - Updates the ball position based on its movement direction.
     * - If ball is moving downward, increment the y-coordinate; otherwise decrement
     * - If ball is moving to the right, increment the x-coordinate; otherwise decrement
     */    public void ballPositioning(){
        if (ball.isGoDownBall()) {
            double currentY = ball.getyBall(); // Get the current yBall value
            currentY += ball.getVelocityY(); // Update the yBall value
            ball.setyBall(currentY);
        } else {
            double currentY = ball.getyBall();
            currentY -= ball.getVelocityY();
            ball.setyBall(currentY);
        }

        if (ball.isGoRightBall()) {
            double currentX = ball.getxBall();
            currentX += ball.getVelocityX();
            ball.setxBall(currentX);
        } else {
            double currentX = ball.getxBall();
            currentX -= ball.getVelocityX();
            ball.setxBall(currentX);
        }
    }


    /**
     * If ball collides with  BreakPaddle, updates the ball's horizontal movement direction
     * according to the direction of collision
     */
    public void breakCollisonDirection(){
        if (ball.isColideToBreak()) {

            if (ball.isColideToBreakAndMoveToRight()) {
                ball.setGoRightBall(true);
            } else {
                ball.setGoRightBall(false);
            }
        }
    }

    /**
     * Handles collision between the ball and the BreakPaddle.
     *
     *
     * Reset collision flags and play a bounce sound on impact.
     * Update ball velocity based on collision position and direction.
     * Record collision time for game statistics.
     *
     *
     * Resets flags: colideToBreak, colideToBreakAndMoveToRight, goDownBall.
     * Plays a bounce sound effect using the Sounds class.
     * Adjusts ball velocity considering collision characteristics.
     * Records the collision time in game statistics.
     * Collision detection relies on ball x-coordinate within BreakPaddle's bounds.
     */
    public void handleBreakCollision(){
        // Check if the ball collides with the BreakPaddle
        if (ball.getyBall() >= breakPaddle.getyBreak() - GameConstants.BALL_RADIUS.getIntValue()) {

            if (ball.getxBall() >= breakPaddle.getxBreak() && ball.getxBall() <= breakPaddle.getxBreak() + GameConstants.BREAK_WIDTH.getIntValue() ) {
                stats.setHitTime(stats.getTime()); // Record time of collision
                resetCollideFlags();
                ball.setColideToBreak(true);
              Sounds.playBounceSound();
                ball.setGoDownBall(false);

                // Calculate relation and update velocity based on collision
                double relation = (ball.getxBall() - breakPaddle.getCenterBreakX()) / (GameConstants.BREAK_WIDTH.getIntValue() / 2);

                if (Math.abs(relation) <= 0.3) {
                    //vX = 0;
                    int v1 = (int) (1 * Math.abs(relation));
                    ball.setVelocityX(v1);
                } else if (Math.abs(relation) > 0.3 && Math.abs(relation) <= 0.7) {
                    int v1 = (int) (1.5 * ((Math.abs(relation) * 1.5) + (game.getLevel() / 3.500)));
                    ball.setVelocityX(v1);
                } else {
                    int v1 = (int) (1.2 * ((Math.abs(relation) * 2) + (game.getLevel() / 3.500)));
                    ball.setVelocityX(v1);
                }

                // Determine the direction of the collision
                if (ball.getxBall() - breakPaddle.getCenterBreakX() > 0) {
                    ball.setColideToBreakAndMoveToRight(true);
                } else {
                    ball.setColideToBreakAndMoveToRight(false);
                }
            }
        }
    }

    /**
     * resets all the collide booleans to be false
     */
    public void resetCollideFlags() {

        ball.setColideToBreak(false);
        ball.setColideToBreakAndMoveToRight(false);
        ball.setColideToRightWall(false);
        ball.setColideToLeftWall(false);

        ball.setColideToRightBlock(false);
        ball.setColideToBottomBlock(false);
        ball.setColideToLeftBlock(false);
        ball.setColideToTopBlock(false);
    }

}
