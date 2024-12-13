package com.example.demo.actors;

/**
 * The FighterPlane class represents a fighter plane in the game. It extends the {@link ActiveActorDestructible}
 * class and provides additional functionality related to the plane's health, firing projectiles, and taking damage.
 * The fighter plane can be destroyed when its health reaches zero.
 */
public abstract class FighterPlane extends ActiveActorDestructible {

	/**
	 * The current health of the fighter plane.
	 */
	private int health;

	/**
	 * Constructs a FighterPlane object with the specified image, height, position, and health.
	 *
	 * @param imageName The name of the image file for the fighter plane.
	 * @param imageHeight The height of the fighter plane's image.
	 * @param initialXPos The initial x-coordinate position of the fighter plane.
	 * @param initialYPos The initial y-coordinate position of the fighter plane.
	 * @param health The initial health of the fighter plane.
	 */
	public FighterPlane(String imageName, int imageHeight, double initialXPos, double initialYPos, int health) {
		super(imageName, imageHeight, initialXPos, initialYPos);
		this.health = health;
	}

	/**
	 * Fires a projectile from the fighter plane. The specific behavior of firing a projectile
	 * must be implemented by subclasses.
	 *
	 * @return The projectile fired by the fighter plane.
	 */
	public abstract ActiveActorDestructible fireProjectile();

	/**
	 * Reduces the fighter plane's health by one unit when it takes damage.
	 * If the health reaches zero, the fighter plane is destroyed.
	 */
	@Override
	public void takeDamage() {
		health--;
		if (healthAtZero()) {
			this.destroy();
		}
	}

	/**
	 * Calculates the x-coordinate position of a projectile relative to the fighter plane's
	 * position and an optional offset.
	 *
	 * @param xPositionOffset The offset to apply to the fighter plane's x-position when calculating the projectile's position.
	 * @return The calculated x-coordinate position for the projectile.
	 */
	protected double getProjectileXPosition(double xPositionOffset) {
		return getLayoutX() + getTranslateX() + xPositionOffset;
	}

	/**
	 * Calculates the y-coordinate position of a projectile relative to the fighter plane's
	 * position and an optional offset.
	 *
	 * @param yPositionOffset The offset to apply to the fighter plane's y-position when calculating the projectile's position.
	 * @return The calculated y-coordinate position for the projectile.
	 */
	protected double getProjectileYPosition(double yPositionOffset) {
		return getLayoutY() + getTranslateY() + yPositionOffset;
	}

	/**
	 * Checks if the fighter plane's health has reached zero.
	 *
	 * @return true if the health is zero, false otherwise.
	 */
	private boolean healthAtZero() {
		return health == 0;
	}

	/**
	 * Gets the current health of the fighter plane.
	 *
	 * @return The current health of the fighter plane.
	 */
	public int getHealth() {
		return health;
	}
}
