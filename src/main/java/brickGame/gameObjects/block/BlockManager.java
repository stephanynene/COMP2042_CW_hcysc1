package brickGame.gameObjects.block;

import java.util.ArrayList;

import javafx.scene.layout.Pane;
/**
 * The BlockManager class manages blocks within the game.
 * It maintains a collection of blocks and their corresponding visual representations.
 */
public class BlockManager {
    private ArrayList<Block> blocks = new ArrayList<>();
    private ArrayList<BlockView> blockViews = new ArrayList<>();
    private Pane root;
    /**
     * Constructs a BlockManager instance, specifying the root pane where blocks will be displayed.
     *
     * @param root The root pane where blocks will be visually presented.
     */
    public BlockManager(Pane root) {
        this.root = root;
    }
    /**
     * Adds a block to the BlockManager and adds the visual representation to the root pane.
     *
     * @param block The block to be added.
     */
    public void addBlock(Block block) {
        blocks.add(block);
        BlockView blockView = block.getBlockView();
        blockViews.add(blockView);
        root.getChildren().add(blockView.getRect());
    }
    /**
     * Iterates over the blocks, so that their visual representations are displayed on the root pane.
     */
    public void drawBlocks() {
        // Iterate over blocks and add to the manager
        for (Block block : blocks) {
            addBlock(block);
        }
    }
}
