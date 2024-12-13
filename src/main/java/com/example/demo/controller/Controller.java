package com.example.demo.controller;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Observable;
import java.util.Observer;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import com.example.demo.levelparent.LevelParent;

/**
 * The {@code Controller} class handles the game flow and level transitions in the Sky Battle application.
 * Original source code can be found in the original git repository: <a href="https://github.com/kooitt/CW2024.git">ORIGINAL SOURCE CODE</a>
 * <p>
 * This class uses reflection to dynamically load levels and implements the {@link Observer} interface
 * to respond to updates from observed objects. It manages the game's main stage and coordinates
 * level-specific logic.
 * </p>
 */
public class Controller implements Observer {

	/**
	 * The name of the first level class
	 */
	private static final String LEVEL_ONE_CLASS_NAME = "com.example.demo.levels.LevelOne";

	/**
	 * The primary stage for displaying the main menu and the game.
	 * Carried over to other classes who need it to call the stage/game scene.
	 */
	private final Stage stage;

	/**
	 * Constructs a {@code Controller} with the specified stage.
	 *
	 * @param stage the primary stage for the game
	 */
	public Controller(Stage stage) {
		this.stage = stage;
	}

	/**
	 * Launches the game by showing the stage and navigating to the first level.
	 *
	 * @throws ClassNotFoundException if the level class cannot be found
	 * @throws NoSuchMethodException if the required constructor is not found
	 * @throws SecurityException if access to the constructor is denied
	 * @throws InstantiationException if the level class cannot be instantiated
	 * @throws IllegalAccessException if the constructor is not accessible
	 * @throws IllegalArgumentException if invalid arguments are provided to the constructor
	 * @throws InvocationTargetException if the constructor throws an exception
	 */
	public void launchGame() throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException  {
			stage.show();
			goToLevel(LEVEL_ONE_CLASS_NAME);
	}

	/**
	 * Navigates to the specified level by dynamically loading its class and invoking its constructor.
	 *
	 * @param className the fully qualified name of the level class to load
	 * @throws ClassNotFoundException if the specified class cannot be found
	 * @throws NoSuchMethodException if the required constructor is not found
	 * @throws SecurityException if access to the constructor is denied
	 * @throws InstantiationException if the level class cannot be instantiated
	 * @throws IllegalAccessException if the constructor is not accessible
	 * @throws IllegalArgumentException if invalid arguments are provided to the constructor
	 * @throws InvocationTargetException if the constructor throws an exception
	 */
	private void goToLevel(String className) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
			Class<?> myClass = Class.forName(className);
			Constructor<?> constructor = myClass.getConstructor(double.class, double.class, Stage.class);
			LevelParent myLevel = (LevelParent) constructor.newInstance(stage.getHeight(), stage.getWidth(), stage);
			myLevel.addObserver(this);
			Scene scene = myLevel.initializeScene();
			stage.setWidth(1300);
			stage.setHeight(750);
			stage.setScene(scene);
			myLevel.startGame();

	}

	/**
	 * Responds to updates from observed objects by navigating to the specified level.
	 *
	 * @param arg0 is the observable object that triggered the update.
	 * @param arg1 is an argument passed to the {@code notifyObservers} method.
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
		try {
			goToLevel((String) arg1);
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
				| IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText(e.getClass().toString());
			alert.show();
		}
	}

}
