package brickGame.constants;

import javafx.scene.paint.Color;

public enum GameConstants {

    SAVE_PATH("C:/save/save.mdds"),
    SAVE_PATH_DIR("C:/save/"),
    GAME_OVER_MESSAGE("Game Over!"),
    TIMES_UP_MESSAGE("Times up!"),

    LEVEL_UP_MESSAGE("Level up!"),
    WIN_MESSAGE("You Won!"),
    RESTART_MESSAGE("Restart"),

    NORMAL_BALL("/ball.jpg"),
    GOLD_BALL("goldball.jpg"),
    SECOND_BALL("second-ball.png"),
    BREAKPADDLE_SPEED(5),
    CHOCO_BLOCK_IMG("choco.jpg"),
    HEART_BLOCK_IMG("heart.jpg"),
    STAR_BLOCK_IMG("star.jpg"),
    STURDY_BLOCK_IMG("sturdyBrick.jpg"),
    THUNDER_BLOCK_IMG("thunder-block.png"),
    BREAK_WIDTH(80),
    BREAK_HEIGHT(15),

    BALL_RADIUS(10),

    GOOD_BONUS_IMG("good-bonus.png"),
    BAD_BONUS_IMG("bad-bonus.png"),

    SCENE_WIDTH(500),
    SCENE_HEIGHT(700),
    BLOCK_NORMAL(99),
    BLOCK_CHOCO(100),
    BLOCK_HEART(101),
    BLOCK_STAR(102),
    BLOCK_STURDY(103),
    BLOCK_THUNDER(104),

    NO_HIT(-1),
    HIT_RIGHT(0),
    HIT_BOTTOM(1),
    HIT_LEFT(2),
    HIT_TOP(3),

    BLOCK_WIDTH(100),
    BLOCK_HEIGHT(30),
    BLOCK_X_CENTRE(BLOCK_WIDTH.getIntValue() / 2),
    BLOCK_Y_CENTRE(BLOCK_HEIGHT.getIntValue() / 2),
    PADDING_H(50),
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
