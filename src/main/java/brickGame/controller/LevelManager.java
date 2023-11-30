package brickGame.controller;

import brickGame.Main;
import brickGame.gameEngine.GameEngine;

public class LevelManager {

    private Main game;
    private ConcretePhysicsEngine concretePhysicsEngine;
    private GameEngine gameEngine;
    public LevelManager(Main game, ConcretePhysicsEngine concretePhysicsEngine) {
        this.game = game;
        this.concretePhysicsEngine = concretePhysicsEngine;
    }

    public void setLMGameEngine(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
        System.out.println(gameEngine);
    }

    public void nextLevel() {
        // Logic for transitioning to the next level
        game.setVelocityX(1.000);
        gameEngine.stop();
        concretePhysicsEngine.resetCollideFlags();
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
        concretePhysicsEngine.resetCollideFlags();
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
