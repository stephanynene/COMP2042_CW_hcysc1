package brickGame;

import brickGame.constants.GameConstants;
import brickGame.gameEngine.GameEngine;
import brickGame.gameEngine.PhysicsEngine;
import brickGame.gameObjects.Ball;
import brickGame.gameObjects.Block;
import brickGame.gameObjects.Bonus;
import brickGame.gameObjects.BreakPaddle;
import brickGame.scoring.Score;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;

public class UpdateElements implements GameEngine.OnAction {

    private Main game;
    private BreakPaddle breakPaddle;
    private Ball ball;
    private PhysicsEngine physicsEngine;
    private Pane root;

    public UpdateElements(Main game, BreakPaddle breakPaddle, Ball ball, PhysicsEngine physicsEngine, Pane root) {
        this.game = game;
        this.breakPaddle = breakPaddle;
        this.ball = ball;
        this.physicsEngine = physicsEngine;
        this.root = root;
    }

//    public void UpdateElements(Main game, BreakPaddle breakPaddle, Ball ball, PhysicsEngine physicsEngine, Pane root){
//        this.game = game;
//        this.breakPaddle = breakPaddle;
//        this.ball = ball;
//        this.physicsEngine = physicsEngine;
//        this.root = root;
//    }
    public void onUpdate() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                game.updateScoreLabel(game.getScore());
                game.updateHeartLabel(game.getHeart());

                breakPaddle.rect.setX(breakPaddle.getxBreak());
                breakPaddle.rect.setY(breakPaddle.getyBreak());
                ball.setCenterX(ball.getxBall());
                ball.setCenterY(ball.getyBall());

                for (Bonus choco : game.getChocos()) {
                    choco.choco.setY(choco.y);
                }
            }
        });


        if (ball.getyBall() >= Block.getPaddingTop() && ball.getyBall() <= (Block.getHeight() * (game.getLevel() + 1)) + Block.getPaddingTop()) {
            for (final Block block : game.getBlocks()) {
                int hitCode = block.checkHitToBlock(ball.getxBall(), ball.getyBall());
                if (hitCode != GameConstants.NO_HIT.getIntValue()) {
                    game.setScore(game.getScore() + 1);

                    new Score().show(block.x, block.y, 1, game);

                    block.rect.setVisible(false);
                    block.isDestroyed = true;
                    game.setDestroyedBlockCount(game.getDestroyedBlockCount()+1);
                    //System.out.println("size is " + blocks.size());
                    physicsEngine.resetCollideFlags();

                    if (block.type == GameConstants.BLOCK_CHOCO.getIntValue()) {
                        final Bonus choco = new Bonus(block.row, block.column);
                        choco.timeCreated = game.getTime();
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                root.getChildren().add(choco.choco);
                            }
                        });
                        game.getChocos().add(choco);
                    }

                    if (block.type == GameConstants.BLOCK_STAR.getIntValue()) {
                        game.setGoldTime(game.getTime());
                        ImagePattern imagePattern = new ImagePattern(new Image("goldball.png"));
                        ball.setFill(imagePattern);
                        System.out.println("gold ball");
                        root.getStyleClass().add("goldRoot");
                        game.setGoldStauts(true);
                    }

                    if (block.type == GameConstants.BLOCK_HEART.getIntValue()) {
                        game.setHeart(game.getHeart() + 1);
                    }

                    if (hitCode == GameConstants.HIT_RIGHT.getIntValue()) {
                        game.setColideToRightBlock(true);
                    } else if (hitCode == GameConstants.HIT_BOTTOM.getIntValue()) {
                        game.setColideToBottomBlock(true);
                    } else if (hitCode == GameConstants.HIT_LEFT.getIntValue()) {
                        game.setColideToLeftBlock(true);
                    } else if (hitCode == GameConstants.HIT_TOP.getIntValue()) {
                        game.setColideToTopBlock(true);
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

    }

    @Override
    public void onTime(long time) {

    }
}
