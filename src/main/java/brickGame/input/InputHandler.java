package brickGame.input;

import brickGame.Main;
import brickGame.Sounds;
import brickGame.saving.GameSaver;
import brickGame.gameObjects.ball.Ball;
import brickGame.gameObjects.breakpaddle.BreakPaddle;
import brickGame.stats.Stats;
import brickGame.timer.Timer;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

/**
 * The InputHandler class manages user input events -> handling key presses and releases.
 * It is responsible for controlling the movement of the breakable paddle and triggering various game actions.
 */
public class InputHandler implements EventHandler<KeyEvent> {

    private BreakPaddle breakPaddle;
    private Ball ball;
    private Main game;
    private Stats stats;
    private Timer timer;

    private boolean leftKeyPressed = false;
    private boolean rightKeyPressed = false;
    private int muteCount = 0;

    /**
     * Constructs an InputHandler instance with references to the game objects and components.
     *
     * @param breakPaddle The breakable paddle controlled by user input.
     * @param ball        The game ball.
     * @param game        The main game instance.
     * @param stats       The game statistics.
     * @param timer       The game timer.
     */
    public InputHandler(BreakPaddle breakPaddle, Ball ball, Main game, Stats stats, Timer timer) {
        this.breakPaddle = breakPaddle;
        this.ball = ball;
        this.game = game;
        this.stats = stats;
        this.timer = timer;
    }

    // Keyboard for paddle
    /**
     * Handles both key presses and key releases.
     *
     * @param event The KeyEvent representing the user's input.
     */
    @Override
    public void handle(KeyEvent event) {
        if (event.getEventType() == KeyEvent.KEY_PRESSED) {
            handleKeyPress(event);
        } else if (event.getEventType() == KeyEvent.KEY_RELEASED) {
            handleKeyRelease(event);
        }
    }

    /**
     * Handles key presses and triggers corresponding actions.
     * LEFT -> sets leftKeyPressed true and moves break paddle left
     * RIGHT -> sets rightKeyPressed tre and moves break paddle right
     * S - > Save game for loading
     * M - > Mute and unmute background music
     * @param event The KeyEvent representing the key press event.
     */
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
            case M:
               toggleBackgroundMusicMute();
        }
        movePaddle();
    }

    /**
     * Handles key releases and updates corresponding flags.
     *
     * @param event The KeyEvent representing the key release event.
     */
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

    /**
     * Moves the breakpaddle based on the user input.
     */
    public void movePaddle() {
        if (leftKeyPressed) {
            breakPaddle.moveLeft();
        }
        if (rightKeyPressed) {
            breakPaddle.moveRight();
        }
    }
    /**
     * Toggles the mute state of the background music when the 'M' key is pressed.
     */
    private void toggleBackgroundMusicMute() {
        if (muteCount % 2 == 0) {
            Sounds.muteBackgroundMusic();
        } else {
            Sounds.unmuteBackgroundMusic();
        }
        muteCount++;
    }
}
