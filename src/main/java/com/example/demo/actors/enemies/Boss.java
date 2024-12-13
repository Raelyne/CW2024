package com.example.demo.actors.enemies;

import com.example.demo.actors.ActiveActorDestructible;
import com.example.demo.actors.FighterPlane;
import com.example.demo.actors.projectile.BossProjectile;
import com.example.demo.controller.SoundManager;
import com.example.demo.images.ShieldImage;

import java.util.*;

/**
 * Represents a Boss enemy in the game, which is a special type of {@link FighterPlane}.
 * The Boss has unique movement patterns, projectile firing mechanics, and a shield system
 * that temporarily protects it from damage.
 * Original source code can be found in the original git repository: <a href="https://github.com/kooitt/CW2024.git">ORIGINAL SOURCE CODE</a>
 */
public class Boss extends FighterPlane {
	/** Path to the Boss image resource. */
	private static final String IMAGE_NAME = "Enemy/bossMonster.gif";

	/** Initial X position of the Boss on the screen. */
	private static final double INITIAL_X_POSITION = 1000.0;

	/** Initial Y position of the Boss on the screen. */
	private static final double INITIAL_Y_POSITION = 400;

	/** Offset for the Y position of the projectiles fired by the Boss. */
	private static final double PROJECTILE_Y_POSITION_OFFSET = 75.0;

	/** Probability that the Boss will fire a projectile in a given frame. */
	private static final double BOSS_FIRE_RATE = 0.045;

	/** Probability that the Boss will activate its shield in a given frame. */
	private static final double BOSS_SHIELD_PROBABILITY = 0.03;

	/** Height of the Boss image in pixels. */
	private static final int IMAGE_HEIGHT = 350;

	/** Vertical velocity of the Boss during movement. */
	private static final int VERTICAL_VELOCITY = 8;

	/** Initial health of the Boss. */
	private static final int HEALTH = 100;

	/** Frequency of movement changes per cycle. */
	private static final int MOVE_FREQUENCY_PER_CYCLE = 5;

	/** Value representing no movement. */
	private static final int ZERO = 0;

	/** Maximum consecutive frames the Boss can move in the same direction. */
	private static final int MAX_FRAMES_WITH_SAME_MOVE = 7;

	/** Upper bound for the Boss's Y position on the screen. */
	private static final int Y_POSITION_UPPER_BOUND = -50;

	/** Lower bound for the Boss's Y position on the screen. */
	private static final int Y_POSITION_LOWER_BOUND = 475;

	/** Maximum frames the shield can remain active. */
	private static final int MAX_FRAMES_WITH_SHIELD = 70;

	/** Offset for the X position of the shield relative to the Boss. */
	private static final int SHIELD_X_POSITION_OFFSET = 60;

	/** Offset for the Y position of the shield relative to the Boss. */
	private static final int SHIELD_Y_POSITION_OFFSET = 45;

	/** Pattern for the Boss's vertical movement. */
	private final List<Integer> movePattern;

	/** Indicates whether the Boss's shield is currently active. */
	private boolean isShielded;

	/** Tracks consecutive moves in the same direction. */
	private int consecutiveMovesInSameDirection;

	/** Current index in the movement pattern. */
	private int indexOfCurrentMove;

	/** Tracks the number of frames the shield has been active. */
	private int framesWithShieldActivated;

	/** The shield image associated with the Boss. */
	private final ShieldImage shieldImage;

	private SoundManager soundManager;

	private static final String BOSS_LAUGH_SFX = "/com/example/demo/sfx/level_sfx/bossLaugh.mp3";

	/**
	 * Constructs a new {@code Boss} instance with default properties.
	 */
	public Boss() {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, HEALTH);
		movePattern = new ArrayList<>();
		consecutiveMovesInSameDirection = 0;
		indexOfCurrentMove = 0;
		framesWithShieldActivated = 0;
		isShielded = false;
		shieldImage = new ShieldImage(INITIAL_X_POSITION - SHIELD_X_POSITION_OFFSET, INITIAL_Y_POSITION - SHIELD_Y_POSITION_OFFSET);
		initializeMovePattern();

		soundManager = SoundManager.getInstance(); // Initialize SoundManager instance
		soundManager.loadSFX("boss_laughter", BOSS_LAUGH_SFX);

	}

	/**
	 * Updates the position of the Boss based on its movement pattern and adjusts the shield's position.
	 * Ensures the Boss stays within specified bounds.
	 */
	@Override
	public void updatePosition() {
		double initialTranslateY = getTranslateY();
		moveVertically(getNextMove());
		double currentPosition = getLayoutY() + getTranslateY();
		shieldImage.setLayoutY(currentPosition - SHIELD_Y_POSITION_OFFSET);
		if (currentPosition < Y_POSITION_UPPER_BOUND || currentPosition > Y_POSITION_LOWER_BOUND) {
			setTranslateY(initialTranslateY);
		}
	}

	/**
	 * Updates the Boss's state, including its position and shield activation.
	 */
	@Override
	public void updateActor() {
		updatePosition();
		updateShield();
	}

	/**
	 * Fires a projectile from the Boss if the firing probability is met.
	 *
	 * @return a {@link BossProjectile} instance if fired, or {@code null} otherwise.
	 */
	@Override
	public ActiveActorDestructible fireProjectile() {
		return bossFiresInCurrentFrame() ? new BossProjectile(getProjectileInitialPosition()) : null;
	}

	/**
	 * Inflicts damage on the Boss unless its shield is active.
	 */
	@Override
	public void takeDamage() {
		if (!isShielded) {
			super.takeDamage();
		}
	}


	/**
	 * Initializes the Boss's vertical movement pattern and shuffles it to ensure randomness.
	 */
	private void initializeMovePattern() {
		for (int i = 0; i < MOVE_FREQUENCY_PER_CYCLE; i++) {
			movePattern.add(VERTICAL_VELOCITY);
			movePattern.add(-VERTICAL_VELOCITY);
			movePattern.add(ZERO);
		}
		Collections.shuffle(movePattern);
	}

	/**
	 * Consistently updates the Boss's shield state, activating or deactivating it as necessary.
	 */
	private void updateShield() {
		if (isShielded) {
			framesWithShieldActivated++;
			shieldImage.showShield();
		}
		else if (shieldShouldBeActivated()) activateShield();
		if (shieldExhausted()) deactivateShield();
	}

	/**
	 * Retrieves the next move in the movement pattern, ensuring bounds and randomness are maintained.
	 *
	 * @return the next vertical movement value.
	 */
	private int getNextMove() {
		int currentMove = movePattern.get(indexOfCurrentMove);
		consecutiveMovesInSameDirection++;
		if (consecutiveMovesInSameDirection == MAX_FRAMES_WITH_SAME_MOVE) {
			Collections.shuffle(movePattern);
			consecutiveMovesInSameDirection = 0;
			indexOfCurrentMove++;
		}
		if (indexOfCurrentMove == movePattern.size()) {
			indexOfCurrentMove = 0;
		}
		return currentMove;
	}

	/**
	 * Determines whether the Boss should fire a projectile in the current frame.
	 *
	 * @return {@code true} if the Boss fires, {@code false} otherwise.
	 */
	private boolean bossFiresInCurrentFrame() {
		return Math.random() < BOSS_FIRE_RATE;
	}

	/**
	 * Calculates the initial Y position for a fired projectile.
	 *
	 * @return the Y position for the projectile.
	 */
	private double getProjectileInitialPosition() {
		return getLayoutY() + getTranslateY() + PROJECTILE_Y_POSITION_OFFSET;
	}

	/**
	 * Determines whether the shield should be activated based on a random probability.
	 *
	 * @return {@code true} if the shield should be activated, {@code false} otherwise.
	 */
	private boolean shieldShouldBeActivated() {
		return Math.random() < BOSS_SHIELD_PROBABILITY;
	}

	/**
	 * Checks whether the shield has been active for its maximum duration.
	 *
	 * @return {@code true} if the shield is exhausted, {@code false} otherwise.
	 */
	private boolean shieldExhausted() {
		return framesWithShieldActivated == MAX_FRAMES_WITH_SHIELD;
	}

	/**
	 * Activates the Boss's shield, making it immune to damage.
	 * Also incorporates a 30% chance to play the boss laughing
	 */
	private void activateShield() {
		isShielded = true;
		shieldImage.showShield();
		if (Math.random() < 0.3) {
			soundManager.playSFX("boss_laughter");
		}
	}

	/**
	 * Deactivates the Boss's shield, making it vulnerable to damage again.
	 */
	private void deactivateShield() {
		isShielded = false;
		framesWithShieldActivated = 0;
		shieldImage.hideShield();
	}

	/**
	 * Retrieves the shield image associated with the Boss.
	 *
	 * @return the {@link ShieldImage} instance.
	 */
	public ShieldImage getshieldImage() {
		return shieldImage;
	}
}
