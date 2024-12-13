package com.example.demo.actors.obstacles;

/**
 * Represents an asteroid obstacle in the game. Asteroids move horizontally
 * across the screen and can be destroyed when their health reaches zero.
 * <p>
 *     Meant to be fragile, but much faster version of its counterpart, the {@link Satellite}.
 *     May hit the player if the player is not aware enough of their surroundings/careful when maneuvering.
 * </p>
 * This class extends the {@link Obstacle} class.
 */
public class Asteroid extends Obstacle {

    /** Path to the image resource representing the asteroid. */
    private static final String IMAGE_NAME = "Obstacles/asteroid.gif";

    /** Height of the asteroid image in pixels. */
    private static final int IMAGE_HEIGHT = 50;

    /** Horizontal velocity of the asteroid. */
    private static final int HORIZONTAL_VELOCITY = -20;

    /** Initial health of the asteroid. */
    private static final int INITIAL_HEALTH = 1;

    /**
     * Constructs an {@code Asteroid} instance with a specified initial position.
     *
     * @param initialXPos the initial X position of the asteroid on the screen.
     * @param initialYPos the initial Y position of the asteroid on the screen.
     */
    public Asteroid(double initialXPos, double initialYPos) {
        super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, INITIAL_HEALTH);
    }

    /**
     * Updates the position of the asteroid by moving it horizontally
     * based on its velocity.
     */
    @Override
    public void updatePosition() {
        moveHorizontally(HORIZONTAL_VELOCITY);
    }

    /**
     * Updates the state of the asteroid, including its position.
     */
    @Override
    public void updateActor() {
        updatePosition();
    }

}
