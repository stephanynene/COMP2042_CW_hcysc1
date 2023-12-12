package brickGame.controller;

/**
 * The PhysicsEngine interface defines methods related to physics and collision handling in the game.
 */
public interface PhysicsEngine {

    void setPhysicsToBall();
    void ballCollision();
    void wallCollisons();
    void calculateVelocity();
    void boundaryCollisons();
    void ballPositioning();
    void breakCollisonDirection();
    void handleBreakCollision();
    void resetCollideFlags();
}
