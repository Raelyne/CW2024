package com.example.demo.actors.obstacles;

import com.example.demo.actors.ActiveActorDestructible;

/**
 * Represents an obstacle in the game, which is a destructible object that
 * players must avoid or destroy. Obstacles can take damage and are destroyed
 * when their health reaches zero. This class serves as a superclass for specific
 * obstacle types, such as {@link Asteroid} & {@link Satellite}
 */
public abstract class Obstacle extends ActiveActorDestructible {

    /** The current health of the obstacle. */
    private int health;

    /**
     * Constructs an {@code Obstacle} instance with the specified properties.
     *
     * @param imageName   the file path of the image representing the obstacle.
     * @param imageHeight the height of the obstacle's image in pixels.
     * @param initialXPos the initial X position of the obstacle on the screen.
     * @param initialYPos the initial Y position of the obstacle on the screen.
     * @param health      the initial health of the obstacle.
     */
    public Obstacle(String imageName, int imageHeight, double initialXPos, double initialYPos, int health) {
        super(imageName, imageHeight, initialXPos, initialYPos);
        this.health = health;
    }

    /**
     * Reduces the obstacle's health by 1. If the health reaches zero,
     * the obstacle is destroyed.
     */
    @Override
    public void takeDamage() {
        health--;
        if (healthAtZero()) {
            this.destroy();
        }
    }

    /**
     * Checks whether the obstacle's health has reached zero.
     *
     * @return {@code true} if the obstacle's health is zero, {@code false} otherwise.
     */
    private boolean healthAtZero() {
        return health == 0;
    }

    /**
     * Retrieves the current health of the obstacle.
     *
     * @return the current health value.
     */
    public int getHealth() {
        return health;
    }
}