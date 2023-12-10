package brickGame.input;

import brickGame.Main;
import brickGame.saving.GameSaver;
import brickGame.gameObjects.ball.Ball;
import brickGame.gameObjects.breakpaddle.BreakPaddle;
import brickGame.stats.Stats;
import brickGame.timer.Timer;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public class InputHandler implements EventHandler<KeyEvent> {

    private BreakPaddle breakPaddle;
    private Ball ball;
    private Main game;
    private Stats stats;
    private Timer timer;

    private boolean leftKeyPressed = false;
    private boolean rightKeyPressed = false;

    public InputHandler(BreakPaddle breakPaddle, Ball ball, Main game, Stats stats, Timer timer) {
        this.breakPaddle = breakPaddle;
        this.ball = ball;
        this.game = game;
        this.stats = stats;
        this.timer = timer;
    }

    // Keyboard for paddle
    @Override
    public void handle(KeyEvent event) {
        if (event.getEventType() == KeyEvent.KEY_PRESSED) {
            handleKeyPress(event);
        } else if (event.getEventType() == KeyEvent.KEY_RELEASED) {
            handleKeyRelease(event);
        }
    }

    public void handleKeyPress(KeyEvent event) {
        switch (event.getCode()) {
            case LEFT:
                leftKeyPressed = true;
                break;
            case RIGHT:
                rightKeyPressed = true;
                break;
            case DOWN:
                break;
            case S:
                GameSaver gameSaver = new GameSaver();
                gameSaver.saveGameState(game, breakPaddle, ball, stats, timer);
                break;
        }
        movePaddle();
    }

    public void handleKeyRelease(KeyEvent event) {
        switch (event.getCode()) {
            case LEFT:
                leftKeyPressed = false;
                break;
            case RIGHT:
                rightKeyPressed = false;
                break;
        }
    }
    public void movePaddle() {
        if (leftKeyPressed) {
            breakPaddle.moveLeft();
        }
        if (rightKeyPressed) {
            breakPaddle.moveRight();
        }
    }
}
