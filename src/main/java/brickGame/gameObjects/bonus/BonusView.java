package brickGame.gameObjects.bonus;

import brickGame.constants.GameConstants;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.util.Random;

public class BonusView {

    public BonusView(Rectangle choco, double x, double y) {
        choco.setWidth(30);
        choco.setHeight(30);
        choco.setX(x);
        choco.setY(y);

        String url;
        if (new Random().nextInt(20) % 2 == 0) {
            url = GameConstants.BONUS1_IMG.getStringValue();
        } else {
            url = GameConstants.BONUS2_IMG.getStringValue();
        }

        choco.setFill(new ImagePattern(new Image(url)));
    }
}
