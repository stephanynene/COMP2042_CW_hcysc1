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
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
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

//
//    public boolean isGoRightBall() {
//        return goRightBall;
//    }
//
//    public void setGoRightBall(boolean goRightBall) {
//        this.goRightBall = goRightBall;
//    }
//
//    private boolean goRightBall  = true;

    public boolean isColideToBreak() {
        return colideToBreak;
    }

    public void setColideToBreak(boolean colideToBreak) {
        this.colideToBreak = colideToBreak;
    }

    private boolean colideToBreak = false; // Boolean, true when ball collides with Paddle

    public boolean isColideToBreakAndMoveToRight() {
        return colideToBreakAndMoveToRight;
    }

    public void setColideToBreakAndMoveToRight(boolean colideToBreakAndMoveToRight) {
        this.colideToBreakAndMoveToRight = colideToBreakAndMoveToRight;
    }

    private boolean colideToBreakAndMoveToRight = true;

    public boolean isColideToRightWall() {
        return colideToRightWall;
    }

    public void setColideToRightWall(boolean colideToRightWall) {
        this.colideToRightWall = colideToRightWall;
    }

    private boolean colideToRightWall = false;

    public boolean isColideToLeftWall() {
        return colideToLeftWall;
    }

    public void setColideToLeftWall(boolean colideToLeftWall) {
        this.colideToLeftWall = colideToLeftWall;
    }

    private boolean colideToLeftWall = false;

    public boolean isColideToRightBlock() {
        return colideToRightBlock;
    }

    public void setColideToRightBlock(boolean colideToRightBlock) {
        this.colideToRightBlock = colideToRightBlock;
    }

    private boolean colideToRightBlock          = false;

    public boolean isColideToBottomBlock() {
        return colideToBottomBlock;
    }

    public void setColideToBottomBlock(boolean colideToBottomBlock) {
        this.colideToBottomBlock = colideToBottomBlock;
    }

    private boolean colideToBottomBlock         = false;

    public boolean isColideToLeftBlock() {
        return colideToLeftBlock;
    }

    public void setColideToLeftBlock(boolean colideToLeftBlock) {
        this.colideToLeftBlock = colideToLeftBlock;
    }

    private boolean colideToLeftBlock           = false;

    public boolean isColideToTopBlock() {
        return colideToTopBlock;
    }

    public void setColideToTopBlock(boolean colideToTopBlock) {
        this.colideToTopBlock = colideToTopBlock;
    }

    private boolean colideToTopBlock = false;
    public double getVelocity() {
        return velocity;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    private double velocity = 1.000;
    public double getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(double velocityX) {
        this.velocityX = velocityX;
    }

    private double velocityX = 1.000;

    public double getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(double velocityY) {
        this.velocityY = velocityY;
    }

    private double velocityY = 1.000;



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

    private boolean loadFromSave = false;

    public Stage  primaryStage;
    Button load    = null;
    Button newGame = null;

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
                new Stats().showMessage("Level Up :)", this);
            }
            if (level == 18) {
                new Stats().showWin(this);
                return;
            }
            stats = new Stats();
            blockManager = new BlockManager(root);
            blockManager.drawBlocks();


            ball = new Ball(GameConstants.BALL_RADIUS.getIntValue());
            ball.initBall(level);
            ballView = ball.getBallView();

            board = new Board(this);
            board.initBoard();

            breakPaddle = new BreakPaddle();
            breakPaddle.initBreak();

            inputHandler = new InputHandler(breakPaddle, ball, this, stats);

            load = new Button("Resume Load Game");
            newGame = new Button("Start New Game");
            load.setTranslateX(220);
            load.setTranslateY(300);
            newGame.setTranslateX(220);
            newGame.setTranslateY(340);
        }

        root = new Pane();
        root.setPrefSize(GameConstants.SCENE_WIDTH.getIntValue(), GameConstants.SCENE_HEIGHT.getIntValue());
        scoreLabel = new Label("Score: " + score);
        levelLabel = new Label("Level: " + level);
        levelLabel.setTranslateY(20);
        heartLabel = new Label("Heart : " + stats.getHeart());
        heartLabel.setTranslateX(GameConstants.SCENE_WIDTH.getIntValue() - 70);

        if (loadFromSave == false) {
            root.getChildren().addAll(breakPaddle.rect, ballView, scoreLabel, heartLabel, levelLabel, newGame);
        } else {
            root.getChildren().addAll(breakPaddle.rect, ballView, scoreLabel, heartLabel, levelLabel);
        }

        for (Block block : blocks) {
            root.getChildren().add(block.getBlockView().getRect());


        }
        Scene scene = new Scene(root, GameConstants.SCENE_WIDTH.getIntValue(), GameConstants.SCENE_HEIGHT.getIntValue());
        scene.getStylesheets().add("style.css");
        scene.setOnKeyPressed(inputHandler);

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
        // Create instances of classes that implement the PhysicsEngine interface
        concretePhysicsEngine = new ConcretePhysicsEngine(this, ball, breakPaddle, stats);

        physicsUpdater = new PhysicsUpdater(this, ball, root, chocos, breakPaddle, concretePhysicsEngine, stats);
        elementsUpdater = new ElementsUpdater(this, breakPaddle, ball, concretePhysicsEngine, root, stats);
        levelManager = new LevelManager(this, concretePhysicsEngine, stats, ball);

        // Initialize game engine only after physicsUpdater and elementsUpdater are intialised
        gameEngine = new GameEngine(this, physicsUpdater, elementsUpdater);
        gameEngine.setFps(120);
        gameEngine.start();

        // Set game engine in the physics engine and level manager
        ((ConcretePhysicsEngine) concretePhysicsEngine).setPEGameEngine(gameEngine);
        ((LevelManager) levelManager).setLMGameEngine(gameEngine);


    }

    public void checkDestroyedCount() {
        if (stats.getDestroyedBlockCount() == blocks.size()) {
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
        colideToBreak = loadSave.colideToBreak;
        colideToBreakAndMoveToRight = loadSave.colideToBreakAndMoveToRight;
        colideToRightWall = loadSave.colideToRightWall;
        colideToLeftWall = loadSave.colideToLeftWall;
        colideToRightBlock = loadSave.colideToRightBlock;
        colideToBottomBlock = loadSave.colideToBottomBlock;
        colideToLeftBlock = loadSave.colideToLeftBlock;
        colideToTopBlock = loadSave.colideToTopBlock;
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
        velocityX = loadSave.vX;

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
    }
    public void clearBlocks() {
        blocks.clear();
    }

    public void clearChocos() {
        chocos.clear();
    }
}
