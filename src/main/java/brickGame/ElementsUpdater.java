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
import java.util.ArrayList;
import java.util.List;

public class ElementsUpdater implements GameEngine.OnAction {

    private Main game;
    private BreakPaddle breakPaddle;
    private Ball ball;
    private PhysicsEngine physicsEngine;
    private Pane root;

    public ElementsUpdater(Main game, BreakPaddle breakPaddle, Ball ball, PhysicsEngine physicsEngine, Pane root) {
        this.game = game;
        this.breakPaddle = breakPaddle;
        this.ball = ball;
        this.physicsEngine = physicsEngine;
        this.root = root;
    }

    public void onUpdate() {
        Platform.runLater(this::updateUI);
        if (isBallWithinBounds()) {
            handleBlockCollisions();
        }
    }


    private void updateUI() {
        game.updateScoreLabel(game.getScore());
        game.updateHeartLabel(game.getHeart());

        // Update positions of UI elements
        breakPaddle.rect.setX(breakPaddle.getxBreak());
        breakPaddle.rect.setY(breakPaddle.getyBreak());
        ball.getBallView().setCenterX(ball.getxBall());
        ball.getBallView().setCenterY(ball.getyBall());

        // Update positions of bonus item
        updateChocos();
    }

    // Check if ball is within vertical game bounds
    private boolean isBallWithinBounds() {
        double paddingTop = Block.getPaddingTop();
        double ballY = ball.getyBall();
        double blockHeight = Block.getHeight();
        int level = game.getLevel();

        return ballY >= paddingTop && ballY <= (blockHeight * (level + 1)) + paddingTop;
    }

    private void handleBlockCollisions() {
        for (Block block : game.getBlocks()) {
            int hitCode = block.checkHitToBlock(ball.getxBall(), ball.getyBall());
            if (hitCode != GameConstants.NO_HIT.getIntValue()) {
                // Increase the score and handle the specific block hit
                game.setScore(game.getScore() + 1);
                handleBlockHit(block);
            }
        }
    }


    private void handleBlockHit(Block block) {
        // Show score, hide block, and update game state
        new Score().show(block.x, block.y, 1, game);
        block.getBlockView().getRect().setVisible(false);
        block.isDestroyed = true;
        game.setDestroyedBlockCount(game.getDestroyedBlockCount() + 1);
        physicsEngine.resetCollideFlags();

        // Handle different block types
        if (block.type == GameConstants.BLOCK_CHOCO.getIntValue()) {
            handleChocoBlockHit(block);
        } else if (block.type == GameConstants.BLOCK_STAR.getIntValue()) {
            handleStarBlockHit();
        } else if (block.type == GameConstants.BLOCK_HEART.getIntValue()) {
            game.setHeart(game.getHeart() + 1);
        }
    }

    // Handle hit to choco block
    private void handleChocoBlockHit(Block block) {
        final Bonus choco = new Bonus(block.row, block.column);
        choco.timeCreated = game.getTime();
        // Add choco to UI
        Platform.runLater(() -> root.getChildren().add(choco.choco));
        // Update game state on JavaFX thread
        Platform.runLater(() -> game.getChocos().add(choco));
    }

    // Handle hit to star block
    private void handleStarBlockHit() {
        // Set gold ball status and update UI
        game.setGoldTime(game.getTime());
        ImagePattern imagePattern = new ImagePattern(new Image("goldball.png"));
        ball.setFill(imagePattern);
        System.out.println("gold ball");
        root.getStyleClass().add("goldRoot");
        game.setGoldStatus(true);
    }

    // Update position of chocos
    private void updateChocos() {
        // Create a copy of the list to avoid ConcurrentModificationException
        List<Bonus> chocosCopy = new ArrayList<>(game.getChocos());

        for (Bonus choco : chocosCopy) {
            choco.choco.setY(choco.y);
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
