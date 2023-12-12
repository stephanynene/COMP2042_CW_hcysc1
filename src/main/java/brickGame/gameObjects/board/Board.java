package brickGame.gameObjects.board;

import brickGame.Main;
import brickGame.constants.GameConstants;
import brickGame.gameObjects.block.Block;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/**
 * The Board class manages the initialisation of game blocks. It creates a grid of blocks,
 * using factors like game level, randomisation, and block types with colors and their durability.
 */
public class Board {

    private Main mainInstance;
    /**
     * Constructs a Board with a reference to the main instance.
     *
     * @param mainInstance The main instance.
     */
    public Board(Main mainInstance) {
        this.mainInstance = mainInstance;
    }

    /**
     * Sets up the game board with different types, colors, and durability of blocks.
     * The board is created with rows and columns of blocks, considering the game level and randomness.
     *
     * places block types like Chocolate, Heart, Star, Thunder, Sturdy, and Normal on the board.
     * Sturdy blocks have extra durability for added
     * resistance. They need to be hit by the ball more than once to be removed from screen.
     *
     * Determines the type of block to be generated based on a random number.
     * Assigns block types with different characteristics
     *  If the random number modulo 10 equals 1, a Chocolate block is selected.
     *  If the random number modulo 10 equals 2 and a Heart block is not already placed,
     *  a Heart block is selected; otherwise, a Normal block is chosen.
     *  If the random number modulo 30 equals 3, a Star block is selected.
     *  If the random number modulo 10 equals 3, a Thunder block is selected.
     *  If the random number modulo 5 equals 3, a Sturdy block with durability 2 is chosen.
     *  Otherwise, normal block is selected
     *
     *  Assigns a color to a block based on a random number and adds the block to the game board by retrieving an array of  colors from the GameConstants enum clas.
     * calculates the index of the color to be assigned to the block based on the random number.
     *  The block is created with the determined color, type, and durability, and is added to the list of blocks on the game board.
     *
     *  Star block is set to spawn less often
     *
     **/
    public void initBoard() {
        Random random = new Random();

        synchronized (mainInstance.getBlocks()) {
            // Obtain the list of blocks from the main instance
            List<Block> mainBlocks = mainInstance.getBlocks();
            if (mainBlocks == null) {
                // If list is null, create a new ArrayList and set it in the main instance
                mainBlocks = new ArrayList<>();
                mainInstance.setBlocks((ArrayList<Block>) mainBlocks);
            }

            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < mainInstance.level + 1; j++) {
                    int r = random.nextInt(500);
                    // Generate a random number to determine block type and other properties
                    int durability = 0;

                    if (r % 5 == 0) {
                        continue; // Skip this iteration
                    }

                    int type;
                    if (r % 10 == 1) {
                        type = GameConstants.BLOCK_CHOCO.getIntValue();
                    } else if (r % 10 == 2) {
                        if (!mainInstance.isExistHeartBlock()) {  // If Heart block is not already placed, use Heart; otherwise, use Normal
                            type = GameConstants.BLOCK_HEART.getIntValue();
                            mainInstance.setExistHeartBlock(true);
                        } else {
                            type = GameConstants.BLOCK_NORMAL.getIntValue();
                        }
                    } else if (r % 30 == 3) {
                        type = GameConstants.BLOCK_STAR.getIntValue();
                    }else if (r % 10 == 3) {
                        type = GameConstants.BLOCK_THUNDER.getIntValue();
                    }else if (r % 5 == 3) {
                        type = GameConstants.BLOCK_STURDY.getIntValue();
                        durability = 2;
                    } else {
                        type = GameConstants.BLOCK_NORMAL.getIntValue();
                    }

                    Color[] colors = (Color[]) ((Object[]) GameConstants.COLORS.getValue());
                    mainBlocks.add(new Block(j, i, colors[r % colors.length], type, durability));
                }
            }
        }
    }
}
