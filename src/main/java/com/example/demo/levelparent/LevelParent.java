package com.example.demo.levelparent;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import com.example.demo.actors.*;
import com.example.demo.actors.player.*;
import com.example.demo.controller.SoundManager;
import com.example.demo.levels.LevelView;
import com.example.demo.controller.MainMenuController;
import javafx.animation.*;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.util.Duration;
import javafx.stage.Stage;

/**
 * This is an abstract class representing a level in the game.
 * It handles the game loop, player and enemy actions, collisions, and transitions between game states.
 * Uses a factory design pattern for creating enemies and obstacles.
 * Original source code can be found in the original git repository: <a href="https://github.com/kooitt/CW2024.git">ORIGINAL SOURCE CODE</a>
 */
public abstract class LevelParent extends Observable {

	/** Adjustment value for determining the maximum Y position for enemies. */
	private static final double SCREEN_HEIGHT_ADJUSTMENT = 150;

	/** Delay between frames in milliseconds. */
	private static final int MILLISECOND_DELAY = 50;

	/** Cooldown period for firing projectiles in milliseconds. */
	private static final long PROJECTILE_COOLDOWN = 110;

	private static final String BG_MUSIC = "/com/example/demo/sfx/level_music/mainMenuMusic.mp3";
	private static final String BUTTON_CLICK_SFX = "/com/example/demo/sfx/ui_sfx/buttonclick.mp3";
	private static final String SHOOT_SFX = "/com/example/demo/sfx/level_sfx/userShootalt.mp3";

	private final double screenHeight;
	private final double screenWidth;
	private final double enemyMaximumYPosition;

	private final Group root;
	private final Timeline timeline;
	private final UserPlane user;
	private final Scene scene;
	private final ImageView background;
	private final LevelView levelView;

	private boolean isGameActive;
	private boolean didGameEnd;

	private final List<ActiveActorDestructible> friendlyUnits;
	private final List<ActiveActorDestructible> enemyUnits;
	private final List<ActiveActorDestructible> userProjectiles;
	private final List<ActiveActorDestructible> enemyProjectiles;
	private final List<ActiveActorDestructible> obstacles;

	private int currentNumberOfEnemies;
	private int currentNumberOfObstacles;

	private final Set<KeyCode> activeKeys = new HashSet<>();
	private long lastFiredProjectile = 0;

	private Stage stage;
	private Button popupButton;

	private final SoundManager soundManager;

	/**
	 * Constructs a LevelParent object with the specified parameters.
	 *
	 * @param backgroundImageName the file path of the background image
	 * @param screenHeight        the height of the screen
	 * @param screenWidth         the width of the screen
	 * @param playerInitialHealth the initial health of the player
	 * @param stage               the primary stage for the level
	 */
	public LevelParent(String backgroundImageName, double screenHeight, double screenWidth, int playerInitialHealth, Stage stage) {
		this.root = new Group();
		this.scene = new Scene(root, screenWidth, screenHeight);
		this.timeline = new Timeline();
		this.user = new UserPlane(playerInitialHealth);
		this.friendlyUnits = new ArrayList<>();
		this.enemyUnits = new ArrayList<>();
		this.userProjectiles = new ArrayList<>();
		this.enemyProjectiles = new ArrayList<>();
		this.obstacles = new ArrayList<>();


        this.background = new ImageView(new Image(Objects.requireNonNull(getClass().getResource(backgroundImageName)).toExternalForm()));
		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;
		this.enemyMaximumYPosition = screenHeight - SCREEN_HEIGHT_ADJUSTMENT;
		this.levelView = instantiateLevelView();
		this.currentNumberOfEnemies = 0;
		this.currentNumberOfObstacles = 0;

		this.stage = stage;
		initializeTimeline();
		friendlyUnits.add(user);

		//Sound-related
		this.soundManager = SoundManager.getInstance();

		soundManager.loadSFX("button_click", BUTTON_CLICK_SFX);
		soundManager.loadSFX("shoot", SHOOT_SFX);
	}

	/**
	 * Plays the sound effect for shooting.
	 */
	protected void playShootSound() {
		soundManager.playSFX("shoot");
	}

	/**
	 * Initializes the friendly units for the level.
	 * <p>
	 * Subclasses must implement this method to define specific friendly units.
	 * </p>
	 */
	protected abstract void initializeFriendlyUnits();

	/**
	 * Checks if the game is over.
	 * <p>
	 * Subclasses must implement this method to define game-over conditions.
	 * </p>
	 */
	protected abstract void checkIfGameOver();

	/**
	 * Creates a new enemy actor for the level.
	 * <p>
	 * Subclasses can override this method to define specific enemy behavior.
	 * </p>
	 *
	 * @return the created enemy actor, or {@code null} if no enemy is created
	 */
	protected ActiveActorDestructible createEnemy() {
		// Default implementation returns null (no enemy for this level)
		return null;
	}

	/**
	 * Creates a new obstacle for the level.
	 * <p>
	 * Subclasses can override this method to define specific obstacle behavior.
	 * </p>
	 *
	 * @return the created obstacle, or {@code null} if no obstacle is created
	 */
	protected ActiveActorDestructible createObstacle() {
		// Default implementation returns null (no obstacle for this level)
		return null;
	}

	/**
	 * Returns the spawn probability for enemies.
	 *
	 * @return a double value between 0.0 and 1.0 indicating the spawn probability
	 */
	protected double getEnemySpawnProbability() {
		return 0.0; // Default: No enemy spawn probability
	}

	/**
	 * Returns the spawn probability for obstacles.
	 *
	 * @return a double value between 0.0 and 1.0 indicating the spawn probability
	 */
	protected double getObstacleSpawnProbability() {
		return 0.0; // Default: No obstacle spawn probability
	}

	/**
	 * Returns the total number of enemies for the level.
	 *
	 * @return the total number of enemies
	 */
	protected int getTotalEnemies() {
		return 0; // Default: No enemies allowed
	}

	/**
	 * Returns the total number of obstacles for the level.
	 *
	 * @return the total number of obstacles
	 */
	protected int getTotalObstacles() {
		return 0; // Default: No obstacles allowed
	}

	/**
	 * Spawns enemy units based on the spawn probability and total allowed enemies.
	 */
	protected void spawnEnemyUnits() {
		currentNumberOfEnemies = getCurrentNumberOfEnemies();
		for (int i = 0; i < getTotalEnemies() - currentNumberOfEnemies; i++) {
			if (Math.random() < getEnemySpawnProbability()) {
				ActiveActorDestructible newEnemy = createEnemy();
				if (newEnemy != null) {
					addEnemyUnit(newEnemy);
				}
			}
		}
	}

	/**
	 * Spawns obstacles based on the spawn probability and total allowed obstacles.
	 */
	protected void spawnObstacles() {
		currentNumberOfObstacles = getCurrentNumberOfObstacles();
		for (int i = 0; i < getTotalObstacles() - currentNumberOfObstacles; i++) {
			if (Math.random() < getObstacleSpawnProbability()) {
				ActiveActorDestructible newObstacle = createObstacle();
				if (newObstacle != null) {
					addObstacle(newObstacle);
				}
			}
		}
	}

	/**
	 * Abstract method to instantiate the {@code LevelView} for the level.
	 *
	 * @return the instantiated {@code LevelView} object
	 */
	protected abstract LevelView instantiateLevelView();

	/**
	 * Initializes the scene for the level.
	 *
	 * @return the initialized {@code Scene} object
	 */
	public Scene initializeScene() {
		initializeBackground();
		initializeFriendlyUnits();
		levelView.showHeartDisplay();
		return scene;
	}

	/**
	 * Starts the game by activating the timeline and setting game status to active.
	 */
	public void startGame() {
		background.requestFocus();
		isGameActive = true; //Add a value to help the game decide if the game is running at the moment. Useful
		didGameEnd = false;
		timeline.play();
	}

	/**
	 * Toggles the game between paused and active states.
	 * <p>
	 * Pauses the game if it is currently active, or resumes it if it is paused.
	 * Displays or hides the pause menu as needed.
	 * </p>
	 */
	public void pauseGame() {
		if (isGameActive && !didGameEnd) {
			isGameActive = false;
			timeline.pause();
			levelView.showPauseImage();
			showMainMenuButton(stage);
		}
		else if (!isGameActive && !didGameEnd) {
			isGameActive = true;
			timeline.play();
			levelView.hidePauseImage();

			if (popupButton != null) {
				Group root = (Group) scene.getRoot();
				root.getChildren().remove(popupButton);
				popupButton = null; // Clear the reference
			}
		}
	} //Pauses the game if the game is active, and starts the game again if it is already paused.

	/**
	 * Cleans up all assets on the screen to prepare for the next level.
	 * <p>
	 * This includes clearing projectiles, enemies, obstacles, and friendly units.
	 * </p>
	 */
	private void cleanAssets() {
		//Do a proper cleaning of all assets on the screen before proceeding to the next level.
		user.destroy();
		userProjectiles.clear();
		friendlyUnits.clear();
		enemyUnits.clear();
		enemyProjectiles.clear();
		obstacles.clear();
	}

	/**
	 * Proceeds to the next level by stopping the timeline, cleaning assets, and notifying observers.
	 *
	 * @param levelName the name of the next level
	 */
	public void goToNextLevel(String levelName) {
		timeline.stop(); //Fixes the memory leaks produced from not having the user plane cleared when level is cleared
		cleanAssets(); //Clean all assets on current screen
		setChanged();
		notifyObservers(levelName);
	}

	/**
	 * Updates the game scene by handling spawning, actions, and collisions.
	 * <p>
	 * Also checks game-over conditions and updates the visual representation of the level.
	 * </p>
	 */
	private void updateScene() {
		spawnEnemyUnits();
		spawnObstacles();
		updateActors();
		generateEnemyFire();
		handlePlayerActions();
		updateNumberOfEnemies();
		updateNumberOfObstacles();
		handleEnemyPenetration();
		handleObstaclePenetration();
		handleObstacleCollisions();
		handleProjectileObstacleCollisions();
		handleUserProjectileCollisions();
		handleEnemyProjectileCollisions();
		handlePlaneCollisions();
		removeAllDestroyedActors();
		updateKillCount();
		updateLevelView();
		checkIfGameOver();
	}

	/**
	 * Initializes the timeline for the game loop with a specified frame delay.
	 */
	private void initializeTimeline() {
		timeline.setCycleCount(Timeline.INDEFINITE);
		KeyFrame gameLoop = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> updateScene());
		timeline.getKeyFrames().add(gameLoop);
	}

	/**
	 * Initializes the background image and sets up key event handlers.
	 */
	private void initializeBackground() {
		background.setFocusTraversable(true);
		background.setFitHeight(screenHeight);
		background.setFitWidth(screenWidth);
		background.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent e) {

				KeyCode kc = e.getCode();
				activeKeys.add(kc); //On key press, add that key to the hash set
				if (kc == KeyCode.ESCAPE) pauseGame();
			}
		});
		background.setOnKeyReleased(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent e) {
				KeyCode kc = e.getCode();
				activeKeys.remove(kc); //On key release, remove that key from the hash set
				if (kc == KeyCode.UP || kc == KeyCode.DOWN || kc == KeyCode.W || kc == KeyCode.S) {
					user.stopY();
				}
				else if (kc == KeyCode.LEFT || kc == KeyCode.RIGHT || kc == KeyCode.A || kc == KeyCode.D) {
					user.stopX();
				}
			}
		});
		root.getChildren().add(background);
	}

	/**
	 * Handles user input and performs actions such as moving and firing projectiles.
	 * <p>
	 * Ensures inputs do not interfere with each other.
	 * </p>
	 */
	private void handlePlayerActions() {
		if (!isGameActive) return; //Void all inputs if the game is currently not active.
		if (activeKeys.contains(KeyCode.UP) || (activeKeys.contains(KeyCode.W))) user.moveUp();
		if (activeKeys.contains(KeyCode.DOWN) || (activeKeys.contains(KeyCode.S))) user.moveDown();
		if (activeKeys.contains(KeyCode.LEFT) || (activeKeys.contains(KeyCode.A))) user.moveLeft();
		if (activeKeys.contains(KeyCode.RIGHT) || (activeKeys.contains(KeyCode.D))) user.moveRight();
		if (activeKeys.contains(KeyCode.SPACE) || (activeKeys.contains(KeyCode.K))) {
			long currentTime = System.currentTimeMillis();
			if (currentTime - lastFiredProjectile> PROJECTILE_COOLDOWN) {
				fireProjectile();
				playShootSound();
				lastFiredProjectile = currentTime;
			}
		} //Makes sure that the active keys don't make any weird combinations when the user is inputting as it can effectively separate the processing of keys
	}

	/**
	 * Fires a projectile from the user plane and adds it to the game scene.
	 */
	private void fireProjectile() {
		ActiveActorDestructible projectile = user.fireProjectile();
		root.getChildren().add(projectile);
		userProjectiles.add(projectile);
	}

	/**
	 * Generates enemy fire by spawning projectiles from each enemy unit.
	 */
	private void generateEnemyFire() {
		enemyUnits.forEach(enemy -> spawnEnemyProjectile(((FighterPlane) enemy).fireProjectile()));
	}

	/**
	 * Spawns a projectile fired by an enemy and adds it to the game scene if valid.
	 *
	 * @param projectile the enemy projectile to be spawned
	 */
	private void spawnEnemyProjectile(ActiveActorDestructible projectile) {
		if (projectile != null) {
			root.getChildren().add(projectile);
			enemyProjectiles.add(projectile);
		}
	}

	/**
	 * Updates all actors in the game, including friendly units, enemy units, projectiles, and obstacles.
	 */
	private void updateActors() {
		friendlyUnits.forEach(ActiveActorDestructible::updateActor);
		enemyUnits.forEach(ActiveActorDestructible::updateActor);
		userProjectiles.forEach(ActiveActorDestructible::updateActor);
		enemyProjectiles.forEach(ActiveActorDestructible::updateActor);
		obstacles.forEach(ActiveActorDestructible::updateActor);
	}

	/**
	 * Removes all destroyed actors from the game scene and their respective lists.
	 */
	private void removeAllDestroyedActors() {
		removeDestroyedActors(friendlyUnits);
		removeDestroyedActors(enemyUnits);
		removeDestroyedActors(userProjectiles);
		removeDestroyedActors(enemyProjectiles);
		removeDestroyedActors(obstacles);
	}

	/**
	 * Removes destroyed actors from a specific list and the game scene.
	 *
	 * @param actors the list of actors to check for destruction
	 */
	private void removeDestroyedActors(List<ActiveActorDestructible> actors) {
		List<ActiveActorDestructible> destroyedActors = actors.stream().filter(ActiveActorDestructible::isDestroyed)
				.collect(Collectors.toList());
		root.getChildren().removeAll(destroyedActors);
		actors.removeAll(destroyedActors);
	}

	/**
	 * Handles collisions between friendly and enemy planes.
	 */
	private void handlePlaneCollisions() {
		handleCollisions(friendlyUnits, enemyUnits);
	}

	/**
	 * Handles collisions between friendly planes and obstacles.
	 */
	private void handleObstacleCollisions() {
		handleCollisions(friendlyUnits, obstacles);
	}

	/**
	 * Handles collisions between user-fired projectiles and enemy units.
	 */
	private void handleUserProjectileCollisions() {
		handleCollisions(userProjectiles, enemyUnits);
	}

	/**
	 * Handles collisions between user-fired projectiles and obstacles.
	 */
	private void handleProjectileObstacleCollisions() {
		handleCollisions(userProjectiles, obstacles);
	}

	/**
	 * Handles collisions between enemy projectiles and friendly units.
	 */
	private void handleEnemyProjectileCollisions() {
		handleCollisions(enemyProjectiles, friendlyUnits);
	}

	/**
	 * Detects and processes collisions between two groups of actors.
	 *
	 * @param actors1 the first group of actors
	 * @param actors2 the second group of actors
	 */
	private void handleCollisions(List<ActiveActorDestructible> actors1,
			List<ActiveActorDestructible> actors2) {
		for (ActiveActorDestructible actor : actors2) {
			for (ActiveActorDestructible otherActor : actors1) {
				if (actor.getBoundsInParent().intersects(otherActor.getBoundsInParent())) {
					actor.takeDamage();
					otherActor.takeDamage();
				}
			}
		}
	}

	/**
	 * Handles cases where an enemy unit penetrates the user's defenses.
	 */
	private void handleEnemyPenetration() {
		for (ActiveActorDestructible enemy : enemyUnits) {
			if (enemyHasPenetratedDefenses(enemy)) {
				user.takeDamage();
				enemy.destroy();
				user.decrementKillCount();
			}
		}
	}

	/**
	 * Handles cases where an obstacle penetrates the user's defenses.
	 */
	private void handleObstaclePenetration() {
		for (ActiveActorDestructible obstacle : obstacles) {
			if (obstacleHasPenetratedDefenses(obstacle)) {
				obstacle.destroy();
				System.out.println("Obstacle cleared!");
			}
		}
	} //Obstacles do not count as enemies. Therefore, they should not make user take damage

	/**
	 * Updates the level view to reflect the user's current health.
	 */
	private void updateLevelView() {
		levelView.removeHearts(user.getHealth());
	}

	/**
	 * Updates the user's kill count based on defeated enemies.
	 */
	private void updateKillCount() {
		for (int i = 0; i < currentNumberOfEnemies - enemyUnits.size(); i++) {
			user.incrementKillCount();
		}
	}

	/**
	 * Checks if an enemy unit has penetrated the user's defenses.
	 *
	 * @param enemy the enemy unit to check
	 * @return true if the enemy has penetrated, false otherwise
	 */
	private boolean enemyHasPenetratedDefenses(ActiveActorDestructible enemy) {
		return Math.abs(enemy.getTranslateX()) > screenWidth;
	}

	/**
	 * Checks if an obstacle has penetrated the user's defenses.
	 *
	 * @param obstacles the obstacle to check
	 * @return true if the obstacle has penetrated, false otherwise
	 */
	private boolean obstacleHasPenetratedDefenses(ActiveActorDestructible obstacles) {
		return Math.abs(obstacles.getTranslateX()) > screenWidth;
	}

	/**
	 * Handles actions for winning the game.
	 */
	protected void winGame() {
		timeline.stop();
		isGameActive = false;
		levelView.showWinImage();
		cleanAssets();
		showMainMenuButton(stage);
	}

	/**
	 * Handles actions for losing the game.
	 */
	protected void loseGame() {
		timeline.stop();
		isGameActive = false;
		didGameEnd = true;
		levelView.showGameOverImage();
		showMainMenuButton(stage);
	}

	/**
	 * Displays a button to return to the main menu and handles its functionality.
	 *
	 * @param stage the primary stage of the application
	 */
	private void showMainMenuButton(Stage stage) {
		// Create a StackPane as a parent container to center the button
		Group root = (Group) scene.getRoot();

		if (popupButton == null) {
			popupButton = new Button("Go Back To Main Menu");
			popupButton.setStyle("-fx-font-size: 16px; -fx-padding: 10;");

			// Position the button in the center
			popupButton.setLayoutX(555); // X-coordinate
			popupButton.setLayoutY(475); // Y-coordinate

			popupButton.setFocusTraversable(false);

			popupButton.setOnAction(event -> {
				cleanAssets(); //To ensure all assets will always be cleaned.
				// Close the popup button (remove it) when clicked
				root.getChildren().remove(popupButton);
				soundManager.stopBackgroundMusic();
				soundManager.playBackgroundMusic(BG_MUSIC);
				MainMenuController mainMenuController = new MainMenuController();
				try {
					mainMenuController.showMainMenu(stage);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			});
		}
		// Add the popup button to the root layout (overlay)
		root.getChildren().add(popupButton);
	}

	/**
	 * Gets the user's plane.
	 *
	 * @return the user's plane
	 */
	protected UserPlane getUser() {
		return user;
	}

	/**
	 * Gets the root group of the game scene.
	 *
	 * @return the root group
	 */
	protected Group getRoot() {
		return root;
	}

	/**
	 * Gets the current number of enemy units.
	 *
	 * @return the number of enemy units
	 */
	protected int getCurrentNumberOfEnemies() {
		return enemyUnits.size();
	}

	/**
	 * Adds an enemy unit to the game scene.
	 *
	 * @param enemy the enemy unit to add
	 */
	protected void addEnemyUnit(ActiveActorDestructible enemy) {
		enemyUnits.add(enemy);
		root.getChildren().add(enemy);
	}

	/**
	 * Gets the current number of obstacles.
	 *
	 * @return the number of obstacles
	 */
	protected int getCurrentNumberOfObstacles() {
		return obstacles.size();
	}

	/**
	 * Adds an obstacle to the game scene.
	 *
	 * @param obstacle the obstacle to add
	 */
	protected void addObstacle(ActiveActorDestructible obstacle) {
		obstacles.add(obstacle);
		root.getChildren().add(obstacle);
	} // Add obstacles to the scene

	/**
	 * Gets the maximum Y position for enemy units.
	 *
	 * @return the maximum Y position
	 */
	protected double getEnemyMaximumYPosition() {
		return enemyMaximumYPosition;
	}

	/**
	 * Gets the screen width.
	 *
	 * @return the screen width
	 */
	protected double getScreenWidth() {
		return screenWidth;
	}

	/**
	 * Checks if the user's plane is destroyed.
	 *
	 * @return true if the user's plane is destroyed, false otherwise
	 */
	protected boolean userIsDestroyed() {
		return user.isDestroyed();
	}

	/**
	 * Updates the number of enemies in the game.
	 */
	private void updateNumberOfEnemies() {
		currentNumberOfEnemies = enemyUnits.size();
	}

	/**
	 * Updates the number of obstacles in the game.
	 */
	private void updateNumberOfObstacles() {
		currentNumberOfObstacles = obstacles.size();
	}
}
