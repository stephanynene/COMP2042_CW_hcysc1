package brickGame.gameObjects;

import brickGame.GameConstants;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class BlockView {


    private Rectangle rect;
    public Rectangle getRect() {
        return rect;
    }
    public BlockView(double x, double y, double width, double height, Color color, int type) {
        rect = new Rectangle();
        rect.setWidth(width);
        rect.setHeight(height);
        rect.setX(x);
        rect.setY(y);

        if (type == GameConstants.BLOCK_CHOCO.getIntValue()) {
            Image image = new Image("choco.jpg");
            ImagePattern pattern = new ImagePattern(image);
            rect.setFill(pattern);
        } else if (type == GameConstants.BLOCK_HEART.getIntValue()) {
            Image image = new Image("heart.jpg");
            ImagePattern pattern = new ImagePattern(image);
            rect.setFill(pattern);
        } else if (type == GameConstants.BLOCK_STAR.getIntValue()) {
            Image image = new Image("star.jpg");
            ImagePattern pattern = new ImagePattern(image);
            rect.setFill(pattern);
        } else {
            rect.setFill(color);
        }
    }
}
