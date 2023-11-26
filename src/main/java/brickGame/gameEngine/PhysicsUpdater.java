package brickGame.gameEngine;

import brickGame.constants.GameConstants;
import brickGame.Main;
import brickGame.scoring.Score;
import brickGame.gameObjects.Ball;
import brickGame.gameObjects.Bonus;
import brickGame.gameObjects.BreakPaddle;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;

import java.util.ArrayList;

public class PhysicsUpdater implements GameEngine.OnAction {
    private Main game;
    private Ball ball;
    private Pane root;
    private Bonus bonus;
    private BreakPaddle breakPaddle;
    private PhysicsEngine physicsEngine;
    public PhysicsUpdater(Main game, Ball ball, Pane root, ArrayList<Bonus> bonuses, BreakPaddle breakPaddle, PhysicsEngine physicsEngine ) {
        this.game = game;
        this.ball = ball;
        this.root = root;
        this.breakPaddle = breakPaddle;
        this.physicsEngine = physicsEngine;
    }

    @Override
    public void onUpdate() {

    }

    @Override
    public void onInit() {

    }

    public void onPhysicsUpdate() {
        game.checkDestroyedCount();
        physicsEngine.setPhysicsToBall();
        updateGoldStatus();
        updateChocos();

    }

    @Override
    public void onTime(long time) {

    }

    private void updateChocos() {
        for (Bonus choco : game.getChocos()) {
            if (shouldSkipChocoUpdate(choco)) {
                continue;
            }
            handleChocoCollision(choco);
            updateChocoPosition(choco);
        }
    }

    private void handleChocoCollision(Bonus choco) {
        if (choco.y >= breakPaddle.getyBreak() && choco.y <= breakPaddle.getyBreak() + GameConstants.BREAK_WIDTH.getIntValue()
                && choco.x >= breakPaddle.getxBreak() && choco.x <= breakPaddle.getxBreak() + GameConstants.BREAK_WIDTH.getIntValue()) {
            handleChocoCollisionWithPaddle(choco);
        }
    }
    private void handleChocoCollisionWithPaddle(Bonus choco) {
        System.out.println("You Got it and +3 score for you");
        choco.taken = true;
        choco.choco.setVisible(false);
        game.setScore(game.getScore() +3 );
        new Score().show(choco.x, choco.y, 3, game);
    }
    private void updateChocoPosition(Bonus choco) {
        choco.y += ((game.getTime() - choco.timeCreated) / 1000.000) + 1.000;
    }
    private boolean shouldSkipChocoUpdate(Bonus choco) {
        return choco.y > GameConstants.SCENE_HEIGHT.getIntValue() || choco.taken;
    }
    private void updateGoldStatus() {
        if (game.getTime() - game.getGoldTime() > 5000) {
            resetGoldStatus();
        }
    }

    private void resetGoldStatus() {
        Platform.runLater(() -> {
            if (root != null) {
                root.getStyleClass().remove("goldRoot");
            }
            ball.setFill(new ImagePattern(new Image("ball.png")));
            root.getStyleClass().remove("goldRoot");
            game.setGoldStatus(false);
        });
    }

}
