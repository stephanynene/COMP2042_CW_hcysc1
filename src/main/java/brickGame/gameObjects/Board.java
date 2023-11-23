package brickGame.gameObjects;

import brickGame.GameConstants;
import brickGame.Main;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;

public class Board {

    private Main mainInstance;

    public Board(Main mainInstance) {
        this.mainInstance = mainInstance;
    }
    public void initBoard() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < mainInstance.level + 1; j++) {
                int r = new Random().nextInt(500);
                if (r % 5 == 0) {
                    continue;
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
                ArrayList<Block> mainBlocks = mainInstance.getBlocks();
                mainBlocks.add(new Block(j, i, colors[r % colors.length], type));
                mainInstance.setBlocks(mainBlocks);
//                mainInstance.blocks.add(new Block(j, i, colors[r % colors.length], type));


                //System.out.println("colors " + r % (colors.length));
            }
        }
    }
}
