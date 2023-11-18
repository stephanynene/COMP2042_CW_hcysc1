package brickGame.Scoring;

import brickGame.GameConstants;
import brickGame.Main;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
//import sun.plugin2.message.Message;

public class Score {
    public void show(final double x, final double y, int score, final Main main) {
        String sign;
        if (score >= 0) {
            sign = "+";
        } else {
            sign = "";
        }
//        final Label label = new Label(sign + score);
//        label.setTranslateX(x);
//        label.setTranslateY(y);

//        String signScore = sign + score;
        Label label = ScoreLabel.createLabel(sign + score, x,y, main);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                main.root.getChildren().add(label);
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 21; i++) {
                    try {
                        label.setScaleX(i);
                        label.setScaleY(i);
                        label.setOpacity((20 - i) / 20.0);
                        Thread.sleep(15);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public void showMessage(String message, final Main main) {
//        final Label label = new Label(message);
//        label.setTranslateX(220);
//        label.setTranslateY(340);
Label label = ScoreLabel.createLabel(message, 220, 340, main);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                main.root.getChildren().add(label);
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 21; i++) {
                    try {
                        label.setScaleX(Math.abs(i-10));
                        label.setScaleY(Math.abs(i-10));
                        label.setOpacity((20 - i) / 20.0);
                        Thread.sleep(15);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public void showGameOver(final Main main) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
//                Label label = new Label(GameConstants.GAME_OVER_MESSAGE.getStringValue());
//                label.setTranslateX(200);
//                label.setTranslateY(250);
//
                Label label = ScoreLabel.createLabel(GameConstants.GAME_OVER_MESSAGE.getStringValue(),200,250, main);
                label.setScaleX(2);
                label.setScaleY(2);

//                Button restart = new Button("Restart");
//                restart.setTranslateX(220);
//                restart.setTranslateY(300);

                Button restart = ScoreLabel.createRestartButton(GameConstants.GAME_OVER_MESSAGE.getStringValue(), 220,300,main);
                restart.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        main.restartGame();
                    }
                });

                main.root.getChildren().addAll(label, restart);

            }
        });
    }

    public void showWin(final Main main) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
//                Label label = new Label(GameConstants.WIN_MESSAGE.getStringValue());
//                label.setTranslateX(200);
//                label.setTranslateY(250);

                Label label = ScoreLabel.createLabel(GameConstants.WIN_MESSAGE.getStringValue(), 200,250,main);
                label.setScaleX(2);
                label.setScaleY(2);
                main.root.getChildren().addAll(label);

            }
        });
    }
}
