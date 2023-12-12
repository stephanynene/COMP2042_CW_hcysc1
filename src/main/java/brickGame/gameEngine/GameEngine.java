package brickGame.gameEngine;

import brickGame.controller.ElementsUpdater;
import brickGame.controller.PhysicsUpdater;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

/**
 * The GameEngine class manages the game loop and triggers various actions during the game.
 *Original Source code
 * <a href="https://github.com/kooitt/CourseworkGame/blob/master/src/main/java/brickGame/GameEngine.java">
 * GameEngine.java</a>.
 */

public class GameEngine {

    private OnAction onAction;
    private int fps = 15;
    private PhysicsUpdater physicsUpdater;
    private ElementsUpdater elementsUpdater;
    private Timeline updateTimeline;

    /**
     * Constructs a GameEngine
     *
     * @param onAction         The callback interface for game actions.
     * @param physicsUpdater   The physics updater responsible for updating game physics.
     * @param elementsUpdater  The elements updater responsible for updating game elements.
     */
    public GameEngine(OnAction onAction, PhysicsUpdater physicsUpdater, ElementsUpdater elementsUpdater) {
        this.onAction = onAction;
        this.physicsUpdater = physicsUpdater;
        this.elementsUpdater = elementsUpdater;
    }

    /**
     * @param fps set fps and convert it to milliseconds
     */
    public void setFps(int fps) {
        this.fps = (int) 1000 / fps;
    }

    /**
     * Initialises the game by triggering the "onInit" callback from the provided OnAction interface.
     */
    private void Initialize() {
        onAction.onInit();
    }

    /**
     * Updates game by creating a Timeline with a specified frames-per-second (fps) rate.
     * triggers the "onUpdate", "onPhysicsUpdate", and "onTime" methods from the provided OnAction interface.
     * updateTimeline doesnt stop
     */
    private void Update() {
        updateTimeline = new Timeline(new KeyFrame(Duration.millis(fps), event -> {
            elementsUpdater.onUpdate();
            physicsUpdater.onPhysicsUpdate();
            onAction.onTime((long) updateTimeline.getCurrentTime().toMillis());
        }));
        updateTimeline.setCycleCount(Timeline.INDEFINITE);
        updateTimeline.play();
    }

    /**
     *
     * It calls the "Initialize" and "Update" methods begin the game engine.
     */
    public void start() {
        Initialize();
        Update();
    }

    /**
     * stops the updateTimeline
     */
    public void stop() {
            updateTimeline.stop();
    }

    public interface OnAction {
        void onUpdate();

        void onInit();

        void onPhysicsUpdate();

        void onTime(long time);
    }
}
