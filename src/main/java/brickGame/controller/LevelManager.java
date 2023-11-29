package brickGame.controller;

import brickGame.Main;
import brickGame.gameEngine.GameEngine;

public class LevelManager {

    private Main game;
    private PhysicsEngine physicsEngine;

    private GameEngine gameEngine;
    public LevelManager(Main game, PhysicsEngine physicsEngine , GameEngine gameEngine) {
        this.game = game;
        this.physicsEngine = physicsEngine;
        this.gameEngine = gameEngine;
    }

    public void nextLevel() {
        // Logic for transitioning to the next level
        game.setVelocityX(1.000);
        gameEngine.stop();
        physicsEngine.resetCollideFlags();
        game.setGoDownBall(true);
        game.setGoldStatus(false);
        game.setExistHeartBlock(false);
        game.setHitTime(0);
        game.setTime(0);
        game.setGoldTime(0);

        gameEngine.stop();

        game.clearBlocks();
        game.clearChocos();

        game.setDestroyedBlockCount(0);

        try {
            game.start(game.primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void restartGame() {
        // Logic for restarting the game
        game.setLevel(0);
        game.setHeart(3);
        game.setScore(0);
        game.setVelocityX(1.000);
        game.setDestroyedBlockCount(0);
        physicsEngine.resetCollideFlags();
        game.setGoDownBall(true);
        game.setGoldStatus(false);
        game.setExistHeartBlock(false);
        game.setHitTime(0);
        game.setTime(0);
        game.setGoldTime(0);
        game.clearBlocks();
        game.clearChocos();
        try {
            game.start(game.primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
