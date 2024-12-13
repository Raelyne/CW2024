package com.example.demo.levels;

import com.example.demo.actors.ActiveActorDestructible;
import com.example.demo.actors.enemies.*;
import com.example.demo.actors.obstacles.*;
import com.example.demo.levelparent.LevelParent;
import com.example.demo.controller.SoundManager;
import javafx.stage.Stage;

/**
 * Represents the third level in the game. In this level, the player faces both regular and elite enemy planes,
 * while also dealing with environmental obstacles such as satellites and asteroids. The player must defeat a certain
 * number of enemies to advance to the next level.
 *
 * @see LevelParent
 * @see EnemyPlane
 * @see EliteEnemyPlane
 * @see Satellite
 * @see Asteroid
 * @see SoundManager
 */
public class LevelThree extends LevelParent {

	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/Backgrounds/level3alt.png";
	private static final String NEXT_LEVEL = "com.example.demo.levels.LevelFour";
	private static final int TOTAL_ENEMIES = 4;
	private static final int MAX_OBSTACLES = 2;
	private static final int KILLS_TO_ADVANCE = 25;
	private static final double ENEMY_SPAWN_PROBABILITY = .15;
	private static final double ELITE_PROBABILITY = 0.3;
	private static final double OBSTACLE_SPAWN_PROBABILITY = .3;
	private static final int PLAYER_INITIAL_HEALTH = 5;

	//sounds
	private SoundManager soundManager;
	private static final String LEVEL_BG_MUSIC = "/com/example/demo/sfx/level_music/level3Music.mp3";

	/**
	 * Constructs a LevelThree instance.
	 * Initializes the level with the background image, screen dimensions, player's initial health,
	 * and the game stage. Also plays the background music specific to this level.
	 *
	 * @param screenHeight The height of the screen.
	 * @param screenWidth The width of the screen.
	 * @param stage The current JavaFX stage for the level.
	 */
	public LevelThree(double screenHeight, double screenWidth, Stage stage) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH, stage);
		soundManager = SoundManager.getInstance(); // Initialize SoundManager instance
		soundManager.playBackgroundMusic(LEVEL_BG_MUSIC); // Play background music for the level
	}

	/**
	 * Checks whether the game is over. If the user is destroyed, the game is lost.
	 * If the user has reached the target number of kills, the game advances to the next level
	 * and the background music is stopped.
	 */
	@Override
	protected void checkIfGameOver() {
		if (userIsDestroyed()) {
			loseGame();
		}
		else if (userHasReachedKillTarget()) {
			soundManager.stopBackgroundMusic();
			goToNextLevel(NEXT_LEVEL);
		}
	}

	/**
	 * Initializes the friendly units for the level, which in this case is the player (user).
	 * The user is added to the root node of the level's scene.
	 */
	@Override
	protected void initializeFriendlyUnits() {
		getRoot().getChildren().add(getUser());
	}

	/**
	 * Creates an enemy for the level. The enemy can either be a regular enemy plane or an elite enemy plane,
	 * with the elite enemy plane having a higher probability of spawning.
	 *
	 * @return An instance of an enemy (either EnemyPlane or EliteEnemyPlane).
	 */
	@Override
	protected ActiveActorDestructible createEnemy() {
		double newEnemyInitialYPosition = Math.random() * getEnemyMaximumYPosition();
		if (Math.random() < ELITE_PROBABILITY) {
			return new EliteEnemyPlane(getScreenWidth(), newEnemyInitialYPosition);
		} else {
			return new EnemyPlane(getScreenWidth(), newEnemyInitialYPosition);
		}
	}

	/**
	 * Creates an obstacle for the level. The obstacle can either be a satellite or an asteroid, with the
	 * satellite having a lower probability of spawning.
	 *
	 * @return An instance of an obstacle (either Satellite or Asteroid).
	 */
	@Override
	protected ActiveActorDestructible createObstacle() {
		double newObstacleInitialYPosition = Math.random() * getEnemyMaximumYPosition();
		if (Math.random() < 0.1) {
			return new Satellite(getScreenWidth(), newObstacleInitialYPosition);
		} else {
			return new Asteroid(getScreenWidth(), newObstacleInitialYPosition);
		}
	}//Environmental hazards do not count as enemies, so a different logic is used.

	/**
	 * Returns the probability of spawning an enemy in the level.
	 *
	 * @return The probability of enemy spawning.
	 */
	@Override
	protected double getEnemySpawnProbability() {
		return ENEMY_SPAWN_PROBABILITY;
	}

	/**
	 * Returns the probability of spawning an obstacle in the level.
	 *
	 * @return The probability of obstacle spawning.
	 */
	@Override
	protected double getObstacleSpawnProbability() {
		return OBSTACLE_SPAWN_PROBABILITY;
	}

	/**
	 * Returns the total number of enemies that can spawn in the level at any given time.
	 *
	 * @return The total number of enemies.
	 */
	@Override
	protected int getTotalEnemies() {
		return TOTAL_ENEMIES;
	}

	/**
	 * Returns the maximum number of obstacles that can spawn in the level at any given time.
	 *
	 * @return The total number of obstacles.
	 */
	@Override
	protected int getTotalObstacles() {
		return MAX_OBSTACLES;
	}

	/**
	 * Instantiates and returns the level view for this specific level. The level view displays
	 * the current state of the level, including the player's health.
	 *
	 * @return The LevelView instance for LevelThree.
	 */
	@Override
	protected LevelView instantiateLevelView() {
		return new LevelView(getRoot(), PLAYER_INITIAL_HEALTH);
	}

	/**
	 * Checks if the player has reached the target number of kills required to advance to the next level.
	 *
	 * @return true if the player's kill count meets or exceeds the target, false otherwise.
	 */
	private boolean userHasReachedKillTarget() {
		return getUser().getNumberOfKills() >= KILLS_TO_ADVANCE;
	}

}