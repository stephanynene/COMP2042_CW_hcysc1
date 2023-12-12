package brickGame.constants;

import java.io.Serializable;
/**
 * serializable representation of a block in the game.
 * Implements the Serializable interface
 */
public class BlockSerializable implements Serializable {
    public final int row;
    public final int j;
    public final int type;

    /**
     * Constructs a BlockSerializable with the specified parameters.
     *
     * @param row  The row index of the block.
     * @param j    The column index of the block.
     * @param type The type of the block.
     */
    public BlockSerializable(int row , int j , int type) {
        this.row = row;
        this.j = j;
        this.type = type;
    }
}
