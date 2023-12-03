package brickGame.gameObjects.block;

import java.util.ArrayList;

import javafx.scene.layout.Pane;

public class BlockManager {
    private ArrayList<Block> blocks = new ArrayList<>();
    private ArrayList<BlockView> blockViews = new ArrayList<>();
    private Pane root;

    public BlockManager(Pane root) {
        this.root = root;
    }

    public void addBlock(Block block) {
        blocks.add(block);
        BlockView blockView = block.getBlockView();
        blockViews.add(blockView);
        root.getChildren().add(blockView.getRect());
    }

    public void drawBlocks() {
        // Iterate over blocks and add them to the manager
        for (Block block : blocks) {
            addBlock(block);
        }
    }


}
