package com.example.demo.actors.projectile;

/**
 * The EliteEnemyProjectile class represents a stronger variant of the enemy's projectile, meant for elite enemies.
 * It extends the {@link Projectile} class and inherits its properties, with enhanced behavior such
 * as a different image, size, and velocity compared to the standard {@link EnemyProjectile}.
 */
public class EliteEnemyProjectile extends Projectile {

	/**
	 * The image file name representing the elite enemy projectile.
	 */
	private static final String IMAGE_NAME = "Projectiles/enemyFirealt.gif";

	/**
	 * The height of the elite enemy projectile image.
	 */
	private static final int IMAGE_HEIGHT = 50;

	/**
	 * The horizontal velocity of the elite enemy projectile.
	 * Negative value indicates movement to the left, and the velocity is slower than the standard enemy projectile.
	 */
	private static final int HORIZONTAL_VELOCITY = -15;

	/**
	 * Constructs an EliteEnemyProjectile object with the specified initial x and y positions.
	 * The image file, height, and velocity of the projectile are defined by the constants.
	 *
	 * @param initialXPos The initial x-position of the projectile.
	 * @param initialYPos The initial y-position of the projectile.
	 */
	public EliteEnemyProjectile(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos);
	}

	/**
	 * Updates the horizontal position of the elite enemy projectile by moving it at a constant velocity.
	 * The horizontal velocity is determined by the HORIZONTAL_VELOCITY constant.
	 */
	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
	}

	/**
	 * Updates the actor's state, which involves updating its position.
	 */
	@Override
	public void updateActor() {
		updatePosition();
	}


}
