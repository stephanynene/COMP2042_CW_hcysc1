package brickGame.gameObjects.bonus;
import brickGame.gameObjects.block.Block;
import javafx.scene.shape.Rectangle;
import java.io.Serializable;


public class Bonus implements Serializable {
    public Rectangle choco;
    private BonusView bonusView;
    public long timeCreated;

    public boolean isGood() {
        return isGood;
    }

    public void setGood(boolean good) {
        isGood = good;
    }

    private boolean isGood;
    public boolean taken = false;
    public double x;
    public double y;
    public Bonus(int row, int column, boolean isGood) {
        x = (column * (Block.getWidth())) + Block.getPaddingH() + (Block.getWidth() / 2) - 15;
        y = (row * (Block.getHeight())) + Block.getPaddingTop() + (Block.getHeight() / 2) - 15;
        this.isGood = isGood;
        draw();
    }

    private void draw() {
        choco = new Rectangle();
        bonusView = new BonusView(choco, x, y, isGood);
    }
}