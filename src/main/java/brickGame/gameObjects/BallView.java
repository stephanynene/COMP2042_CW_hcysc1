package brickGame.gameObjects;

import brickGame.constants.GameConstants;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class BallView extends Circle {

    public BallView(double radius) {
        super(radius);
    }

    public void setBallImage() {
        this.setFill(new ImagePattern(new Image(GameConstants.NORMAL_BALL_IMG.getStringValue())));
    }
}