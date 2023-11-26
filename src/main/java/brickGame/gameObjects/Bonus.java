package brickGame.gameObjects;

import brickGame.gameObjects.Block;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.Serializable;
import java.util.Random;

public class Bonus implements Serializable {
    public Rectangle choco;
    private BonusView bonusView;
    public double x;
    public double y;
    public long timeCreated;
    public boolean taken = false;

    public BonusView getBonusView() {
        return bonusView;
    }

    public Bonus(int row, int column) {
        x = (column * (Block.getWidth())) + Block.getPaddingH() + (Block.getWidth() / 2) - 15;
        y = (row * (Block.getHeight())) + Block.getPaddingTop() + (Block.getHeight() / 2) - 15;

        draw();
    }

    private void draw() {
        choco = new Rectangle();
        bonusView = new BonusView(choco, x, y);
        //        choco.setWidth(30);
//        choco.setHeight(30);
//        choco.setX(x);
//        choco.setY(y);
//
//        String url;
//        if (new Random().nextInt(20) % 2 == 0) {
//            url = "bonus1.png";
//        } else {
//            url = "bonus2.png";
//        }
//
//        choco.setFill(new ImagePattern(new Image(url)));
    }
}