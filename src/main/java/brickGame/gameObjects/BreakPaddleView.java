package brickGame.gameObjects;
import brickGame.constants.GameConstants;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class BreakPaddleView extends Rectangle {

    public BreakPaddleView(Rectangle rect, double x, double y) {
        rect.setWidth(GameConstants.BREAK_WIDTH.getIntValue());
        rect.setHeight(GameConstants.BREAK_HEIGHT.getIntValue());
        rect.setX(x);
        rect.setY(y);

        ImagePattern pattern = new ImagePattern(new Image("block.jpg"));
        rect.setFill(pattern);
    }

    public void updatePosition(double x, double y) {
        this.setX(x);
        this.setY(y);
    }
}
