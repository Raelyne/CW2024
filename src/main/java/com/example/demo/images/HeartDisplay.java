package com.example.demo.images;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.util.Objects;

/**
 * The {@code HeartDisplay} class represents a visual display of hearts in a horizontal layout,
 * commonly used to indicate the remaining life or health in a game.
 * <p>
 * This class manages the creation, display, and removal of heart icons in an {@code HBox} container.
 * Original source code can be found in the original git repository: <a href="https://github.com/kooitt/CW2024.git">ORIGINAL SOURCE CODE</a>
 * </p>
 */
public class HeartDisplay {
	/**
	 * The file path to the heart image resource.
	 */
	private static final String HEART_IMAGE_NAME = "/com/example/demo/images/LevelUI/heart.png";
	/**
	 * The height of the heart image.
	 */
	private static final int HEART_HEIGHT = 50;
	/**
	 * Container for displaying the hearts in a horizontal layout.
	 */
	private HBox container;
	/**
	 * The X position of the heart container on the screen.
	 */
	private final double containerXPosition;
	/**
	 * The Y position of the heart container on the screen.
	 */
	private final double containerYPosition;
	/**
	 * The number of hearts to display in the container.
	 */
	private final int numberOfHeartsToDisplay;

	/**
	 * Creates a new {@code HeartDisplay} with the specified position and number of hearts.
	 *
	 * @param xPosition       the X position of the heart container
	 * @param yPosition       the Y position of the heart container
	 * @param heartsToDisplay the number of hearts to display
	 */
	public HeartDisplay(double xPosition, double yPosition, int heartsToDisplay) {
		this.containerXPosition = xPosition;
		this.containerYPosition = yPosition;
		this.numberOfHeartsToDisplay = heartsToDisplay;
		initializeContainer();
		initializeHearts();
	}

	/**
	 * Initializes the {@code HBox} container for displaying the hearts.
	 */
	private void initializeContainer() {
		container = new HBox();
		container.setLayoutX(containerXPosition);
		container.setLayoutY(containerYPosition);		
	}

	/**
	 * Initializes the heart images in the container.
	 * <p>
	 * Each heart is represented as an {@code ImageView} with the specified height,
	 * preserving the original aspect ratio.
	 * </p>
	 */
	private void initializeHearts() {
		for (int i = 0; i < numberOfHeartsToDisplay; i++) {
			ImageView heart = new ImageView(new Image(Objects.requireNonNull(getClass().getResource(HEART_IMAGE_NAME)).toExternalForm()));

			heart.setFitHeight(HEART_HEIGHT);
			heart.setPreserveRatio(true);
			container.getChildren().add(heart);
		}
	}

	/**
	 * Removes one heart from the container.
	 * <p>
	 * This method removes the first heart in the container, if any hearts remain.
	 * </p>
	 */
	public void removeHeart() {
		if (!container.getChildren().isEmpty())
			container.getChildren().removeFirst();
	}

	/**
	 * Retrieves the {@code HBox} container holding the hearts.
	 *
	 * @return the {@code HBox} container
	 */
	public HBox getContainer() {
		return container;
	}

}
