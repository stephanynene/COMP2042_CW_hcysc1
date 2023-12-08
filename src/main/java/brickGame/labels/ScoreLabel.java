package brickGame.labels;

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

//create button
    public static Button createButton(String text, double x, double y, Main main) {
        Button button = new Button(text);
        button.setTranslateX(x);
        button.setTranslateY(y);
        return button;
    }
}