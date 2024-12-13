package com.example.demo.actors.obstacles;

/**
 * Represents a satellite obstacle in the game. Satellites move horizontally
 * across the screen and have higher durability compared to other obstacles.
 * <p>
 *    Meant to be the other variant of its counterpart obstacle, {@link Asteroid}. Much slower in movement but tougher
 *    which can block bullets from hitting enemies if left alone.
 * </p>
 * This class extends the {@link Obstacle} class.
 */
public class Satellite extends Obstacle {

    /** Path to the image resource representing the satellite. */
    private static final String IMAGE_NAME = "Obstacles/satellite.png";

    /** Height of the satellite image in pixels. */
    private static final int IMAGE_HEIGHT = 100;

    /** Horizontal velocity of the satellite. */
    private static final int HORIZONTAL_VELOCITY = -5;

    /** Initial health of the satellite. */
    private static final int INITIAL_HEALTH = 10;

    /**
     * Constructs a {@code Satellite} instance with a specified initial position.
     *
     * @param initialXPos the initial X position of the satellite on the screen.
     * @param initialYPos the initial Y position of the satellite on the screen.
     */
    public Satellite(double initialXPos, double initialYPos) {
        super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, INITIAL_HEALTH);
    }

    /**
     * Updates the position of the satellite by moving it horizontally
     * based on its velocity.
     */
    @Override
    public void updatePosition() {
        moveHorizontally(HORIZONTAL_VELOCITY);
    }

    /**
     * Updates the state of the satellite, including its position.
     */
    @Override
    public void updateActor() {
        updatePosition();
    }

}
