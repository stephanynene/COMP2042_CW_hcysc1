package brickGame.gameObjects.block;

import brickGame.constants.GameConstants;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

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

        setOnImageType(type, color);
    }

    private void setBlockImage(String imagePath) {
        Image image = new Image(imagePath);
        ImagePattern pattern = new ImagePattern(image);
        rect.setFill(pattern);
    }

    private void setOnImageType(int type, Color color) {
        if (type == GameConstants.BLOCK_CHOCO.getIntValue()) {
            setBlockImage(GameConstants.CHOCO_BLOCK_IMG.getStringValue());
        } else if (type == GameConstants.BLOCK_HEART.getIntValue()) {
            setBlockImage(GameConstants.HEART_BLOCK_IMG.getStringValue());
        } else if (type == GameConstants.BLOCK_STAR.getIntValue()) {
            setBlockImage(GameConstants.STAR_BLOCK_IMG.getStringValue());
        } else if (type == GameConstants.BLOCK_STURDY.getIntValue()) {
            setBlockImage(GameConstants.STURDY_BLOCK_IMG.getStringValue());
        } else if (type == GameConstants.BLOCK_THUNDER.getIntValue()) {
            setBlockImage(GameConstants.THUNDER_BLOCK_IMG.getStringValue());
        } else {
            rect.setFill(color);
        }
    }
}