package brickGame.gameObjects.bonus;
import brickGame.gameObjects.block.Block;
import javafx.scene.shape.Rectangle;
import java.io.Serializable;

/**
 * The Bonus class represents a bonus object in the game.
 *
 * Bonuses appear on the game board and can be collected by contact with the player's breakpaddle.
 * Each bonus has a specific effect, and it can be either good (gain score) or bad (lose score) to the player.
 *
 * This class provides information about the bonus, including its visual representation, creation time,
 * position on the game board, and if it is good or bad bonus
 *
 */
public class Bonus implements Serializable {
    public Rectangle choco;
    private BonusView bonusView;
    public long timeCreated;
    private boolean isGood;
    public boolean taken = false;
    public double x;
    public double y;

    /**
     * Gets status of bonus
     * can be: good (true) or bad
     *
     *
     * @return True if the bonus is good; false otherwise.
     */
    public boolean isGood() {
        return isGood;
    }
    /**
     * Constructs a new Bonus object at a specified row and column on the game board.
     *
     * @param row    The row index where the bonus is located.
     * @param column The column index where the bonus is located.
     * @param isGood Determines whether the bonus is considered beneficial (true) or harmful (false).
     */
    public Bonus(int row, int column, boolean isGood) {
        x = (column * (Block.getWidth())) + Block.getPaddingH() + (Block.getWidth() / 2) - 15;
        y = (row * (Block.getHeight())) + Block.getPaddingTop() + (Block.getHeight() / 2) - 15;
        this.isGood = isGood;
        draw();
    }

    /**
     * Draws visual representation of the bonus on the game board.
     * Creates a new rectangular shape (choco) and initialises corresponding BonusView,
     * which is responsible for rendering the bonus with properties (position and type - good or bad).
     */
    private void draw() {
        choco = new Rectangle();
        bonusView = new BonusView(choco, x, y, isGood);
    }
}