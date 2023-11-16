package brickGame;

public enum GameConstants {

    SAVE_PATH("C:/save/save.mdds"),
    SAVE_PATH_DIR("C:/save/"),
    BREAK_WIDTH(130),
    BREAK_HEIGHT(30),
    LEFT(1),
    RIGHT(2),
    SCENE_WIDTH(500),
    SCENE_HEIGHT(700),
    BLOCK_CHOCO(1),
    BLOCK_HEART(2),
    BLOCK_STAR(3),
    BLOCK_NORMAL(4);

    private final Object value;

    GameConstants(Object value) {
        this.value = value;
    }

    public String getStringValue() {
        return (value instanceof String) ? (String) value : null;
    }

    public int getIntValue() {
        return (value instanceof Integer) ? (int) value : -1;
    }
}
