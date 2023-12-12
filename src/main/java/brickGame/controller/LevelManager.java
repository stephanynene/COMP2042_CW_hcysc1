package brickGame.controller;

import brickGame.Main;
import brickGame.Sounds;
import brickGame.gameEngine.GameEngine;
import brickGame.gameObjects.ball.Ball;
import brickGame.stats.Stats;
import brickGame.timer.Timer;

/**
 * LevelManager class manages level by defining the nextLevel method and restartGame method
 *
 */
public class LevelManager {

    private Main game;
    private ConcretePhysicsEngine concretePhysicsEngine;
    private GameEngine gameEngine;
    private Stats stats;
    private Ball ball;
    private Timer timer;

    /**
     * Constructs a new LevelManager instance.
     *
     * @param game                The Main game instance.
     * @param concretePhysicsEngine The ConcretePhysicsEngine instance.
     * @param stats               The Stats instance.
     * @param ball                The Ball instance.
     * @param timer               The Timer instance.
     */
    public LevelManager(Main game, ConcretePhysicsEngine concretePhysicsEngine, Stats stats, Ball ball, Timer timer) {
        this.game = game;
        this.concretePhysicsEngine = concretePhysicsEngine;
        this.stats = stats;
        this.ball = ball;
        this.timer = timer;
    }

    /**
     * @param gameEngine
     * Sets the game engine
     */
    public void setLMGameEngine(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
    }

    /**
     * Advances game to next level.
     * Resets game parameters and starts the next level.
     */
    public void nextLevel() {

        Sounds sounds = new Sounds();
        sounds.playSound("next-level-sound");
        // Logic for transitioning to the next level
        ball.setVelocityX(1.000);
        gameEngine.stop();
        concretePhysicsEngine.resetCollideFlags();
        ball.setGoDownBall(true);
        game.setGoldStatus(false);
        game.setExistHeartBlock(false);
        stats.setHitTime(0);
        stats.setTime(0);
        stats.setGoldTime(0);

        gameEngine.stop();

        game.clearBlocks();
        game.clearChocos();

        stats.setDestroyedBlockCount(0);

        try {
            game.start(game.primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Restarts game for restart button use
     * Resets game parameters and starts game from the beginning.
     */
    public void restartGame() {
        // Logic for restarting the game
        game.setLevel(0);
        stats.setHeart(3);
        game.setScore(0);
        ball.setVelocityX(1.000);
        stats.setDestroyedBlockCount(0);
        concretePhysicsEngine.resetCollideFlags();
        ball.setGoDownBall(true);
        game.setGoldStatus(false);
        game.setExistHeartBlock(false);
        stats.setHitTime(0);
        stats.setTime(0);
        stats.setGoldTime(0);
        game.clearBlocks();
        game.clearChocos();
        try {
            game.start(game.primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
