package brickGame.stats;

import brickGame.Main;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class ScoreLabel {


    //Method used in creating label in Score class
    public static Label createLabel(String text, double x, double y, Main main) {
        Label label = new Label(text);
        label.setTranslateX(x);
        label.setTranslateY(y);
        return label;
    }

// Method used in creating button in Score class
    public static Button createButton(String text, double x, double y, Main main) {
        Button restart = new Button(text);
        restart.setTranslateX(x);
        restart.setTranslateY(y);
//        restart.setOnAction(event -> main.restartGame());
        return restart;
    }
}