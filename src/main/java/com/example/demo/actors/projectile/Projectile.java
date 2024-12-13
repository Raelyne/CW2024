package com.example.demo.actors.projectile;

import com.example.demo.actors.ActiveActorDestructible;

/**
 * Represents a generic projectile in the game, which extends {@link ActiveActorDestructible}.
 * This class provides the basic structure and functionality for all projectile types,
 * such as handling damage and updating position.
 * <p>
 * Subclasses are expected to define specific behavior for projectile movement.
 * </p>
 */
public abstract class Projectile extends ActiveActorDestructible {

	/**
	 * Constructs a new {@code Projectile} instance with the specified properties.
	 *
	 * @param imageName     the file path to the image representing the projectile
	 * @param imageHeight   the height of the projectile image in pixels
	 * @param initialXPos   the initial X-coordinate of the projectile
	 * @param initialYPos   the initial Y-coordinate of the projectile
	 */
	public Projectile(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		super(imageName, imageHeight, initialXPos, initialYPos);
	}

	/**
	 * Handles the damage logic for the projectile.
	 * By default, taking damage results in the projectile being destroyed.
	 */
	@Override
	public void takeDamage() {
		this.destroy();
	}

	/**
	 * Updates the position of the projectile.
	 * Subclasses must implement this method to define their own movement logic.
	 */
	@Override
	public abstract void updatePosition();

}
