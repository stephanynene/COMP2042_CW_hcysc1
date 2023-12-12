package brickGame;

import brickGame.constants.BlockSerializable;
import brickGame.constants.GameConstants;
import brickGame.controller.ElementsUpdater;
import brickGame.controller.LevelManager;
import brickGame.gameEngine.GameEngine;
import brickGame.controller.ConcretePhysicsEngine;
import brickGame.controller.PhysicsUpdater;
import brickGame.gameObjects.ball.Ball;
import brickGame.gameObjects.ball.BallView;
import brickGame.gameObjects.block.Block;
import brickGame.gameObjects.block.BlockManager;
import brickGame.gameObjects.block.BlockView;
import brickGame.gameObjects.board.Board;
import brickGame.gameObjects.breakpaddle.BreakPaddle;
import brickGame.input.InputHandler;
import brickGame.saving.GameStateReader;
import brickGame.stats.Stats;
import brickGame.timer.Timer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import brickGame.gameObjects.bonus.Bonus;

import java.util.ArrayList;
import java.util.Random;


/**
 *
 * Original Source Code link:
 * {@linkplain <a href="https://github.com/kooitt/CourseworkGame">GitHub - CourseworkGame</a>}.
 *  Original Main class:
 *  {@linkplain <a href="https://github.com/kooitt/CourseworkGame/blob/master/src/main/java/brickGame/Main.java">
 *   Main.java</a>}.
 *
 * The Main class is the entry point of the Space Brick Breaker game.
 * It extends the JavaFX Application class and initialises various game components,
 * such as the game engine, physics engine, input handling, and more.
 * The game starts with the creation of the main game window and allows users
 * to interact with the game through keyboard inputs. It also manages game state,
 * levels, and provides functionality for saving and loading the game.
 * The Space Brick Breaker game involves breaking blocks with a ball and paddle.
 * Players progress through levels, earning points and encountering various challenges.
 *  The game supports functionalities such as saving, loading, and resuming from a previous state.
 * @author Stephanie Chung Sing Hung
 *
 *
 */
public class Main extends Application implements GameEngine.OnAction {
    private boolean isGoldStatus = false;
    private boolean isExistHeartBlock = false;
    public int level = 0;
    private int  score    = 0;
    private ArrayList<Block> blocks = new ArrayList<Block>();
    private ArrayList<Bonus> chocos = new ArrayList<Bonus>();
    /**
     * Gets the current gold status of the game.
     *
     * @return {@code true} if the game is in a gold status, {@code false} otherwise.
     */
    public boolean isGoldStatus() {
        return isGoldStatus;
    }

    /**
     * Sets the gold status of the game.
     *
     * @param goldStatus {@code true} to enable gold status, {@code false} to disable it.
     */
    public void setGoldStatus(boolean goldStatus) {
        isGoldStatus = goldStatus;
    }

    /**
     * Checks if there is a heart block present in the game.
     *
     * @return {@code true} if a heart block exists, {@code false} otherwise.
     */
    public boolean isExistHeartBlock() {
        return isExistHeartBlock;
    }

    /**
     * Sets the presence of a heart block in the game.
     *
     * @param existHeartBlock {@code true} to indicate the presence of a heart block, {@code false} otherwise.
     */
    public void setExistHeartBlock(boolean existHeartBlock) {
        isExistHeartBlock = existHeartBlock;
    }

    /**
     * Gets the current level of the game.
     *
     * @return The current level of the game.
     */
    public int getLevel() {
        return level;
    }

    /**
     * Sets the level of the game.
     *
     * @param level The level to set.
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * Gets the current score in the game.
     *
     * @return The current score.
     */
    public int getScore() {
        return score;
    }

    /**
     * Sets the score in the game.
     *
     * @param score The score to set.
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Gets the list of blocks in the game.
     *
     * @return The list of blocks.
     */
    public ArrayList<Block> getBlocks() {
        return blocks;
    }

    /**
     * Sets the list of blocks in the game.
     *
     * @param blocks The list of blocks to set.
     */
    public void setBlocks(ArrayList<Block> blocks) {
        this.blocks = blocks;
    }

    /**
     * Gets the list of bonus objects (chocos) in the game.
     *
     * @return The list of bonus objects.
     */
    public ArrayList<Bonus> getChocos() {
        return chocos;
    }

    public  Pane root;
    private Label scoreLabel;
    private Label heartLabel;
    private Label levelLabel;
    private Label countdownLabel;

    private boolean loadFromSave = false;

    public Stage  primaryStage;
    Button load    = null;
    Button newGame = null;
    private Timer timer;
    private BreakPaddle breakPaddle;
    private Ball ball;
    private BallView ballView;
    private GameEngine gameEngine;
    private PhysicsUpdater physicsUpdater;
    private ElementsUpdater elementsUpdater;
    private ConcretePhysicsEngine concretePhysicsEngine;
    private InputHandler inputHandler;
    private BlockManager blockManager;
    private LevelManager levelManager;
    private Board board;
    private Stats stats;

    /**
     * The start method serves as the entry point for JavaFX applications. It contains the initialisation of various game components,
     * creates the main game scene, and manages button actions. The method is responsible for setting up the initial game state,
     * loading saved games and transition between levels.
     *
     * @param primaryStage  primary stage for the JavaFX application, providing the main window.
     * @throws Exception An exception is thrown if any error occurs during the application start process.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;

        // Initialize game components based on whether it's a new game or loaded from save
        if (loadFromSave == false) {
            level++;

            if (level >1){
                new Stats().showMessage(GameConstants.LEVEL_UP_MESSAGE.getStringValue(), this);
            }
            if (level == 18) {
                new Stats().showWin(this);
                return;
            }

            if (level == 1) {
                stats = new Stats();
                stats.setHeart(3);
            }

            createTimer();
            createGameObjects();
            inputHandler = new InputHandler(breakPaddle, ball, this, stats, timer);
            createStartGameButtons();

        }

        root = new Pane();
        root.setPrefSize(GameConstants.SCENE_WIDTH.getIntValue(), GameConstants.SCENE_HEIGHT.getIntValue());

        createScreenLabels();

        if (loadFromSave == false) {
            root.getChildren().addAll(breakPaddle.rect, ballView, scoreLabel, heartLabel, levelLabel, countdownLabel, newGame, load);

        } else {
            root.getChildren().addAll(breakPaddle.rect, ballView, scoreLabel, heartLabel, levelLabel, countdownLabel, newGame, load);
        }

        for (Block block : blocks) {
            root.getChildren().add(block.getBlockView().getRect());


        }
        Scene scene = new Scene(root, GameConstants.SCENE_WIDTH.getIntValue(), GameConstants.SCENE_HEIGHT.getIntValue());
        scene.getStylesheets().add("style.css");
        scene.setOnKeyPressed(inputHandler::handleKeyPress);
        scene.setOnKeyReleased(inputHandler::handleKeyRelease);

        primaryStage.setTitle("Space Brick Breaker");
        primaryStage.setScene(scene);
        primaryStage.show();


        if (loadFromSave == false) {
            if (level > 1 && level < 18) {
                load.setVisible(false);
                newGame.setVisible(false);
                initGameComponents();
            }

            load.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    loadGame();
                    load.setVisible(false);
                    newGame.setVisible(false);
                }
            });

            newGame.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    initGameComponents();
                    load.setVisible(false);
                    newGame.setVisible(false);
                }
            });
        } else {
            initGameComponents();
            loadFromSave = false;
        }
    }


    /**
     *
     * launches the JavaFX application, initiating the JavaFX runtime environment.
     *
     * @param args Command-line arguments provided to the application.
     */
    public static void main(String[] args) {
        launch(args);
    }


    /**
     * Initalises components related to controller
     * and passes needed parameters to them
     */
    private void initGameComponents(){

        Sounds.playBackgroundMusic();
        Sounds.setBackgroundMusicVolume(0.8);

        // Create instances of classes that implement the PhysicsEngine interface
        concretePhysicsEngine = new ConcretePhysicsEngine(this, ball, breakPaddle, stats);

        physicsUpdater = new PhysicsUpdater(this, ball, root, chocos, breakPaddle, concretePhysicsEngine, stats, inputHandler);
        elementsUpdater = new ElementsUpdater(this, breakPaddle, ball, concretePhysicsEngine, root, stats);
        levelManager = new LevelManager(this, concretePhysicsEngine, stats, ball, timer);

        // Initialise game engine only after physicsUpdater and elementsUpdater are intialised
        gameEngine = new GameEngine(this, physicsUpdater, elementsUpdater);
        gameEngine.setFps(120);
        gameEngine.start();

        // Set game engine in the physics engine and level manager
        ((ConcretePhysicsEngine) concretePhysicsEngine).setPEGameEngine(gameEngine);
        ((LevelManager) levelManager).setLMGameEngine(gameEngine);
        ((Timer) timer).setGameEngineTimer(gameEngine);

    }

    /**
     * Initalises game objects and passes necessary parameters to them
     */
    private void createGameObjects() {
        blockManager = new BlockManager(root);
        blockManager.drawBlocks();

        ball = new Ball(GameConstants.BALL_RADIUS.getIntValue());
        ball.initBall();
        ballView = ball.getBallView();
        ball.setVelocity(level);

        board = new Board(this);
        board.initBoard();

        breakPaddle = new BreakPaddle();
        breakPaddle.initBreak();
    }

    /**
     * Creates newGame and load button for the start of game so user can choose either one
     */
    private void createStartGameButtons(){
        newGame = new Button("Start New Game");
        newGame.getStyleClass().add("custom-button");
        newGame.setTranslateX(180);
        newGame.setTranslateY(340);

        load = new Button("Resume Load Game");
        load.setTranslateX(170);
        load.setTranslateY(390);
        load.getStyleClass().add("custom-button");
    }

    /**
     * Creates the necessary labels that are needed on screen
     *
     */
    private void createScreenLabels() {
        scoreLabel = new Label("Score: " + score);
        scoreLabel.getStyleClass().add("custom-label");

        levelLabel = new Label("Level: " + level);
        levelLabel.setTranslateY(20);
        levelLabel.getStyleClass().add("custom-label");


        heartLabel = new Label("Heart : " + stats.getHeart());
        heartLabel.getStyleClass().add("custom-label");
        heartLabel.setTranslateX(GameConstants.SCENE_WIDTH.getIntValue() - 80);

        countdownLabel = new Label("Timer: " + (timer.getGameTimeLimit() / 1000) + "s");
        countdownLabel.setTranslateX(200);
        countdownLabel.getStyleClass().add("custom-label");
    }

    /**
     * Creates the Timer instance and sets the initial game limit to 100 seconds and the current time
     */
    private void createTimer(){
        timer = new Timer();
        timer.setGameTimeLimit(100000); // Set initial game time limit
        timer.setGameStartTime(System.currentTimeMillis());
    }

    /**
     * Checks to see if all the blocks are destroyed by iterating through the block list
     * If a block is not destroyed, returns false
     * if all destroyed, then proceed to the next level
     */
    public void checkDestroyedCount() {
        boolean allDestroyed = true;
        for (Block block : blocks) {
            if (!block.isDestroyed) {
                allDestroyed = false;
            }
        }
        if (allDestroyed) {
            Platform.runLater(() -> levelManager.nextLevel());
        }
    }

    /**
     * Creates a new instance of GameStateReader and uses the read method from there
     *  Updates various game attributes and components based on the loaded data.
     *  Restarts the game with the loaded state
     */
    private void loadGame() {

        GameStateReader gameStateReader = new GameStateReader();
        gameStateReader.read();

        isExistHeartBlock = gameStateReader.isExistHeartBlock;
        isGoldStatus = gameStateReader.isGoldStauts;
        ball.setGoDownBall(gameStateReader.goDownBall);
        ball.setGoRightBall(gameStateReader.goRightBall);
        ball.setColideToBreak(gameStateReader.colideToBreak);
        ball.setColideToBreakAndMoveToRight(gameStateReader.colideToBreakAndMoveToRight);
        ball.setColideToRightWall(gameStateReader.colideToRightWall);
        ball.setColideToLeftWall(gameStateReader.colideToLeftWall);
        ball.setColideToRightBlock(gameStateReader.colideToRightBlock);
        ball.setColideToBottomBlock(gameStateReader.colideToBottomBlock);
        ball.setColideToLeftBlock(gameStateReader.colideToLeftBlock);
        ball.setColideToTopBlock(gameStateReader.colideToTopBlock);
        level = gameStateReader.level;
        score = gameStateReader.score;
        stats.setHeart(gameStateReader.heart);
        stats.setDestroyedBlockCount(gameStateReader.destroyedBlockCount);
        ball.setxBall(gameStateReader.xBall);
        ball.setyBall(gameStateReader.yBall);
        breakPaddle.setxBreak(gameStateReader.xBreak);
        breakPaddle.setyBreak(gameStateReader.yBreak);
        breakPaddle.setCenterBreakX(gameStateReader.centerBreakX);
        stats.setTime(gameStateReader.time);
        stats.setGoldTime(gameStateReader.goldTime);
        ball.setVelocityX(gameStateReader.vX);

        timer.setRemainingSeconds(gameStateReader.remainingSeconds);
        timer.setElapsedTime(gameStateReader.elapsedTime);
        timer.setGameStartTime(timer.getGameStartTime() - timer.getElapsedTime());

        blocks.clear();
        chocos.clear();

        for (BlockSerializable ser : gameStateReader.blocks) {
            int r = new Random().nextInt(200);
            Color[] colors = GameConstants.COLORS.getValue();
            blocks.add(new Block(ser.row, ser.j, colors[r % colors.length], ser.type, 0));
        }
        try {
            loadFromSave = true;
            start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * restarts the game by calling restartGame method from levelManager
     */
    public void restartGameLevel(){
        levelManager.restartGame();
 }

    /**
     * Updates the score label with the given new score value.
     *
     * @param newScore The new score value to be displayed.
     */
    public void updateScoreLabel(int newScore) {
        scoreLabel.setText("Score: " + newScore);
    }

    /**
     * Updates the heart label with the given new heart value.
     *
     * @param newHeart The new heart value to be displayed.
     */
    public void updateHeartLabel(int newHeart) {
        heartLabel.setText("Heart: " + newHeart);
    }



    @Override
    public void onUpdate() {

    }

    @Override
    public void onInit() {

    }
    @Override
    public void onPhysicsUpdate() {

    }

    /**
     * Called when a time update event occurs in the game.
     *
     * tracks elapsed time, checks for time-based game events,updates the countdown timer on the game screen.
     *
     * @param time The current time in milliseconds.
     *             Represents the time elapsed since the start of the game.
     *
     *
     * The default implementation calculates the elapsed time, checks for time limit exceedance,
     *           and updates the countdown timer UI element on the game screen.
     *           It triggers a game over method from Timer if the time limit is exceeded.
     */
    @Override
    public void onTime(long time) {
        stats.setTime(time);

        // Calculate elapsed time
        timer.setElapsedTime(System.currentTimeMillis() - timer.getGameStartTime());

        // Check if the player has exceeded the time limit
        if (timer.getElapsedTime() > timer.getGameTimeLimit()) {
            timer.timeUpGameOver(this);
        }

        // Update the countdown timer on the game screen
        timer.updateCountdownTimer(timer.getGameTimeLimit() - timer.getElapsedTime(), countdownLabel);
    }


    /**
     * Clears blocks
     */
    public void clearBlocks() {
        blocks.clear();
    }

    /**
     * Clears chocos
     */
    public void clearChocos() {
        chocos.clear();
    }


}
