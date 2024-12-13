package com.example.demo.actors;

/**
 * The ActiveActorDestructible class is an abstract class that represents an actor
 * in the game which can be destroyed. It extends the {@link ActiveActor} class
 * and implements the {@link Destructible} interface, adding functionality for
 * handling destruction and damage. The class provides methods for updating the actor's
 * position and state, as well as for handling its destruction status.
 */
public abstract class ActiveActorDestructible extends ActiveActor implements Destructible {

	/**
	 * A flag indicating whether the actor has been destroyed.
	 */
	private boolean isDestroyed;

	/**
	 * Constructs an ActiveActorDestructible object with the specified image, height,
	 * and initial position. The actor's destruction status is initialized to false.
	 *
	 * @param imageName The name of the image file for the actor.
	 * @param imageHeight The height of the actor's image.
	 * @param initialXPos The initial x-coordinate position of the actor.
	 * @param initialYPos The initial y-coordinate position of the actor.
	 */
	public ActiveActorDestructible(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		super(imageName, imageHeight, initialXPos, initialYPos);
		isDestroyed = false;
	}

	/**
	 * Updates the position of the actor. This method must be implemented by subclasses
	 * to define how the actor's position is updated.
	 * This method is inherited from {@link ActiveActor}.
	 */
	@Override
	public abstract void updatePosition();

	/**
	 * Updates the state of the actor. This method must be implemented by subclasses
	 * to define how the actor's state is updated.
	 */
	public abstract void updateActor();

	/**
	 * Handles the actor taking damage. This method must be implemented by subclasses
	 * to define the behavior when the actor takes damage.
	 */
	@Override
	public abstract void takeDamage();

	/**
	 * Marks the actor as destroyed by calling the {@link #setDestroyed(boolean)} method.
	 * This method is called when the actor should be removed or deactivated in the game.
	 */
	@Override
	public void destroy() {
		setDestroyed(true);
	}

	/**
	 * Sets the destroyed status of the actor.
	 *
	 * @param isDestroyed A boolean value indicating whether the actor is destroyed.
	 */
	protected void setDestroyed(boolean isDestroyed) {
		this.isDestroyed = isDestroyed;
	}

	/**
	 * Returns the current destroyed status of the actor.
	 *
	 * @return true if the actor is destroyed, false otherwise.
	 */
	public boolean isDestroyed() {
		return isDestroyed;
	}
	
}
