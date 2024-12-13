package com.example.demo.actors.projectile;

/**
 * The UserProjectile class represents a projectile fired by the player character in the game.
 * It extends the {@link Projectile} class and defines properties and behaviors specific to the user's
 * projectile, such as its image, velocity, and initial position.
 * Original source code can be found in the original git repository: <a href="https://github.com/kooitt/CW2024.git">ORIGINAL SOURCE CODE</a>
 */
public class UserProjectile extends Projectile {

	/**
	 * The image file name representing the user projectile.
	 */
	private static final String IMAGE_NAME = "Projectiles/userProjectile.png";

	/**
	 * The height of the user projectile image.
	 */
	private static final int IMAGE_HEIGHT = 15;

	/**
	 * The horizontal velocity of the user projectile.
	 * Positive value indicates movement to the right.
	 */
	private static final int HORIZONTAL_VELOCITY = 15;

	/**
	 * Constructs a UserProjectile object with the specified initial x and y positions.
	 * The image file and height of the projectile are defined by the constants.
	 *
	 * @param initialXPos The initial x-position of the projectile.
	 * @param initialYPos The initial y-position of the projectile.
	 */
	public UserProjectile(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos);
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
