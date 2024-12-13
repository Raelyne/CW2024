package com.example.demo.levels;

import com.example.demo.images.ShieldImage;
import javafx.scene.Group;

/**
 * A subclass of {@link LevelView} that adds additional visual elements specific to Level Four,
 * such as a shield image. This class manages the display of the shield image alongside the
 * standard heart display, win/loss images, and pause image inherited from the parent class.
 * Originally LevelViewLevelTwo, but renamed as the new boss level was now level four.
 * Original source code can be found in the original git repository: <a href="https://github.com/kooitt/CW2024.git">ORIGINAL SOURCE CODE</a>
 *
 * @see LevelView
 * @see ShieldImage
 */
public class LevelViewLevelFour extends LevelView {

	// Constants for positioning the shield image
	private static final int SHIELD_X_POSITION = 1000;
	private static final int SHIELD_Y_POSITION = 400;

	// Member variables for visual components
	private final Group root;
	private final ShieldImage shieldImage;

	/**
	 * Constructs a LevelViewLevelFour instance that manages the visual elements for Level Four.
	 * This includes the shield image in addition to the heart display, win/loss images, and pause image
	 * provided by the parent class {@link LevelView}.
	 *
	 * @param root The root container (Group) to which visual elements are added.
	 * @param heartsToDisplay The initial number of hearts to display for the player.
	 */
	public LevelViewLevelFour(Group root, int heartsToDisplay) {
		super(root, heartsToDisplay);
		this.root = root;
		this.shieldImage = new ShieldImage(SHIELD_X_POSITION, SHIELD_Y_POSITION);
		addImagesToRoot();
	}

	/**
	 * Adds the shield image to the root container.
	 */
	private void addImagesToRoot() {
		root.getChildren().addAll(shieldImage);
		System.out.println("Shield added to root!");
	}

}