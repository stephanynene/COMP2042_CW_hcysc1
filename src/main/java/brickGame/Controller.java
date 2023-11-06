package brickGame;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class Controller {
Main main = new Main();
    @FXML
    private Label heartLabel;

    @FXML
    private Label levelLabel;

    @FXML
    private Button newGame;

    @FXML
    private Label scoreLabel;


    public void updateScore(int newScore) {
        scoreLabel.setText("Score: " + newScore);
    }

    public void updateLevel(int newLevel) {
        levelLabel.setText("Level: " + newLevel);
    }

    public void updateHearts(int newHearts) {
        heartLabel.setText("Hearts remaining: " + newHearts);
    }
}
