package brickGame.saving;

import brickGame.constants.BlockSerializable;
import brickGame.constants.GameConstants;
import brickGame.Main;
import brickGame.stats.Stats;
import brickGame.gameObjects.ball.Ball;
import brickGame.gameObjects.block.Block;
import brickGame.gameObjects.breakpaddle.BreakPaddle;
import brickGame.timer.Timer;
import javafx.application.Platform;


import java.io.*;
import java.util.ArrayList;


public class GameSaver {

    public void saveGameState(Main gameInstance, BreakPaddle breakPaddle, Ball ball, Stats stats, Timer timer) {
        new File(GameConstants.SAVE_PATH_DIR.getStringValue()).mkdirs();
        File file = new File(GameConstants.SAVE_PATH.getStringValue());

        try {
            if (file.getParentFile().mkdirs() || file.createNewFile()) {
                System.out.println("File created successfully");
            } else {
                System.out.println("File exists");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file))) {
            outputStream.writeInt(gameInstance.getLevel());
            outputStream.writeInt(gameInstance.getScore());
            outputStream.writeInt(stats.getHeart());
            outputStream.writeInt(stats.getDestroyedBlockCount());
            outputStream.writeLong(timer.getElapsedTime());
            outputStream.writeLong(timer.getRemainingSeconds());


            outputStream.writeDouble(ball.getxBall());
            outputStream.writeDouble(ball.getyBall());
            outputStream.writeDouble(breakPaddle.getxBreak());
            outputStream.writeDouble(breakPaddle.getyBreak());
            outputStream.writeDouble(breakPaddle.getCenterBreakX());
            outputStream.writeLong(stats.getTime());
            outputStream.writeLong(stats.getGoldTime());
            outputStream.writeDouble(ball.getVelocityX());

            outputStream.writeBoolean(gameInstance.isExistHeartBlock());
            outputStream.writeBoolean(gameInstance.isGoldStatus());
            outputStream.writeBoolean(ball.isGoDownBall());
            outputStream.writeBoolean(ball.isGoRightBall());
            outputStream.writeBoolean(ball.isColideToBreak());
            outputStream.writeBoolean(ball.isColideToBreakAndMoveToRight());
            outputStream.writeBoolean(ball.isColideToRightWall());
            outputStream.writeBoolean(ball.isColideToLeftWall());
            outputStream.writeBoolean(ball.isColideToRightBlock());
            outputStream.writeBoolean(ball.isColideToBottomBlock());
            outputStream.writeBoolean(ball.isColideToLeftBlock());
            outputStream.writeBoolean(ball.isColideToTopBlock());

            ArrayList<BlockSerializable> blockSerializables = new ArrayList<>();

            synchronized (gameInstance.getBlocks()) {
                for (Block block : gameInstance.getBlocks()) {
                    if (block.isDestroyed) {
                        continue;
                    }
                    blockSerializables.add(new BlockSerializable(block.row, block.column, block.type));
                }
            }

            outputStream.writeObject(blockSerializables);

            Platform.runLater(() -> {
                new Stats().showMessage("Game Saved", gameInstance);
                System.out.println("Game Saved");
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}