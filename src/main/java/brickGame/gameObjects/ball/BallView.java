package brickGame.gameObjects.ball;

import brickGame.constants.GameConstants;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class BallView extends Circle {

    public BallView(double radius) {
        super(radius);
    }

    public void setBallImage(GameConstants ballType) {
        this.setFill(new ImagePattern(new Image(ballType.getStringValue())));
    }
}