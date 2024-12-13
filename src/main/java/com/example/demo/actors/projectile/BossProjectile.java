package com.example.demo.actors.projectile;

/**
 * Represents a boss projectile in the game.
 * Original source code can be found in the original git repository: <a href="https://github.com/kooitt/CW2024.git">ORIGINAL SOURCE CODE</a>
 * <p>
 * This class extends the {@code Projectile} class and defines a specific type of projectile
 * fired by a boss character. The projectile moves horizontally at a constant velocity
 * and has predefined properties such as image, height, and initial position.
 * </p>
 */
public class BossProjectile extends Projectile {

	/**
	 * The image file name representing the boss projectile.
	 */
	private static final String IMAGE_NAME = "Projectiles/bossFirealt.gif";

	/**
	 * The height of the boss projectile image.
	 */
	private static final int IMAGE_HEIGHT = 75;

	/**
	 * The horizontal velocity of the boss projectile.
	 * Negative value indicates movement to the left.
	 */
	private static final int HORIZONTAL_VELOCITY = -18;

	/**
	 * The initial x-position of the boss projectile when it is created.
	 */
	private static final int INITIAL_X_POSITION = 950;

	/**
	 * Constructs a BossProjectile object with the specified initial y-position.
	 * The x-position is set to the default initial value, and the projectile's image
	 * and height are defined by the constants.
	 *
	 * @param initialYPos The initial y-position of the projectile.
	 */
	public BossProjectile(double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, initialYPos);
	}

	/**
	 * Updates the horizontal position of the projectile by moving it at a constant velocity.
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
