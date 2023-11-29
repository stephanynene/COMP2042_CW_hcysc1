package brickGame.gameObjects;

import brickGame.Main;
import brickGame.constants.GameConstants;
import brickGame.gameObjects.Block;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {

    private Main mainInstance;

    public Board(Main mainInstance) {
        this.mainInstance = mainInstance;
    }

    public void initBoard() {
        Random random = new Random();

        synchronized (mainInstance.getBlocks()) {
            List<Block> mainBlocks = mainInstance.getBlocks();
            if (mainBlocks == null) {
                mainBlocks = new ArrayList<>();
                mainInstance.setBlocks((ArrayList<Block>) mainBlocks);
            }

            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < mainInstance.level + 1; j++) {
                    int r = random.nextInt(500);

                    if (r % 5 == 0) {
                        continue; // Skip this iteration
                    }

                    int type;
                    if (r % 10 == 1) {
                        type = GameConstants.BLOCK_CHOCO.getIntValue();
                    } else if (r % 10 == 2) {
                        if (!mainInstance.isExistHeartBlock()) {
                            type = GameConstants.BLOCK_HEART.getIntValue();
                            mainInstance.setExistHeartBlock(true);
                        } else {
                            type = GameConstants.BLOCK_NORMAL.getIntValue();
                        }
                    } else if (r % 10 == 3) {
                        type = GameConstants.BLOCK_STAR.getIntValue();
                    } else {
                        type = GameConstants.BLOCK_NORMAL.getIntValue();
                    }

                    Color[] colors = (Color[]) ((Object[]) GameConstants.COLORS.getValue());
                    mainBlocks.add(new Block(j, i, colors[r % colors.length], type));
                }
            }
        }
    }


}
