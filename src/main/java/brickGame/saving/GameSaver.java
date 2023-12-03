package brickGame.saving;

import brickGame.constants.BlockSerializable;
import brickGame.constants.GameConstants;
import brickGame.Main;
import brickGame.stats.Stats;
import brickGame.gameObjects.ball.Ball;
import brickGame.gameObjects.block.Block;
import brickGame.gameObjects.breakpaddle.BreakPaddle;
import javafx.application.Platform;


import java.io.*;
import java.util.ArrayList;


public class GameSaver {

    public void saveGameState(Main gameInstance, BreakPaddle breakPaddle, Ball ball, Stats stats) {
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

            outputStream.writeDouble(ball.getxBall());
            outputStream.writeDouble(ball.getyBall());
            outputStream.writeDouble(breakPaddle.getxBreak());
            outputStream.writeDouble(breakPaddle.getyBreak());
            outputStream.writeDouble(breakPaddle.getCenterBreakX());
            outputStream.writeLong(stats.getTime());
            outputStream.writeLong(gameInstance.getGoldTime());
            outputStream.writeDouble(gameInstance.getVelocityX());

            outputStream.writeBoolean(gameInstance.isExistHeartBlock());
            outputStream.writeBoolean(gameInstance.isGoldStatus());
            outputStream.writeBoolean(gameInstance.isGoDownBall());
            outputStream.writeBoolean(gameInstance.isGoRightBall());
            outputStream.writeBoolean(gameInstance.isColideToBreak());
            outputStream.writeBoolean(gameInstance.isColideToBreakAndMoveToRight());
            outputStream.writeBoolean(gameInstance.isColideToRightWall());
            outputStream.writeBoolean(gameInstance.isColideToLeftWall());
            outputStream.writeBoolean(gameInstance.isColideToRightBlock());
            outputStream.writeBoolean(gameInstance.isColideToBottomBlock());
            outputStream.writeBoolean(gameInstance.isColideToLeftBlock());
            outputStream.writeBoolean(gameInstance.isColideToTopBlock());

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