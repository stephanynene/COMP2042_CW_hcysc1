package brickGame.labels;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class BonusLabel {
    private final Pane root;
    private final javafx.scene.control.Label messageLabel;

    public BonusLabel(Pane root) {
        this.root = root;
        this.messageLabel = new javafx.scene.control.Label();
        initUI();
    }

    private void initUI() {
        root.getChildren().add(messageLabel);
    }

    public void showMessage(String message, double x, double y) {
        messageLabel.setText(message);

        // Center message
        double labelWidth = messageLabel.prefWidth(-1);
        messageLabel.setLayoutX((root.getWidth() - labelWidth) / 2);
        messageLabel.setLayoutY(root.getHeight() / 2);

        // Set a timeline to remove the message after a certain duration
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1.5), event -> messageLabel.setText("")));
        timeline.setCycleCount(1);
        timeline.play();
    }
}
