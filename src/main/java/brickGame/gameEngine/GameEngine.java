package brickGame.gameEngine;


import brickGame.UpdateElements;

public class GameEngine {

    private OnAction onAction;
    private int fps = 15;
    private Thread updateThread;
    private Thread physicsThread;
    public boolean isStopped = true;

    private PhysicsUpdater physicsUpdater;
    private UpdateElements updateElements;

    private volatile boolean stopRequested = false;


//    public void setOnAction(OnAction onAction) {
//        this.onAction = onAction;
//    }
//
//    public void setPhysicsUpdater(PhysicsUpdater physicsUpdater) {
//        this.physicsUpdater = physicsUpdater;
//    }

    public void setOnActionAndPhysicsUpdater(OnAction onAction, PhysicsUpdater physicsUpdater, UpdateElements updateElements) {
        this.onAction = onAction;
        this.physicsUpdater = physicsUpdater;
        this.updateElements = updateElements;
    }

    /**
     * @param fps set fps and we convert it to millisecond
     */
    public void setFps(int fps) {
        this.fps = (int) 1000 / fps;
    }

//    private synchronized void Update() {
//        updateThread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (!updateThread.isInterrupted()) {
//                    try {
//                        updateElements.onUpdate();
//                        Thread.sleep(fps);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });
//        updateThread.start();
//    }
    private synchronized void Update() {
      updateThread = new Thread(() -> {
        while (!stopRequested) {
            try {
                updateElements.onUpdate();
                Thread.sleep(fps);
            } catch (InterruptedException e) {
                // Handle interruption, clean up, and exit the thread
                Thread.currentThread().interrupt();
                return;
            }
        }
    });
    updateThread.start();
}
    private void Initialize() {
        onAction.onInit();
    }

//    private synchronized void PhysicsCalculation() {
//        physicsThread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (!physicsThread.isInterrupted()) {
//                    try {
//                        physicsUpdater.onPhysicsUpdate();
//                        Thread.sleep(fps);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });
//
//        physicsThread.start();
//    }

    private void PhysicsCalculation() {
        physicsThread = new Thread(() -> {
            while (!stopRequested) {
                try {
                    physicsUpdater.onPhysicsUpdate();
                    Thread.sleep(fps);
                } catch (InterruptedException e) {
                    // Handle interruption, clean up, and exit the thread
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        });
        physicsThread.start();
    }

    public void start() {
        time = 0;
        Initialize();
        Update();
        PhysicsCalculation();
        TimeStart();
        isStopped = false;
    }

//    public void stop() {
//        if (!isStopped) {
//            isStopped = true;
//            updateThread.stop();
//            physicsThread.stop();
//            timeThread.stop();
//        }
//    }

    public void stop() {
        if (!stopRequested) {
            stopRequested = true;
            // Optionally, you can interrupt the threads here if needed.
        }
    }

    private long time = 0;

    private Thread timeThread;

    private void TimeStart() {
        timeThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        time++;
                        onAction.onTime(time);
                        Thread.sleep(1);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        timeThread.start();
    }


    public interface OnAction {
        void onUpdate();

        void onInit();

        void onPhysicsUpdate();

        void onTime(long time);
    }

}
