package brickGame.labels;

import brickGame.Main;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * The ScoreLabel class provides static methods for creating labels and buttons related to the game's score.
 */
public class ScoreLabel {

    /**
     * Creates a label with passed text and coordinates.
     *
     * @param text The text to be displayed on the label.
     * @param x    The x-coordinate of the label.
     * @param y    The y-coordinate of the label.
     * @param main The main instance of the game.
     * @return The created label.
     */
    public static Label createLabel(String text, double x, double y, Main main) {
        Label label = new Label(text);
        label.setTranslateX(x);
        label.setTranslateY(y);
        return label;
    }

    /**
     * Creates a button with the passed text and coordinates.
     *
     * @param text The text to be displayed on the button.
     * @param x    The x-coordinate of the button.
     * @param y    The y-coordinate of the button.
     * @param main The main instance of the game.
     * @return The created button.
     */
    public static Button createButton(String text, double x, double y, Main main) {
        Button button = new Button(text);
        button.setTranslateX(x);
        button.setTranslateY(y);
        return button;
    }
}