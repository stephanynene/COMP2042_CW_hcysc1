package brickGame.controller;

import brickGame.Sounds;
import brickGame.constants.GameConstants;
import brickGame.Main;
import brickGame.gameEngine.GameEngine;
import brickGame.stats.Stats;
import brickGame.gameObjects.ball.Ball;
import brickGame.gameObjects.breakpaddle.BreakPaddle;

public class ConcretePhysicsEngine implements PhysicsEngine {

    private Main game;
    private Ball ball;
    private BreakPaddle breakPaddle;
    private GameEngine gameEngine;
    private Stats stats;

    public ConcretePhysicsEngine(Main game, Ball ball, BreakPaddle breakPaddle, Stats stats) {
        this.game = game;
        this.ball = ball;
        this.breakPaddle = breakPaddle;
        this.stats = stats;
    }

    public void setPEGameEngine(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
        System.out.println(gameEngine);
    }

    public void setPhysicsToBall() {
        calculateVelocity();
        ballPositioning();
        boundaryCollisons();
        handleBreakCollision();
        breakCollisonDirection();
        wallCollisons();
        ballCollision();
    }
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

    public void wallCollisons(){

        if (ball.isColideToRightWall()) {
            ball.setGoRightBall(false);
        }

        if (ball.isColideToLeftWall()) {
            ball.setGoRightBall(true);
        }

    }

    // Calculate velocity based on time and hit time
    public void calculateVelocity() {
        ball.setVelocity(((stats.getTime() - stats.getHitTime()) / 1000.000) + 2.000);
    }

    //Handle boundary collisions
    public void boundaryCollisons(){

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
                stats.setHeart(stats.getHeart() - 1);
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
            //vX = 1.000;
            ball.setColideToRightWall(true);
        }

        // Check if the ball hits the left boundary
        if (ball.getxBall() <= 0) {
            Sounds.playBounceSound();

            resetCollideFlags();
            //vX = 1.000;
            ball.setColideToLeftWall(true);
        }
    }


    //Update ball positioning
    public void ballPositioning(){
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


    //Handle collisions to break paddle
    public void breakCollisonDirection(){
        if (ball.isColideToBreak()) {

            if (ball.isColideToBreakAndMoveToRight()) {
                ball.setGoRightBall(true);
            } else {
                ball.setGoRightBall(false);
            }
        }
    }

    public void handleBreakCollision(){
        // Check if the ball collides with the BreakPaddle
        if (ball.getyBall() >= breakPaddle.getyBreak() - GameConstants.BALL_RADIUS.getIntValue()) {

            if (ball.getxBall() >= breakPaddle.getxBreak() && ball.getxBall() <= breakPaddle.getxBreak() + GameConstants.BREAK_WIDTH.getIntValue() ) {
                stats.setHitTime(stats.getTime());
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
