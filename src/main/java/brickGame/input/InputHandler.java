package brickGame.input;

import brickGame.Main;
import brickGame.saving.GameSaver;
import brickGame.gameObjects.ball.Ball;
import brickGame.gameObjects.breakpaddle.BreakPaddle;
import brickGame.stats.Stats;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public class InputHandler implements EventHandler<KeyEvent> {

    private BreakPaddle breakPaddle;
    private Ball ball;
    private Main game;
    private Stats stats;

    public InputHandler(BreakPaddle breakPaddle, Ball ball, Main game, Stats stats) {
        this.breakPaddle = breakPaddle;
        this.ball = ball;
        this.game = game;
        this.stats = stats;
    }

    //Keyboard for paddle
    @Override
    public void handle(KeyEvent event) {
        switch (event.getCode()) {
            case LEFT:
                breakPaddle.moveLeft();
                break;
            case RIGHT:
                breakPaddle.moveRight();
                break;
            case DOWN:
               // setPhysicsToBall();
                break;
            case S:
                GameSaver gameSaver = new GameSaver();
                gameSaver.saveGameState(game, breakPaddle, ball, stats);
                break;
        }
    }
}
