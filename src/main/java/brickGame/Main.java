package brickGame;

import brickGame.Saving.GameSaver;
import brickGame.Saving.LoadSave;
import brickGame.Scoring.Score;
import brickGame.gameObjects.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;


import java.util.ArrayList;
import java.util.Random;

public class Main extends Application implements GameEngine.OnAction {

    public boolean isGoldStauts() {
        return isGoldStauts;
    }

    public void setGoldStauts(boolean goldStauts) {
        isGoldStauts = goldStauts;
    }

    private boolean isGoldStauts  = false;

    public boolean isExistHeartBlock() {
        return isExistHeartBlock;
    }

    public void setExistHeartBlock(boolean existHeartBlock) {
        isExistHeartBlock = existHeartBlock;
    }

    private boolean isExistHeartBlock = false;

    public int getDestroyedBlockCount() {
        return destroyedBlockCount;
    }

    public void setDestroyedBlockCount(int destroyedBlockCount) {
        this.destroyedBlockCount = destroyedBlockCount;
    }

    private int destroyedBlockCount = 0;



    public int getHeart() {
        return heart;
    }

    public void setHeart(int heart) {
        this.heart = heart;
    }
    private int  heart    = 3;
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

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    private long time = 0;

    public long getHitTime() {
        return hitTime;
    }

    public void setHitTime(long hitTime) {
        this.hitTime = hitTime;
    }

    private long hitTime  = 0;

    public long getGoldTime() {
        return goldTime;
    }

    public void setGoldTime(long goldTime) {
        this.goldTime = goldTime;
    }

    private long goldTime = 0;
    float oldXBreak;

    public boolean isGoDownBall() {
        return goDownBall;
    }

    public void setGoDownBall(boolean goDownBall) {
        this.goDownBall = goDownBall;
    }

    private boolean goDownBall  = true;

    public boolean isGoRightBall() {
        return goRightBall;
    }

    public void setGoRightBall(boolean goRightBall) {
        this.goRightBall = goRightBall;
    }

    private boolean goRightBall  = true;

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
    private ArrayList<Bonus> chocos = new ArrayList<Bonus>();
    public  Pane root;
    private Label scoreLabel;
    private Label heartLabel;
    private Label levelLabel;

    private boolean loadFromSave = false;

    Stage  primaryStage;
    Button load    = null;
    Button newGame = null;

    private BreakPaddle breakPaddle;
    private Ball ball;
    private BallView ballView;
    private GameEngine gameEngine;
    private PhysicsEngine physicsEngine;
    private InputHandler inputHandler;

    private Board board;
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;

        if (loadFromSave == false) {
            level++;
            if (level >1){
                new Score().showMessage("Level Up :)", this);
            }
            if (level == 18) {
                new Score().showWin(this);
                return;
            }

            drawBlocks();
            ball = new Ball(GameConstants.BALL_RADIUS.getIntValue());
            ball.initBall(level);
            ballView = ball.getBallView();

            board = new Board(this);
            board.initBoard();

            breakPaddle = new BreakPaddle();
            breakPaddle.initBreak();
            physicsEngine = new PhysicsEngine(this, ball, breakPaddle, gameEngine);
            inputHandler = new InputHandler(breakPaddle, ball, this);

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
        heartLabel = new Label("Heart : " + heart);
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
                gameEngine = new GameEngine();
                gameEngine.setOnAction(this);
                gameEngine.setFps(120);
                gameEngine.start();
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
                    gameEngine = new GameEngine();
                    gameEngine.setOnAction(Main.this);
                    gameEngine.setFps(120);
                    gameEngine.start();

                    load.setVisible(false);
                    newGame.setVisible(false);
                }
            });
        } else {
            gameEngine = new GameEngine();
            gameEngine.setOnAction(this);
            gameEngine.setFps(120);
            gameEngine.start();
            loadFromSave = false;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void checkDestroyedCount() {
        if (destroyedBlockCount == blocks.size()) {
            //TODO win level todo...
            //System.out.println("You Win");

            nextLevel();
        }
    }
    private void drawBlocks() {
        for (Block block : blocks) {
            BlockView blockView = block.getBlockView();
            blockViews.add(blockView);
            root.getChildren().add(blockView.getRect());
        }
    }
    private void loadGame() {

        LoadSave loadSave = new LoadSave();
        loadSave.read();

        isExistHeartBlock = loadSave.isExistHeartBlock;
        isGoldStauts = loadSave.isGoldStauts;
        goDownBall = loadSave.goDownBall;
        goRightBall = loadSave.goRightBall;
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
        heart = loadSave.heart;
        destroyedBlockCount = loadSave.destroyedBlockCount;
        ball.setxBall(loadSave.xBall);
        ball.setyBall(loadSave.yBall);
        breakPaddle.setxBreak(loadSave.xBreak);
        breakPaddle.setyBreak(loadSave.yBreak);
        breakPaddle.setCenterBreakX(loadSave.centerBreakX);
        time = loadSave.time;
        goldTime = loadSave.goldTime;
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

    private void nextLevel() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    velocityX = 1.000;

                    gameEngine.stop();
                    physicsEngine.resetCollideFlags();
                    goDownBall = true;

                    isGoldStauts = false;
                    isExistHeartBlock = false;

                    hitTime = 0;
                    time = 0;
                    goldTime = 0;

                    gameEngine.stop();
                    blocks.clear();
                    chocos.clear();
                    destroyedBlockCount = 0;
                    start(primaryStage);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void restartGame() {

        try {
            level = 0;
            heart = 3;
            score = 0;
            velocityX = 1.000;
            destroyedBlockCount = 0;
            physicsEngine.resetCollideFlags();
            goDownBall = true;

            isGoldStauts = false;
            isExistHeartBlock = false;
            hitTime = 0;
            time = 0;
            goldTime = 0;

            blocks.clear();
            chocos.clear();

            start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpdate() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                scoreLabel.setText("Score: " + score);
                heartLabel.setText("Heart : " + heart);

                breakPaddle.rect.setX(breakPaddle.getxBreak());
                breakPaddle.rect.setY(breakPaddle.getyBreak());
                ballView.setCenterX(ball.getxBall());
                ballView.setCenterY(ball.getyBall());

                for (Bonus choco : chocos) {
                    choco.choco.setY(choco.y);
                }
            }
        });


        if (ball.getyBall() >= Block.getPaddingTop() && ball.getyBall() <= (Block.getHeight() * (level + 1)) + Block.getPaddingTop()) {
            for (final Block block : blocks) {
                int hitCode = block.checkHitToBlock(ball.getxBall(), ball.getyBall());
                if (hitCode != GameConstants.NO_HIT.getIntValue()) {
                    score += 1;

                    new Score().show(block.x, block.y, 1, this);

                    block.getBlockView().getRect().setVisible(false);
                    block.isDestroyed = true;
                    destroyedBlockCount++;
                    //System.out.println("size is " + blocks.size());
                    physicsEngine.resetCollideFlags();

                    if (block.type == GameConstants.BLOCK_CHOCO.getIntValue()) {
                        final Bonus choco = new Bonus(block.row, block.column);
                        choco.timeCreated = time;
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                root.getChildren().add(choco.choco);
                            }
                        });
                        chocos.add(choco);
                    }

                    if (block.type == GameConstants.BLOCK_STAR.getIntValue()) {
                        goldTime = time;
                        ImagePattern imagePattern = new ImagePattern(new Image("goldball.png"));
                        ball.setFill(imagePattern);
                        System.out.println("gold ball");
                        root.getStyleClass().add("goldRoot");
                        isGoldStauts = true;
                    }

                    if (block.type == GameConstants.BLOCK_HEART.getIntValue()) {
                        heart++;
                    }

                    if (hitCode == GameConstants.HIT_RIGHT.getIntValue()) {
                        colideToRightBlock = true;
                    } else if (hitCode == GameConstants.HIT_BOTTOM.getIntValue()) {
                        colideToBottomBlock = true;
                    } else if (hitCode == GameConstants.HIT_LEFT.getIntValue()) {
                        colideToLeftBlock = true;
                    } else if (hitCode == GameConstants.HIT_TOP.getIntValue()) {
                        colideToTopBlock = true;
                    }

                }

                //TODO hit to break and some work here....
                //System.out.println("Break in row:" + block.row + " and column:" + block.column + " hit");
            }
        }
    }

    @Override
    public void onInit() {

    }

    @Override
    public void onPhysicsUpdate() {

        checkDestroyedCount();
        physicsEngine.setPhysicsToBall();


        if (time - goldTime > 5000) {
            ball.setFill(new ImagePattern(new Image("ball.png")));
            root.getStyleClass().remove("goldRoot");
            isGoldStauts = false;
        }

        for (Bonus choco : chocos) {
            if (choco.y > GameConstants.SCENE_HEIGHT.getIntValue() || choco.taken) {
                continue;
            }
            if (choco.y >= breakPaddle.getyBreak() && choco.y <= breakPaddle.getyBreak() + GameConstants.BREAK_WIDTH.getIntValue()  && choco.x >= breakPaddle.getxBreak() && choco.x <= breakPaddle.getxBreak() + GameConstants.BREAK_WIDTH.getIntValue()) {
                System.out.println("You Got it and +3 score for you");
                choco.taken = true;
                choco.choco.setVisible(false);
                score += 3;
                new Score().show(choco.x, choco.y, 3, this);
            }
            choco.y += ((time - choco.timeCreated) / 1000.000) + 1.000;
        }
        //System.out.println("time is:" + time + " goldTime is " + goldTime);
    }

    @Override
    public void onTime(long time) {
        this.time = time;
    }
}
