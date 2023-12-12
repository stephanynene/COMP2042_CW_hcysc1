package brickGame.gameObjects.bonus;

import brickGame.constants.GameConstants;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class BonusView {

    public BonusView(Rectangle choco, double x, double y, boolean isGood) {
        choco.setWidth(30);
        choco.setHeight(30);
        choco.setX(x);
        choco.setY(y);

        String url;
        if (isGood) {
            // image for good bonus
            url = GameConstants.GOOD_BONUS_IMG.getStringValue();
        } else {
            // image for bad bonus
            url = GameConstants.BAD_BONUS_IMG.getStringValue();
        }

        choco.setFill(new ImagePattern(new Image(url)));
    }
}
