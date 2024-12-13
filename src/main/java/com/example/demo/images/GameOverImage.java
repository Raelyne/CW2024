package com.example.demo.images;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

/**
 * The {@code GameOverImage} class represents an image displayed for a game-over screen.
 * <p>
 * This class extends {@code ImageView} to create and configure a predefined game-over image
 * with specific dimensions and positioning.
 * Original source code can be found in the original git repository: <a href="https://github.com/kooitt/CW2024.git">ORIGINAL SOURCE CODE</a>
 * </p>
 */
 public class GameOverImage extends ImageView {
	/**
	 * The file path to the game-over image resource.
	 */
	private static final String IMAGE_NAME = "/com/example/demo/images/LevelUI/gameover.png";
	/**
	 * The height of the game-over image.
	 */
	private static final double IMAGE_HEIGHT = 300;
	/**
	 * The width of the game-over image.
	 */
	private static final double IMAGE_WIDTH = 300;

	/**
	 * Constructs a new {@code GameOverImage} with a specific position.
	 * <p>
	 * The constructor initializes the game-over image with predefined dimensions
	 * and sets its layout position on the screen.
	 * </p>
	 *
	 * @param xPosition the X-coordinate for positioning the image
	 * @param yPosition the Y-coordinate for positioning the image
	 */
	public GameOverImage(double xPosition, double yPosition) {
		setImage(new Image(Objects.requireNonNull(getClass().getResource(IMAGE_NAME)).toExternalForm()) );
		setFitHeight(IMAGE_HEIGHT);
		setFitWidth(IMAGE_WIDTH);
		setLayoutX(xPosition);
		setLayoutY(yPosition);
	}

}
