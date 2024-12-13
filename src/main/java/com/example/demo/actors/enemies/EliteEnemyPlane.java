package com.example.demo.actors.enemies;

import com.example.demo.actors.ActiveActorDestructible;
import com.example.demo.actors.FighterPlane;
import com.example.demo.actors.projectile.EliteEnemyProjectile;

/**
 * This new class represents an elite enemy plane in the game, which is a stronger variant of {@link FighterPlane}.
 * The elite enemy plane moves horizontally at a slower velocity and has higher health than a regular enemy plane.
 * It also fires more powerful projectiles.
 */
public class EliteEnemyPlane extends FighterPlane {

    /** Path to the elite enemy plane's image resource. */
    private static final String IMAGE_NAME = "Enemy/eliteEnemyPlane.png";

    /** Height of the elite enemy plane image in pixels. */
    private static final int IMAGE_HEIGHT = 100;

    /** Horizontal velocity of the elite enemy plane. */
    private static final int HORIZONTAL_VELOCITY = -5;

    /** Offset for the X position of projectiles fired by the elite enemy plane. */
    private static final double PROJECTILE_X_POSITION_OFFSET = -100.0;

    /** Offset for the Y position of projectiles fired by the elite enemy plane. */
    private static final double PROJECTILE_Y_POSITION_OFFSET = 50.0;

    /** Initial health of the elite enemy plane. */
    private static final int INITIAL_HEALTH = 7;

    /** Probability that the elite enemy plane will fire a projectile in a given frame. */
    private static final double FIRE_RATE = .025;

    /**
     * Constructs an {@code EliteEnemyPlane} instance with the specified initial position.
     *
     * @param initialXPos the initial X position of the elite enemy plane.
     * @param initialYPos the initial Y position of the elite enemy plane.
     */
    public EliteEnemyPlane(double initialXPos, double initialYPos) {
        super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, INITIAL_HEALTH);
    }

    /**
     * Updates the position of the elite enemy plane, moving it horizontally.
     */
    @Override
    public void updatePosition() {
        moveHorizontally(HORIZONTAL_VELOCITY);
    }

    /**
     * Fires a projectile from the elite enemy plane if the firing probability is met.
     *
     * @return an {@link EliteEnemyProjectile} instance if fired, or {@code null} otherwise.
     */
    @Override
    public ActiveActorDestructible fireProjectile() {
        if (Math.random() < FIRE_RATE) {
            double projectileXPosition = getProjectileXPosition(PROJECTILE_X_POSITION_OFFSET);
            double projectileYPosition = getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET);
            return new EliteEnemyProjectile(projectileXPosition, projectileYPosition);
        }
        return null;
    }

    /**
     * Updates the state of the elite enemy plane, including its position.
     */
    @Override
    public void updateActor() {
        updatePosition();
    }

}
