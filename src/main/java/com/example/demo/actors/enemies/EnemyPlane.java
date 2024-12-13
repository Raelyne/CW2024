package com.example.demo.actors.enemies;

import com.example.demo.actors.ActiveActorDestructible;
import com.example.demo.actors.FighterPlane;
import com.example.demo.actors.projectile.EnemyProjectile;

/**
 * Represents an enemy plane in the game, which is a type of {@link FighterPlane}.
 * The enemy plane moves horizontally at a constant velocity and has the ability to fire projectiles.
 * Original source code can be found in the original git repository: <a href="https://github.com/kooitt/CW2024.git">ORIGINAL SOURCE CODE</a>
 */
public class EnemyPlane extends FighterPlane {

	/** Path to the enemy plane's image resource. */
	private static final String IMAGE_NAME = "Enemy/enemyPlanealt.gif";

	/** Height of the enemy plane image in pixels. */
	private static final int IMAGE_HEIGHT = 100;

	/** Horizontal velocity of the enemy plane. */
	private static final int HORIZONTAL_VELOCITY = -7;

	/** Offset for the X position of projectiles fired by the enemy plane. */
	private static final double PROJECTILE_X_POSITION_OFFSET = -25.0;

	/** Offset for the Y position of projectiles fired by the enemy plane. */
	private static final double PROJECTILE_Y_POSITION_OFFSET = 25.0;

	/** Initial health of the enemy plane. */
	private static final int INITIAL_HEALTH = 3;

	/** Probability that the enemy plane will fire a projectile in a given frame. */
	private static final double FIRE_RATE = .015;

	/**
	 * Constructs an {@code EnemyPlane} instance with the specified initial position.
	 *
	 * @param initialXPos the initial X position of the enemy plane.
	 * @param initialYPos the initial Y position of the enemy plane.
	 */
	public EnemyPlane(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, INITIAL_HEALTH);
	}

	/**
	 * Updates the position of the enemy plane, moving it horizontally.
	 */
	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
	}

	/**
	 * Fires a projectile from the enemy plane if the firing probability is met.
	 *
	 * @return an {@link EnemyProjectile} instance if fired, or {@code null} otherwise.
	 */
	@Override
	public ActiveActorDestructible fireProjectile() {
		if (Math.random() < FIRE_RATE) {
			double projectileXPosition = getProjectileXPosition(PROJECTILE_X_POSITION_OFFSET);
			double projectileYPosition = getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET);
			return new EnemyProjectile(projectileXPosition, projectileYPosition);
		}
		return null;
	}

	/**
	 * Updates the state of the enemy plane, including its position.
	 */
	@Override
	public void updateActor() {
		updatePosition();
	}

}
