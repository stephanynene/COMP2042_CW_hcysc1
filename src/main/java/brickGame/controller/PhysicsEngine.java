package brickGame.controller;

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
