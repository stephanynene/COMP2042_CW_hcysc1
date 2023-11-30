module brickGame {
    requires javafx.fxml;
    requires javafx.controls;

    opens brickGame to javafx.fxml;
    exports brickGame;
    exports brickGame.saving;
    opens brickGame.saving to javafx.fxml;
    exports brickGame.gameObjects;
    opens brickGame.gameObjects to javafx.fxml;
    exports brickGame.scoring;
    opens brickGame.scoring to javafx.fxml;
    exports brickGame.input;
    opens brickGame.input to javafx.fxml;
    exports brickGame.gameEngine;
    opens brickGame.gameEngine to javafx.fxml;
    exports brickGame.constants;
    opens brickGame.constants to javafx.fxml;
    exports brickGame.controller;
    opens brickGame.controller to javafx.fxml;
}