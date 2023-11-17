package brickGame;

import javafx.scene.paint.Color;

public enum GameConstants {

    SAVE_PATH("C:/save/save.mdds"),
    SAVE_PATH_DIR("C:/save/"),
    BREAK_WIDTH(130),
    BREAK_HEIGHT(30),

    LEFT(1),
    RIGHT(2),
    SCENE_WIDTH(500),
    SCENE_HEIGHT(700),
    BLOCK_NORMAL(99),
    BLOCK_CHOCO(100),
    BLOCK_HEART(101),
    BLOCK_STAR(102),


    COLORS(new Color[]{
        Color.MAGENTA, Color.RED, Color.GOLD, Color.CORAL,
                Color.AQUA, Color.VIOLET, Color.GREENYELLOW, Color.ORANGE,
                Color.PINK, Color.SLATEGREY, Color.YELLOW, Color.TOMATO, Color.TAN
    });

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
    public Color[] getValue() {
        return (Color[]) value;
    }
}
