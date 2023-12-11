package brickGame.gameEngine;

import brickGame.controller.ElementsUpdater;
import brickGame.controller.PhysicsUpdater;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class GameEngine {

    private OnAction onAction;
    private int fps = 15;
    private PhysicsUpdater physicsUpdater;
    private ElementsUpdater elementsUpdater;
    private Timeline updateTimeline;

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

    private void Initialize() {
        onAction.onInit();
    }

    private void Update() {
        updateTimeline = new Timeline(new KeyFrame(Duration.millis(fps), event -> {
            elementsUpdater.onUpdate();
            physicsUpdater.onPhysicsUpdate();
            onAction.onTime((long) updateTimeline.getCurrentTime().toMillis());
        }));
        updateTimeline.setCycleCount(Timeline.INDEFINITE);
        updateTimeline.play();
    }

    public void start() {
        Initialize();
        Update();
    }

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
