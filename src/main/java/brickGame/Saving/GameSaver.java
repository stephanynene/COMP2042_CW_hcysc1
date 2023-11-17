package brickGame.Saving;

import brickGame.BlockSerializable;
import brickGame.GameConstants;
import brickGame.Main;
import brickGame.Score;
import brickGame.gameObjects.Ball;
import brickGame.gameObjects.Block;
import brickGame.gameObjects.BreakPaddle;

import java.io.*;
import java.util.ArrayList;
import brickGame.GameConstants;




public class GameSaver {


    public void saveGameState(Main gameInstance, BreakPaddle breakPaddle, Ball ball) {

        new Thread(new Runnable() {
            @Override
            public void run() {
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

                ObjectOutputStream outputStream = null;
                try {
                    outputStream = new ObjectOutputStream(new FileOutputStream(file));

                    outputStream.writeInt(gameInstance.getLevel());
                    outputStream.writeInt(gameInstance.getScore());
                    outputStream.writeInt(gameInstance.getHeart());
                    outputStream.writeInt(gameInstance.getDestroyedBlockCount());


                    outputStream.writeDouble(ball.getxBall());
                    outputStream.writeDouble(ball.getyBall());
                    outputStream.writeDouble(breakPaddle.getxBreak());
                    outputStream.writeDouble(breakPaddle.getyBreak());
                    outputStream.writeDouble(breakPaddle.getCenterBreakX());
                    outputStream.writeLong(gameInstance.getTime());
                    outputStream.writeLong(gameInstance.getGoldTime());
                    outputStream.writeDouble(gameInstance.getvX());


                    outputStream.writeBoolean(gameInstance.isExistHeartBlock());
                    outputStream.writeBoolean(gameInstance.isGoldStauts());
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

                    ArrayList<BlockSerializable> blockSerializables = new ArrayList<BlockSerializable>();
                    for (Block block : gameInstance.getBlocks()) {
                        if (block.isDestroyed) {
                            continue;
                        }
                        blockSerializables.add(new BlockSerializable(block.row, block.column, block.type));
                    }

                    outputStream.writeObject(blockSerializables);

                    new Score().showMessage("Game Saved", gameInstance);
                    System.out.println("Game Saved");

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        outputStream.flush();
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    // Method for saving/writing  all game states

    public void writeGameState(){

    }






}
