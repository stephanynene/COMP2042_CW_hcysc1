package brickGame;

import brickGame.Saving.GameSaver;
import brickGame.Saving.LoadSave;
import brickGame.gameObjects.Ball;
import brickGame.gameObjects.Block;
import brickGame.gameObjects.Bonus;
import brickGame.gameObjects.BreakPaddle;
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

public class Main extends Application implements EventHandler<KeyEvent>, GameEngine.OnAction {


    public double getCenterBreakX() {
        return centerBreakX;
    }

    public void setCenterBreakX(double centerBreakX) {
        this.centerBreakX = centerBreakX;
    }

    private double centerBreakX;

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

    private double v = 1.000;

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

    public double getvX() {
        return vX;
    }

    public void setvX(double vX) {
        this.vX = vX;
    }

    private double vX = 1.000;

    public double getvY() {
        return vY;
    }

    public void setvY(double vY) {
        this.vY = vY;
    }

    private double vY = 1.000;



    public ArrayList<Block> getBlocks() {
        return blocks;
    }

    public void setBlocks(ArrayList<Block> blocks) {
        this.blocks = blocks;
    }

    private ArrayList<Block> blocks = new ArrayList<Block>();
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
    private GameEngine engine;
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

            ball = new Ball();
            ball.initBall(level);
            initBoard();
            breakPaddle = new BreakPaddle();
            breakPaddle.initBreak();

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
            root.getChildren().addAll(breakPaddle.rect, ball, scoreLabel, heartLabel, levelLabel, newGame);

        } else {
            root.getChildren().addAll(breakPaddle.rect, ball, scoreLabel, heartLabel, levelLabel);
        }
        for (Block block : blocks) {
            root.getChildren().add(block.rect);
        }
        Scene scene = new Scene(root, GameConstants.SCENE_WIDTH.getIntValue(), GameConstants.SCENE_HEIGHT.getIntValue());
        scene.getStylesheets().add("style.css");
        scene.setOnKeyPressed(this);

        primaryStage.setTitle("Game");
        primaryStage.setScene(scene);
        primaryStage.show();

        if (loadFromSave == false) {
            if (level > 1 && level < 18) {
                load.setVisible(false);
                newGame.setVisible(false);
                engine = new GameEngine();
                engine.setOnAction(this);
                engine.setFps(120);
                engine.start();
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
                    engine = new GameEngine();
                    engine.setOnAction(Main.this);
                    engine.setFps(120);
                    engine.start();

                    load.setVisible(false);
                    newGame.setVisible(false);
                }
            });
        } else {
            engine = new GameEngine();
            engine.setOnAction(this);
            engine.setFps(120);
            engine.start();
            loadFromSave = false;
        }
    }

    private void initBoard() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < level + 1; j++) {
                int r = new Random().nextInt(500);
                if (r % 5 == 0) {
                    continue;
                }
                int type;
                if (r % 10 == 1) {
                    type = Block.BLOCK_CHOCO;
                } else if (r % 10 == 2) {
                    if (!isExistHeartBlock) {
                        type = Block.BLOCK_HEART;
                        isExistHeartBlock = true;
                    } else {
                        type = Block.BLOCK_NORMAL;
                    }
                } else if (r % 10 == 3) {
                    type = Block.BLOCK_STAR;
                } else {
                    type = Block.BLOCK_NORMAL;
                }

                Color[] colors = (Color[]) ((Object[]) GameConstants.COLORS.getValue());
                blocks.add(new Block(j, i, colors[r % colors.length], type));


                //System.out.println("colors " + r % (colors.length));
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
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
                gameSaver.saveGameState(this, breakPaddle, ball);
                break;
        }
    }



    private void resetCollideFlags() {

        colideToBreak = false;
        colideToBreakAndMoveToRight = false;
        colideToRightWall = false;
        colideToLeftWall = false;

        colideToRightBlock = false;
        colideToBottomBlock = false;
        colideToLeftBlock = false;
        colideToTopBlock = false;
    }

    private void setPhysicsToBall() {
     v = ((time - hitTime) / 1000.000) + 1.000;

       // v = 0;
        if (goDownBall) {
            double currentY = ball.getyBall(); // Get the current yBall value
            currentY += vY; // Update the yBall value
            ball.setyBall(currentY);
        } else {
            double currentY = ball.getyBall();
            currentY -= vY;
            ball.setyBall(currentY);
        }

        if (goRightBall) {
            double currentX = ball.getxBall();
            currentX += vX;
            ball.setxBall(currentX);
        } else {
            double currentX = ball.getxBall();
            currentX -= vX;
            ball.setxBall(currentX);
        }

        if (ball.getyBall() <= 0) {
            //vX = 1.000;
            resetCollideFlags();
            goDownBall = true;
            return;
        }
        if (ball.getyBall() >= GameConstants.SCENE_HEIGHT.getIntValue()) {

            goDownBall = false;
            if (!isGoldStauts) {
                //TODO gameover
                heart--;
                new Score().show(GameConstants.SCENE_WIDTH.getIntValue() / 2, GameConstants.SCENE_HEIGHT.getIntValue() / 2, -1, this);

                if (heart == 0) {
                    new Score().showGameOver(this);
                    engine.stop();
                }

            }
            //return;
        }

        if (ball.getyBall() >= breakPaddle.getyBreak() - ball.getBallRadius()) {
            //System.out.println("Colide1");
            if (ball.getxBall() >= breakPaddle.getxBreak() && ball.getxBall() <= breakPaddle.getxBreak() + GameConstants.BREAK_WIDTH.getIntValue() ) {
                hitTime = time;
                resetCollideFlags();
                colideToBreak = true;
                goDownBall = false;

                double relation = (ball.getxBall() - centerBreakX) / (GameConstants.BREAK_WIDTH.getIntValue() / 2);

                if (Math.abs(relation) <= 0.3) {
                    //vX = 0;
                    vX = 0.5 * Math.abs(relation);
                } else if (Math.abs(relation) > 0.3 && Math.abs(relation) <= 0.7) {
                    vX = 0.7 * ((Math.abs(relation) * 1.5) + (level / 3.500));
                    System.out.println("vX " + vX);
                } else {
                    vX = 0.6 * ((Math.abs(relation) * 2) + (level / 3.500));
                    System.out.println("vX " + vX);
                }

                if (ball.getxBall() - centerBreakX > 0) {
                    colideToBreakAndMoveToRight = true;
                } else {
                    colideToBreakAndMoveToRight = false;
                }
                //System.out.println("Colide2");
            }
        }

        if (ball.getxBall() >= GameConstants.SCENE_WIDTH.getIntValue()) {
            resetCollideFlags();
            //vX = 1.000;
            colideToRightWall = true;
        }

        if (ball.getxBall() <= 0) {
            resetCollideFlags();
            //vX = 1.000;
            colideToLeftWall = true;
        }

        if (colideToBreak) {
            if (colideToBreakAndMoveToRight) {
                goRightBall = true;
            } else {
                goRightBall = false;
            }
        }

        //Wall Colide

        if (colideToRightWall) {
            goRightBall = false;
        }

        if (colideToLeftWall) {
            goRightBall = true;
        }

        //Block Colide

        if (colideToRightBlock) {
            goRightBall = true;
        }

        if (colideToLeftBlock) {
            goRightBall = true;
        }

        if (colideToTopBlock) {
            goDownBall = false;
        }

        if (colideToBottomBlock) {
            goDownBall = true;
        }
    }

    private void checkDestroyedCount() {
        if (destroyedBlockCount == blocks.size()) {
            //TODO win level todo...
            //System.out.println("You Win");

            nextLevel();
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
        centerBreakX = loadSave.centerBreakX;
        time = loadSave.time;
        goldTime = loadSave.goldTime;
        vX = loadSave.vX;

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
                    vX = 1.000;

                    engine.stop();
                    resetCollideFlags();
                    goDownBall = true;

                    isGoldStauts = false;
                    isExistHeartBlock = false;

                    hitTime = 0;
                    time = 0;
                    goldTime = 0;

                    engine.stop();
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
            vX = 1.000;
            destroyedBlockCount = 0;
            resetCollideFlags();
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
                ball.setCenterX(ball.getxBall());
                ball.setCenterY(ball.getyBall());

                for (Bonus choco : chocos) {
                    choco.choco.setY(choco.y);
                }
            }
        });


        if (ball.getyBall() >= Block.getPaddingTop() && ball.getyBall() <= (Block.getHeight() * (level + 1)) + Block.getPaddingTop()) {
            for (final Block block : blocks) {
                int hitCode = block.checkHitToBlock(ball.getxBall(), ball.getyBall());
                if (hitCode != Block.NO_HIT) {
                    score += 1;

                    new Score().show(block.x, block.y, 1, this);

                    block.rect.setVisible(false);
                    block.isDestroyed = true;
                    destroyedBlockCount++;
                    //System.out.println("size is " + blocks.size());
                    resetCollideFlags();

                    if (block.type == Block.BLOCK_CHOCO) {
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

                    if (block.type == Block.BLOCK_STAR) {
                        goldTime = time;
                        ImagePattern imagePattern = new ImagePattern(new Image("goldball.png"));
                        ball.setFill(imagePattern);
                        System.out.println("gold ball");
                        root.getStyleClass().add("goldRoot");
                        isGoldStauts = true;
                    }

                    if (block.type == Block.BLOCK_HEART) {
                        heart++;
                    }

                    if (hitCode == Block.HIT_RIGHT) {
                        colideToRightBlock = true;
                    } else if (hitCode == Block.HIT_BOTTOM) {
                        colideToBottomBlock = true;
                    } else if (hitCode == Block.HIT_LEFT) {
                        colideToLeftBlock = true;
                    } else if (hitCode == Block.HIT_TOP) {
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
        setPhysicsToBall();


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
