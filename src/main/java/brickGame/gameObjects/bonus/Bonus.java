package brickGame.gameObjects.bonus;
import brickGame.gameObjects.block.Block;
import javafx.scene.shape.Rectangle;
import java.io.Serializable;


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
    }
}