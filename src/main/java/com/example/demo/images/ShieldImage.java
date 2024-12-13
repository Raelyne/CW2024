package com.example.demo.images;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

/**
 * The {@code ShieldImage} class represents an image displayed for a shield.
 * <p>
 * This class extends {@code ImageView} to create and configure a predefined shield image
 * with specific dimensions and positioning.
 * Original source code can be found in the original git repository: <a href="https://github.com/kooitt/CW2024.git">ORIGINAL SOURCE CODE</a>
 * </p>
 */
public class ShieldImage extends ImageView {
	/**
	 * The file path to the shield image resource.
	 */
	private static final String IMAGE_NAME = "/com/example/demo/images/VFX/energyshield.gif";
	/**
	 * The initial width and height of the shield image.
	 */
	private static final int SHIELD_SIZE = 425;

	/**
	 * Constructs a new {@code ShieldImage} with a specific position.
	 * <p>
	 * The constructor initializes the shield image with predefined dimensions
	 * and sets its layout position on the screen. However this is later overwritten when initializing a new instance inside Boss
	 * </p>
	 *
	 * @param xPosition the X-coordinate for positioning the image
	 * @param yPosition the Y-coordinate for positioning the image
	 */
	public ShieldImage(double xPosition, double yPosition) {
		this.setLayoutX(xPosition);
		this.setLayoutY(yPosition);
		this.setImage(new Image(Objects.requireNonNull(getClass().getResource(IMAGE_NAME)).toExternalForm()));
		this.setVisible(false);
		this.setFitHeight(SHIELD_SIZE);
		this.setFitWidth(SHIELD_SIZE);
	}

	/**
	 * Makes the shield image visible in the scene by setting current (@code ImageView) to true.
	 */
	public void showShield() {
		this.setVisible(true);
	}

	/**
	 * Makes the shield image invisible in the scene by setting current (@code ImageView) to false.
	 */
	public void hideShield() {
		this.setVisible(false);
	}

}