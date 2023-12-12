package brickGame.saving;

import brickGame.constants.BlockSerializable;
import brickGame.constants.GameConstants;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

/**
 * Reads the current state of game
 * Original Source code:
 * <a href="https://github.com/kooitt/CourseworkGame/blob/master/src/main/java/brickGame/LoadSave.java">
 * LoadSave.java</a>.
 */
public class GameStateReader {
    public boolean          isExistHeartBlock;
    public boolean          isGoldStauts;
    public boolean          goDownBall;
    public boolean          goRightBall;
    public boolean          colideToBreak;
    public boolean          colideToBreakAndMoveToRight;
    public boolean          colideToRightWall;
    public boolean          colideToLeftWall;
    public boolean          colideToRightBlock;
    public boolean          colideToBottomBlock;
    public boolean          colideToLeftBlock;
    public boolean          colideToTopBlock;
    public int              level;
    public int              score;
    public int              heart;
    public int              destroyedBlockCount;
    public double            xBall;
    public double           yBall;
    public double           xBreak;
    public double           yBreak;
    public double           centerBreakX;
    public long             time;
    public long             goldTime;
    public double           vX;
    public long elapsedTime;
    public long remainingSeconds;
    public ArrayList<BlockSerializable> blocks = new ArrayList<BlockSerializable>();


    /**
     * Reads the saved game state from a file, restoring the game's variables and objects to their previous state.
     * This method uses ObjectInputStream for deserialization.
     * @throws IOException If an I/O error occurs while reading from the file.
     */
    public void read() {


        try {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(new File(GameConstants.SAVE_PATH.getStringValue())));


            level = inputStream.readInt();
            score = inputStream.readInt();
            heart = inputStream.readInt();
            destroyedBlockCount = inputStream.readInt();

            elapsedTime = inputStream.readLong();
            remainingSeconds = inputStream.readLong();


            xBall = inputStream.readDouble();
            yBall = inputStream.readDouble();
            xBreak = inputStream.readDouble();
            yBreak = inputStream.readDouble();
            centerBreakX = inputStream.readDouble();
            time = inputStream.readLong();
            goldTime = inputStream.readLong();
            vX = inputStream.readDouble();


            isExistHeartBlock = inputStream.readBoolean();
            isGoldStauts = inputStream.readBoolean();
            goDownBall = inputStream.readBoolean();
            goRightBall = inputStream.readBoolean();
            colideToBreak = inputStream.readBoolean();
            colideToBreakAndMoveToRight = inputStream.readBoolean();
            colideToRightWall = inputStream.readBoolean();
            colideToLeftWall = inputStream.readBoolean();
            colideToRightBlock = inputStream.readBoolean();
            colideToBottomBlock = inputStream.readBoolean();
            colideToLeftBlock = inputStream.readBoolean();
            colideToTopBlock = inputStream.readBoolean();


            try {
                blocks = (ArrayList<BlockSerializable>) inputStream.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
