package com.example.demo.levels;

import com.example.demo.images.*;
import javafx.scene.Group;

/**
 * Represents the visual aspects of a level, including the player's health display,
 * win and game-over images, and pause image. This class is responsible for updating and
 * displaying the necessary visual elements during gameplay.
 *
 * @see HeartDisplay
 * @see WinImage
 * @see GameOverImage
 * @see PauseImage
 */
public class LevelView {
	
	private static final double HEART_DISPLAY_X_POSITION = 5;
	private static final double HEART_DISPLAY_Y_POSITION = 25;
	private static final int WIN_IMAGE_X_POSITION = 500;
	private static final int WIN_IMAGE_Y_POSITION = 225;
	private static final int LOSS_SCREEN_X_POSITION = 500;
	private static final int LOSS_SCREEN_Y_POSITION = 225;
	private static final int PAUSE_IMAGE_X_POSITION = 355;
	private static final int PAUSE_IMAGE_Y_POSITION = 175;

	private final Group root;
	private final WinImage winImage;
	private final GameOverImage gameOverImage;
	private final HeartDisplay heartDisplay;
	private final PauseImage pauseImage;

	/**
	 * Constructs a LevelView instance that manages the visual elements for the level.
	 * This includes the player's health display, win and loss images, and pause image.
	 *
	 * @param root The root container (Group) to which visual elements are added.
	 * @param heartsToDisplay The initial number of hearts to display for the player.
	 */
	public LevelView(Group root, int heartsToDisplay) {
		this.root = root;
		this.heartDisplay = new HeartDisplay(HEART_DISPLAY_X_POSITION, HEART_DISPLAY_Y_POSITION, heartsToDisplay);
        this.winImage = new WinImage(WIN_IMAGE_X_POSITION, WIN_IMAGE_Y_POSITION);
		this.gameOverImage = new GameOverImage(LOSS_SCREEN_X_POSITION, LOSS_SCREEN_Y_POSITION);
		this.pauseImage = new PauseImage(PAUSE_IMAGE_X_POSITION,PAUSE_IMAGE_Y_POSITION);
	}

	/**
	 * Displays the heart display (representing the player's remaining health) on the screen.
	 */
	public void showHeartDisplay() {
		root.getChildren().add(heartDisplay.getContainer());
	}

	/**
	 * Displays the win image on the screen when the player wins the level.
	 */
	public void showWinImage() {
		root.getChildren().add(winImage);
		winImage.showWinImage();
	}

	/**
	 * Displays the game-over image on the screen when the player loses the level.
	 */
	public void showGameOverImage() {
		root.getChildren().add(gameOverImage);
	}

	/**
	 * Displays the pause image when the game is paused.
	 */
	public void showPauseImage() {
		root.getChildren().add(pauseImage);
		pauseImage.showPauseImage();
	}

	/**
	 * Hides the pause image from the screen when the game is unpaused.
	 */
	public void hidePauseImage() {
		root.getChildren().remove(pauseImage);
	}

	/**
	 * Removes hearts from the heart display, representing a decrease in the player's health.
	 *
	 * @param heartsRemaining The number of hearts that should remain on the screen.
	 */
	public void removeHearts(int heartsRemaining) {
		int currentNumberOfHearts = heartDisplay.getContainer().getChildren().size();
		for (int i = 0; i < currentNumberOfHearts - heartsRemaining; i++) {
			heartDisplay.removeHeart();
		}
	}

}
