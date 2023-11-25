package brickGame.gameEngine;

import brickGame.constants.GameConstants;
import brickGame.Main;
import brickGame.scoring.Score;
import brickGame.gameObjects.Ball;
import brickGame.gameObjects.BreakPaddle;

public class PhysicsEngine {

    private Main game;
    private Ball ball;
    private BreakPaddle breakPaddle;
    private GameEngine gameEngine;

    public PhysicsEngine(Main game, Ball ball, BreakPaddle breakPaddle, GameEngine gameEngine) {
        this.game = game;
        this.ball = ball;
        this.breakPaddle = breakPaddle;
        this.gameEngine = gameEngine;
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
    private void ballCollision(){
        if (game.isColideToRightBlock()) {
            game.setGoRightBall(true);
        }

        if (game.isColideToLeftBlock()) {
            game.setGoRightBall(true);
        }

        if (game.isColideToTopBlock()) {
            game.setGoDownBall(false);
        }

        if (game.isColideToBottomBlock()) {
            game.setGoDownBall(true);
        }
    }

    private void wallCollisons(){

        if (game.isColideToRightWall()) {
            game.setGoRightBall(false);
        }

        if (game.isColideToLeftWall()) {
            game.setGoRightBall(true);
        }

    }

    // Calculate velocity based on time and hit time
    private void calculateVelocity(){
        game.setVelocity(((game.getTime() - game.getHitTime()) / 1000.000) + 1.000);
    }

    //Handle boundary collisions
    private void boundaryCollisons(){

        // Check if  ball hits the top boundary
        if (ball.getyBall() <= 0) {
            //vX = 1.000;
            resetCollideFlags();
            game.setGoDownBall(true);
            return;

        }
        // Check if the ball goes beyond the bottom boundary
        if (ball.getyBall() >= GameConstants.SCENE_HEIGHT.getIntValue()) {

            game.setGoDownBall(false);
            if (!game.isGoldStauts()) {
                //TODO gameover
                game.setHeart(game.getHeart() - 1);
                new Score().show(GameConstants.SCENE_WIDTH.getIntValue() / 2, GameConstants.SCENE_HEIGHT.getIntValue() / 2, -1, game);

                if (game.getHeart() == 0) {
                    new Score().showGameOver(game);
                    gameEngine.stop();
                }

            }
            //return;
        }

        // Check if the ball hits the right boundary
        if (ball.getxBall() >= GameConstants.SCENE_WIDTH.getIntValue()) {
            resetCollideFlags();
            //vX = 1.000;
            game.setColideToRightWall(true);
        }

        // Check if the ball hits the left boundary
        if (ball.getxBall() <= 0) {
            resetCollideFlags();
            //vX = 1.000;
            game.setColideToLeftWall(true);
        }
    }


    //Update ball positioning
    private void ballPositioning(){
        if (game.isGoDownBall()) {
            double currentY = ball.getyBall(); // Get the current yBall value
            currentY += game.getVelocityY(); // Update the yBall value
            ball.setyBall(currentY);
        } else {
            double currentY = ball.getyBall();
            currentY -= game.getVelocityY();
            ball.setyBall(currentY);
        }

        if (game.isGoRightBall()) {
            double currentX = ball.getxBall();
            currentX += game.getVelocityX();
            ball.setxBall(currentX);
        } else {
            double currentX = ball.getxBall();
            currentX -= game.getVelocityX();
            ball.setxBall(currentX);
        }
    }


    //Handle collisions to break paddle
    private void breakCollisonDirection(){
        if (game.isColideToBreak()) {
            if (game.isColideToBreakAndMoveToRight()) {
                game.setGoRightBall(true);
            } else {
                game.setGoRightBall(false);
            }
        }
    }

    private void handleBreakCollision(){
        // Check if the ball collides with the BreakPaddle
        if (ball.getyBall() >= breakPaddle.getyBreak() - GameConstants.BALL_RADIUS.getIntValue()) {
            //System.out.println("Colide1");
            if (ball.getxBall() >= breakPaddle.getxBreak() && ball.getxBall() <= breakPaddle.getxBreak() + GameConstants.BREAK_WIDTH.getIntValue() ) {
                game.setHitTime(game.getTime());
                resetCollideFlags();
                game.setColideToBreak(true);
                game.setGoDownBall(false);

                // Calculate relation and update velocity based on collision
                double relation = (ball.getxBall() - breakPaddle.getCenterBreakX()) / (GameConstants.BREAK_WIDTH.getIntValue() / 2);

                if (Math.abs(relation) <= 0.3) {
                    //vX = 0;
                    int v1 = (int) (1 * Math.abs(relation));
                    game.setVelocityX(v1);
                } else if (Math.abs(relation) > 0.3 && Math.abs(relation) <= 0.7) {
                    int v1 = (int) (1.5 * ((Math.abs(relation) * 1.5) + (game.getLevel() / 3.500)));
                    game.setVelocityX(v1);
                    System.out.println("vX " + game.getVelocityX());
                } else {
                    int v1 = (int) (1.2 * ((Math.abs(relation) * 2) + (game.getLevel() / 3.500)));
                    game.setVelocityX(v1);
                    System.out.println("vX " + game.getVelocityX());
                }

                // Determine the direction of the collision
                if (ball.getxBall() - breakPaddle.getCenterBreakX() > 0) {
                    game.setColideToBreakAndMoveToRight(true);
                } else {
                    game.setColideToBreakAndMoveToRight(false);
                }
                //System.out.println("Colide2");
            }
        }
    }

    public void resetCollideFlags() {

        game.setColideToBreak(false);
        game.setColideToBreakAndMoveToRight(false);
        game.setColideToRightWall(false);
        game.setColideToLeftWall(false);

        game.setColideToRightBlock(false);
        game.setColideToBottomBlock(false);
        game.setColideToLeftBlock(false);
        game.setColideToTopBlock(false);
    }

}
