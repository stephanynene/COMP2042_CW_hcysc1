package brickGame.labels;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 * The BonusLabel class represents labelling for displaying bonus-related messages in the game.
 * It provides methods to show messages on the game screen and automatically
 * removes the message after a certain duration.
 */
public class BonusLabel {
    private final Pane root;
    private final javafx.scene.control.Label messageLabel;

    /**
     * Constructs a BonusLabel instance with the specified root pane.
     *
     * @param root The root pane of the game.
     */
    public BonusLabel(Pane root) {
        this.root = root;
        this.messageLabel = new javafx.scene.control.Label();
        initUI();
    }

    /**
     * Initializes the user interface by adding the message label to the root pane.
     */
    private void initUI() {
        root.getChildren().add(messageLabel);
    }

    /**
     * Displays message at the specified coordinates on the game screen.
     * The message is removed after a certain duration.
     *
     * @param message The message to be displayed.
     * @param x       The x-coordinate of the message.
     * @param y       The y-coordinate of the message.
     */
    public void showMessage(String message, double x, double y) {
        messageLabel.setText(message);

        // Center message
        double labelWidth = messageLabel.prefWidth(-1);
        messageLabel.setLayoutX((root.getWidth() - labelWidth) / 2);
        messageLabel.setLayoutY(root.getHeight() / 2);

        // Set timeline to remove the message after a certain duration
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1.5), event -> messageLabel.setText("")));
        timeline.setCycleCount(1);
        timeline.play();
    }
}
