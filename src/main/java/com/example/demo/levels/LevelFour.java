package com.example.demo.levels;

import com.example.demo.actors.ActiveActorDestructible;
import com.example.demo.actors.enemies.Boss;
import com.example.demo.actors.obstacles.Asteroid;
import com.example.demo.actors.obstacles.Satellite;
import com.example.demo.levelparent.LevelParent;
import com.example.demo.controller.SoundManager;
import javafx.stage.Stage;

/**
 * Represents the fourth level in the game. In this level, the player faces a boss enemy
 * and navigates through environmental hazards such as asteroids and satellites.
 * The level features background music and a set of obstacles that spawn periodically.
 *
 * @see LevelParent
 * @see Boss
 * @see Asteroid
 * @see Satellite
 * @see SoundManager
 */
public class LevelFour extends LevelParent {

	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/Backgrounds/level4alt.png";
	private static final int PLAYER_INITIAL_HEALTH = 5;
	private static final int MAX_OBSTACLES = 3;
	private static final double OBSTACLE_SPAWN_PROBABILITY = .5;
	private final Boss boss;

	//sounds
	private SoundManager soundManager;
	private static final String LEVEL_BG_MUSIC = "/com/example/demo/sfx/level_music/bossMusic.mp3";

	/**
	 * Constructs a LevelFour instance.
	 * Initializes the level with the background image, screen dimensions, player's initial health,
	 * and the game stage. Also plays the background music specific to this level.
	 *
	 * @param screenHeight The height of the screen.
	 * @param screenWidth The width of the screen.
	 * @param stage The current JavaFX stage for the level.
	 */
    public LevelFour(double screenHeight, double screenWidth, Stage stage) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH, stage);
		soundManager = SoundManager.getInstance(); // Initialize SoundManager instance
		soundManager.playBackgroundMusic(LEVEL_BG_MUSIC); // Play background music for the level
		boss = new Boss();
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
	 * Checks whether the game is over. If the user is destroyed and the boss is not yet destroyed, the game is lost.
	 * If the boss is destroyed, the game is won and the background music is stopped.
	 */
	@Override
	protected void checkIfGameOver() {
		if (userIsDestroyed() && !(boss.isDestroyed())) {
			loseGame();
		} //Added additional condition so both would not be met
		else if (boss.isDestroyed()) {
			soundManager.stopBackgroundMusic();
			winGame();
		}
	}

	/**
	 * Spawns the enemy units for the level. In this case, it spawns the boss enemy if no enemies are currently present.
	 * The boss's shield image is added to the root node of the scene.
	 */
	@Override
	protected void spawnEnemyUnits() {
		if (getCurrentNumberOfEnemies() == 0) {
			addEnemyUnit(boss);
			getRoot().getChildren().add(boss.getshieldImage());
		}
	}

	/**
	 * Creates an obstacle to spawn in the level. The obstacle is randomly chosen between a satellite or an asteroid,
	 * with a different probability for each type.
	 *
	 * @return An instance of an obstacle, either a Satellite or an Asteroid.
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
	 * Returns the probability of spawning an obstacle in the level.
	 *
	 * @return The probability of obstacle spawning.
	 */
	@Override
	protected double getObstacleSpawnProbability() {
		return OBSTACLE_SPAWN_PROBABILITY;
	}

	/**
	 * Returns the total number of obstacles that can exist in the level at any given time.
	 *
	 * @return The maximum number of obstacles.
	 */
	@Override
	protected int getTotalObstacles() {
		return MAX_OBSTACLES;
	}

	/**
	 * Instantiates and returns the level view for this specific level. The level view displays
	 * the current state of the level, including the player's health.
	 *
	 * @return The LevelView instance for LevelFour.
	 */
	@Override
	protected LevelView instantiateLevelView() {
        return new LevelViewLevelFour(getRoot(), PLAYER_INITIAL_HEALTH);
	}

}
