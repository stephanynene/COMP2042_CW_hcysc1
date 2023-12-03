module brickGame {
    requires javafx.fxml;
    requires javafx.controls;

    opens brickGame to javafx.fxml;
    exports brickGame;
    exports brickGame.saving;
    opens brickGame.saving to javafx.fxml;
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
    exports brickGame.gameObjects.ball;
    opens brickGame.gameObjects.ball to javafx.fxml;
    exports brickGame.gameObjects.block;
    opens brickGame.gameObjects.block to javafx.fxml;
    exports brickGame.gameObjects.bonus;
    opens brickGame.gameObjects.bonus to javafx.fxml;
    exports brickGame.gameObjects.breakpaddle;
    opens brickGame.gameObjects.breakpaddle to javafx.fxml;
    exports brickGame.gameObjects.board;
    opens brickGame.gameObjects.board to javafx.fxml;
}