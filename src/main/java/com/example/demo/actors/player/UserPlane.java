package com.example.demo.actors.player;

import com.example.demo.actors.ActiveActorDestructible;
import com.example.demo.actors.FighterPlane;
import com.example.demo.controller.SoundManager;
import com.example.demo.actors.projectile.UserProjectile;

/**
 * Represents the user's controllable fighter plane in the game. The {@code UserPlane}
 * allows movement, firing projectiles, and includes invulnerability mechanics
 * (iFrames) upon taking damage. This class extends {@link FighterPlane}.
 * Original source code can be found in the original git repository: <a href="https://github.com/kooitt/CW2024.git">ORIGINAL SOURCE CODE</a>
 */
public class UserPlane extends FighterPlane {

	/** Path to the image resource representing the user's plane. */
	private static final String IMAGE_NAME = "Player/userplaneALT.gif";

	/** Boundaries for the Y-axis movement of the plane. */
	private static final double Y_UPPER_BOUND = 0;
	private static final double Y_LOWER_BOUND = 650.0;

	/** Boundaries for the X-axis movement of the plane. */
	private static final double X_UPPER_BOUND = 5.0;
	private static final double X_LOWER_BOUND = 1200.0;

	/** Initial position of the user's plane on the X-axis. */
	private static final double INITIAL_X_POSITION = 5.0;

	/** Initial position of the user's plane on the Y-axis. */
	private static final double INITIAL_Y_POSITION = 300.0;

	/** Height of the user's plane image in pixels. */
	private static final int IMAGE_HEIGHT = 70;

	/** Velocity for vertical movement of the plane. */
	private static final int VERTICAL_VELOCITY = 8;

	/** Velocity for horizontal movement of the plane. */
	private static final int HORIZONTAL_VELOCITY = 8;

	/** Offsets for positioning projectiles relative to the plane. */
	private static final int PROJECTILE_X_POSITION_OFFSET = 100;
	private static final int PROJECTILE_Y_POSITION_OFFSET = 30;

	/** Duration of invulnerability after taking damage, in seconds. */
	private static final double INVINCIBILITY_DURATION = 1.0;

	/** Sound effect file path for when the player takes damage. */
	private static final String PLAYER_HIT_SFX = "/com/example/demo/sfx/level_sfx/damageTaken.mp3";

	private double HorizontalvelocityMultiplier;
	private double VerticalvelocityMultiplier;
	private int numberOfKills;
	private boolean isIFramed;
	private double iframeTimer;
	private SoundManager soundManager;

	/**
	 * Constructs a {@code UserPlane} instance with the specified initial health.
	 *
	 * @param initialHealth the initial health of the user's plane.
	 */
	public UserPlane(int initialHealth) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, initialHealth);
		HorizontalvelocityMultiplier = 0;
		VerticalvelocityMultiplier = 0;
		isIFramed = false; // Initially not invulnerable
		iframeTimer = 0.0; // No invincibility when the game starts
		//sounds
		soundManager = SoundManager.getInstance(); // Initialize SoundManager instance
		soundManager.loadSFX("damage_taken", PLAYER_HIT_SFX);
	}

	/**
	 * Stops the horizontal movement of the user's plane.
	 */
	public void stopX() {
		HorizontalvelocityMultiplier = 0;
	}

	/**
	 * Stops the vertical movement of the user's plane.
	 */
	public void stopY() {
		VerticalvelocityMultiplier = 0;
	}

	/**
	 * Handles damage taken by the plane. Triggers invulnerability frames (iFrames)
	 * and plays a sound effect.
	 */
	@Override
	public void takeDamage() {
		// Only take damage if the player is not invulnerable
		if (!isIFramed) {
			super.takeDamage();
			soundManager.playSFX("damage_taken");
			activateIFrames();
		}
	}

	/**
	 * Activates invulnerability frames (iFrames) to make the plane temporarily invincible.
	 */
	private void activateIFrames() {
		System.out.println("The plane is now invulnerable!");
		isIFramed = true;
		iframeTimer = INVINCIBILITY_DURATION; // Start the timer for invulnerability
	}

	/**
	 * Checks if the plane is currently invulnerable.
	 *
	 * @return {@code true} if the plane is invulnerable; {@code false} otherwise.
	 */
	public boolean isinVulnerable() {
		return isIFramed;
	}

	/**
	 * Updates the position of the plane, ensuring it remains within the specified boundaries.
	 */
	@Override
	public void updatePosition() {
		if (isMovingX()) {
			double initialTranslateX = getTranslateX();
			this.moveHorizontally(HORIZONTAL_VELOCITY * HorizontalvelocityMultiplier);
			double newPositionX = getLayoutX() + getTranslateX();
			if (newPositionX < X_UPPER_BOUND || newPositionX > X_LOWER_BOUND) {
				this.setTranslateX(initialTranslateX);
			}
		} //Checks if the user is moving the plane HORIZONTALLY

		if (isMovingY()) {
			double initialTranslateY = getTranslateY();
			this.moveVertically(VERTICAL_VELOCITY * VerticalvelocityMultiplier);
			double newPosition = getLayoutY() + getTranslateY();
			if (newPosition < Y_UPPER_BOUND || newPosition > Y_LOWER_BOUND) {
				this.setTranslateY(initialTranslateY);
			}
		} //Checks if the user is moving the plane VERTICALLY
	}

	/**
	 * Updates the invulnerability frame timer and resets invincibility when the timer expires.
	 */
	private void updateIFrames() {
		if (isIFramed) {
			iframeTimer -= 1 / 30.0; // Decrement the timer by 1/30th of a second each frame
			this.setOpacity(0.5);
			if (iframeTimer <= 0) {
				isIFramed = false; // End invulnerability
				System.out.println("Player no longer invincible!");
				this.setOpacity(1.0);
			}
		}
	}

	/**
	 * Updates the state of the user's plane, including position and iFrames.
	 */
	@Override
	public void updateActor() {
		updatePosition();
		updateIFrames();
	}

	/**
	 * Fires a projectile from the user's plane, positioned relative to the plane's current position.
	 *
	 * @return a {@link UserProjectile} representing the projectile fired by the plane.
	 */
	@Override
	public ActiveActorDestructible fireProjectile() {
		double projectileXPosition = getProjectileXPosition(PROJECTILE_X_POSITION_OFFSET);
		double projectileYPosition = getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET);
		return new UserProjectile(projectileXPosition, projectileYPosition); //Gives the program BOTH X/Y coordinates of user plane
	}

	/**
	 * Checks if the plane is moving in the X axis
	 */
	private boolean isMovingX() {
		return HorizontalvelocityMultiplier != 0;
	}

	/**
	 * Checks if the plane is moving in the Y axis
	 */
	private boolean isMovingY() {
		return VerticalvelocityMultiplier != 0;
	}

	/**
	 * Moves the plane upward by applying a vertical velocity multiplier.
	 */
	public void moveUp() {
		VerticalvelocityMultiplier = -1.5;
	}

	/**
	 * Moves the plane downward by applying a vertical velocity multiplier.
	 */
	public void moveDown() {
		VerticalvelocityMultiplier = 1.5;
	}

	/**
	 * Moves the plane left by applying a horizontal velocity multiplier.
	 */
	public void moveLeft() {
		HorizontalvelocityMultiplier = -1.5;
	}

	/**
	 * Moves the plane right by applying a horizontal velocity multiplier.
	 */
	public void moveRight() {
		HorizontalvelocityMultiplier = 1.5;
	}

	/**
	 * Returns the current number of kills achieved by the player's plane.
	 *
	 * @return the number of kills.
	 */
	public int getNumberOfKills() {
		return numberOfKills;
	}

	/**
	 * Increments the kill count of the player's plane.
	 */
	public void incrementKillCount() {
		numberOfKills++;
	}

	/**
	 * Decrements the kill count of the player's plane.
	 */
	public void decrementKillCount() {
		numberOfKills--;
	}
}
