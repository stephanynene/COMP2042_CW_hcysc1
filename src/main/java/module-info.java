module brickGame {
    requires javafx.fxml;
    requires javafx.controls;

    opens brickGame to javafx.fxml;
    exports brickGame;
    exports brickGame.Saving;
    opens brickGame.Saving to javafx.fxml;
    exports brickGame.gameObjects;
    opens brickGame.gameObjects to javafx.fxml;
    exports brickGame.Scoring;
    opens brickGame.Scoring to javafx.fxml;
}