package brickGame.input;

import brickGame.Main;
import brickGame.saving.GameSaver;
import brickGame.gameObjects.Ball;
import brickGame.gameObjects.BreakPaddle;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public class InputHandler implements EventHandler<KeyEvent> {

    private BreakPaddle breakPaddle;
    private Ball ball;
    private Main game;

    public InputHandler(BreakPaddle breakPaddle, Ball ball, Main game) {
        this.breakPaddle = breakPaddle;
        this.ball = ball;
        this.game = game;
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
                gameSaver.saveGameState(game, breakPaddle, ball);
                break;
        }
    }
}
