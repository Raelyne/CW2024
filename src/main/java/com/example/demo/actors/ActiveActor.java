package com.example.demo.actors;

import javafx.scene.image.*;

import java.util.Objects;

/**
 * The ActiveActor class is an abstract class that represents an actor in the game
 * that can move and has an associated image. It extends the {@link ImageView} class
 * from JavaFX to display images and provides methods for updating the actor's
 * position on the screen.
 */
public abstract class ActiveActor extends ImageView {

	/**
	 * The base location for image files.
	 */
	private static final String IMAGE_LOCATION = "/com/example/demo/images/";

	/**
	 * Constructs an ActiveActor object with the specified image, height, and initial position.
	 * The actor's image is loaded from the resources folder, and its layout position and
	 * image height are set according to the provided parameters.
	 *
	 * @param imageName The name of the image file for the actor.
	 * @param imageHeight The height of the actor's image.
	 * @param initialXPos The initial x-coordinate position of the actor.
	 * @param initialYPos The initial y-coordinate position of the actor.
	 */
	public ActiveActor(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		//this.setImage(new Image(IMAGE_LOCATION + imageName));
		this.setImage(new Image(Objects.requireNonNull(getClass().getResource(IMAGE_LOCATION + imageName)).toExternalForm()));
		this.setLayoutX(initialXPos);
		this.setLayoutY(initialYPos);
		this.setFitHeight(imageHeight);
		this.setPreserveRatio(true);
	}

	/**
	 * Updates the position of the actor. This method must be implemented by subclasses
	 * to define the specific behavior for updating the actor's position.
	 */
	public abstract void updatePosition();

	/**
	 * Moves the actor horizontally by the specified amount.
	 * This method adjusts the actor's translation on the x-axis.
	 *
	 * @param horizontalMove The amount to move the actor horizontally.
	 */
	protected void moveHorizontally(double horizontalMove) {
		this.setTranslateX(getTranslateX() + horizontalMove);
	}

	/**
	 * Moves the actor vertically by the specified amount.
	 * This method adjusts the actor's translation on the y-axis.
	 *
	 * @param verticalMove The amount to move the actor vertically.
	 */
	protected void moveVertically(double verticalMove) {
		this.setTranslateY(getTranslateY() + verticalMove);
	}

}
