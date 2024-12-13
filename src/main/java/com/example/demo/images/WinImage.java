package com.example.demo.images;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

/**
 * The {@code WinImage} class represents an image displayed for a win screen.
 * <p>
 * This class extends {@code ImageView} to create and configure a predefined "you win" image
 * with specific dimensions and positioning.
 * Original source code can be found in the original git repository: <a href="https://github.com/kooitt/CW2024.git">ORIGINAL SOURCE CODE</a>
 * </p>
 */
public class WinImage extends ImageView {
	/**
	 * The file path to the win image resource.
	 */
	private static final String IMAGE_NAME = "/com/example/demo/images/LevelUI/youwin.png";
	/**
	 * The height of the win image.
	 */
	private static final int HEIGHT = 300;
	/**
	 * The width of the win image.
	 */
	private static final int WIDTH = 300;

	/**
	 * Constructs a new {@code WinImage} with a specific position.
	 * <p>
	 * The constructor initializes the "you win" image with predefined dimensions
	 * and sets its layout position on the screen.
	 * </p>
	 *
	 * @param xPosition the X-coordinate for positioning the image
	 * @param yPosition the Y-coordinate for positioning the image
	 */
	public WinImage(double xPosition, double yPosition) {
		this.setImage(new Image(Objects.requireNonNull(getClass().getResource(IMAGE_NAME)).toExternalForm()));
		this.setVisible(false);
		this.setFitHeight(HEIGHT);
		this.setFitWidth(WIDTH);
		this.setLayoutX(xPosition);
		this.setLayoutY(yPosition);
	}

	/**
	 * Makes the win image visible in the scene by setting current (@code ImageView) to true.
	 */
	public void showWinImage() {
		this.setVisible(true);
	}

}
