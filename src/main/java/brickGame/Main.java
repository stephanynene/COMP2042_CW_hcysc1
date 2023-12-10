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
import brickGame.saving.LoadSave;
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
import java.util.Scanner;

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

            timer = new Timer();
            timer.setGameTimeLimit(100000); // Set initial game time limit
            timer.setGameStartTime(System.currentTimeMillis());


            blockManager = new BlockManager(root);
            blockManager.drawBlocks();


            ball = new Ball(GameConstants.BALL_RADIUS.getIntValue());
            ball.initBall(level);
            ballView = ball.getBallView();
            ball.setVelocity(level);
            System.out.println(ball.getVelocity());

            board = new Board(this);
            board.initBoard();

            breakPaddle = new BreakPaddle();
            breakPaddle.initBreak();

            inputHandler = new InputHandler(breakPaddle, ball, this, stats, timer);

            load = new Button("Resume Load Game");
            newGame = new Button("Start New Game");
            load.setTranslateX(194);
            load.setTranslateY(375);
            newGame.setTranslateX(203);
            newGame.setTranslateY(340);

            promptForLoadOrNewGame();
        }

        root = new Pane();
        root.setPrefSize(GameConstants.SCENE_WIDTH.getIntValue(), GameConstants.SCENE_HEIGHT.getIntValue());
        scoreLabel = new Label("Score: " + score);
        levelLabel = new Label("Level: " + level);
        levelLabel.setTranslateY(20);
        heartLabel = new Label("Heart : " + stats.getHeart());
        heartLabel.setTranslateX(GameConstants.SCENE_WIDTH.getIntValue() - 70);

        countdownLabel = new Label("Timer: " + (timer.getGameTimeLimit() / 1000) + "s");
        countdownLabel.setTranslateX(200);


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

        primaryStage.setTitle("Game");
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

        Sounds sounds = new Sounds();
        sounds.playBackgroundMusic();
        sounds.setBackgroundMusicVolume(0.8);

        // Create instances of classes that implement the PhysicsEngine interface
        concretePhysicsEngine = new ConcretePhysicsEngine(this, ball, breakPaddle, stats);

        physicsUpdater = new PhysicsUpdater(this, ball, root, chocos, breakPaddle, concretePhysicsEngine, stats, inputHandler);
        elementsUpdater = new ElementsUpdater(this, breakPaddle, ball, concretePhysicsEngine, root, stats);
        levelManager = new LevelManager(this, concretePhysicsEngine, stats, ball, timer);

        // Initialize game engine only after physicsUpdater and elementsUpdater are intialised
        gameEngine = new GameEngine(this, physicsUpdater, elementsUpdater);
        gameEngine.setFps(120);
        gameEngine.start();

        // Set game engine in the physics engine and level manager
        ((ConcretePhysicsEngine) concretePhysicsEngine).setPEGameEngine(gameEngine);
        ((LevelManager) levelManager).setLMGameEngine(gameEngine);
        ((Timer) timer).setGameEngineTimer(gameEngine);


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

        LoadSave loadSave = new LoadSave();
        loadSave.read();

        isExistHeartBlock = loadSave.isExistHeartBlock;
        isGoldStatus = loadSave.isGoldStauts;
        ball.setGoDownBall(loadSave.goDownBall);
        ball.setGoRightBall(loadSave.goRightBall);
        ball.setColideToBreak(loadSave.colideToBreak);
        ball.setColideToBreakAndMoveToRight(loadSave.colideToBreakAndMoveToRight);
        ball.setColideToRightWall(loadSave.colideToRightWall);
        ball.setColideToLeftWall(loadSave.colideToLeftWall);
        ball.setColideToRightBlock(loadSave.colideToRightBlock);
        ball.setColideToBottomBlock(loadSave.colideToBottomBlock);
        ball.setColideToLeftBlock(loadSave.colideToLeftBlock);
        ball.setColideToTopBlock(loadSave.colideToTopBlock);
        level = loadSave.level;
        score = loadSave.score;
        stats.setHeart(loadSave.heart);
        stats.setDestroyedBlockCount(loadSave.destroyedBlockCount);
        ball.setxBall(loadSave.xBall);
        ball.setyBall(loadSave.yBall);
        breakPaddle.setxBreak(loadSave.xBreak);
        breakPaddle.setyBreak(loadSave.yBreak);
        breakPaddle.setCenterBreakX(loadSave.centerBreakX);
        stats.setTime(loadSave.time);
        stats.setGoldTime(loadSave.goldTime);
        ball.setVelocityX(loadSave.vX);

        timer.setRemainingSeconds(loadSave.remainingSeconds);
        timer.setElapsedTime(loadSave.elapsedTime);

        blocks.clear();
        chocos.clear();

        for (BlockSerializable ser : loadSave.blocks) {
            int r = new Random().nextInt(200);
            Color[] colors = GameConstants.COLORS.getValue();
            blocks.add(new Block(ser.row, ser.j, colors[r % colors.length], ser.type));


        }

        try {
            loadFromSave = true;
            start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void promptForLoadOrNewGame() {
        // Set button actions
        load.setOnAction(event -> loadGame());
        newGame.setOnAction(event -> initGameComponents());

        // Hide existing buttons
        load.setVisible(false);
        newGame.setVisible(false);

        load.setVisible(true);
        newGame.setVisible(true);
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
            timer.timeUpGameOver(this); // Implement this method to handle the game-over condition
        }

        // Update the countdown timer on the game screen (you may need to convert milliseconds to seconds or minutes)
        timer.updateCountdownTimer(timer.getGameTimeLimit() - timer.getElapsedTime(), countdownLabel);
    }


    public void clearBlocks() {
        blocks.clear();
    }

    public void clearChocos() {
        chocos.clear();
    }
}
