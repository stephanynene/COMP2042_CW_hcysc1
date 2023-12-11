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

public class Main extends Application implements GameEngine.OnAction {

    public boolean isGoldStatus() {
        return isGoldStatus;
    }

    public void setGoldStatus(boolean goldStatus) {
        isGoldStatus = goldStatus;
    }

    private boolean isGoldStatus = false;

    public boolean isExistHeartBlock() {
        return isExistHeartBlock;
    }

    public void setExistHeartBlock(boolean existHeartBlock) {
        isExistHeartBlock = existHeartBlock;
    }

    private boolean isExistHeartBlock = false;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int level = 0;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    private int  score    = 0;

    public ArrayList<Block> getBlocks() {
        return blocks;
    }

    public void setBlocks(ArrayList<Block> blocks) {
        this.blocks = blocks;
    }

    private ArrayList<Block> blocks = new ArrayList<Block>();
    private ArrayList<BlockView> blockViews = new ArrayList<>();


    public ArrayList<Bonus> getChocos() {
        return chocos;
    }

    public void setChocos(ArrayList<Bonus> chocos) {
        this.chocos = chocos;
    }


    private ArrayList<Bonus> chocos = new ArrayList<Bonus>();

    public void addToChocos( Bonus bonus) {
        chocos.add(bonus);
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


    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;



        if (loadFromSave == false) {
            level++;
            if (level >1){
                new Stats().showMessage(GameConstants.LEVEL_UP_MESSAGE.getStringValue(), this);
            }
            if (level == 18) {
                new Stats().showWin(this);
                return;
            }

            stats = new Stats();
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




    public static void main(String[] args) {
        launch(args);
    }


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
    private void createGameObjects() {
        blockManager = new BlockManager(root);
        blockManager.drawBlocks();

        ball = new Ball(GameConstants.BALL_RADIUS.getIntValue());
        ball.initBall(level);
        ballView = ball.getBallView();
        ball.setVelocity(level);

        board = new Board(this);
        board.initBoard();

        breakPaddle = new BreakPaddle();
        breakPaddle.initBreak();

    }
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

    private void createTimer(){
        timer = new Timer();
        timer.setGameTimeLimit(100000); // Set initial game time limit
        timer.setGameStartTime(System.currentTimeMillis());
    }

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


    public void restartGameLevel(){
        levelManager.restartGame();
 }

    //Updating score and heart labels - for use in UpdateElements class
    public void updateScoreLabel(int newScore) {
        scoreLabel.setText("Score: " + newScore);
    }
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


    public void clearBlocks() {
        blocks.clear();
    }

    public void clearChocos() {
        chocos.clear();
    }


}
