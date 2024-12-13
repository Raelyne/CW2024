package com.example.demo.images;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

/**
 * The {@code PauseImage} class is a newly added image class that represents an image displayed for a pause screen.
 * <p>
 * This class extends {@code ImageView} to create and configure a predefined pause image
 * with specific dimensions and positioning.
 * </p>
 */
 public class PauseImage extends ImageView {
    /**
     * The file path to the pause image resource.
     */
    private static final String IMAGE_NAME = "/com/example/demo/images/LevelUI/pause.png";
    /**
     * The height of the pause image.
     */
    private static final int HEIGHT = 200;
    /**
     * The width of the pause image.
     */
    private static final int WIDTH = 600;

    /**
     * Constructs a new {@code PauseImage} with a specific position.
     * <p>
     * The constructor initializes the pause image with predefined dimensions
     * and sets its layout position on the screen.
     * </p>
     *
     * @param xPosition the X-coordinate for positioning the image
     * @param yPosition the Y-coordinate for positioning the image
     */
    public PauseImage(double xPosition, double yPosition) {
        this.setImage(new Image(Objects.requireNonNull(getClass().getResource(IMAGE_NAME)).toExternalForm()));
        this.setVisible(false);
        this.setFitHeight(HEIGHT);
        this.setFitWidth(WIDTH);
        this.setLayoutX(xPosition);
        this.setLayoutY(yPosition);
    }

    /**
     * Makes the pause image visible in the scene by setting current (@code ImageView) to true.
     */
    public void showPauseImage() {
        this.setVisible(true);
    }

}