package com.example.demo.controller;

import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Instead of instantly launching levels, the {@code Main} class now initializes the main menu first and launches the application window.
 * Original source code can be found in the original git repository: <a href="https://github.com/kooitt/CW2024.git">ORIGINAL SOURCE CODE</a>
 */
public class Main extends Application {

	/**
	 * The title of the application window.
	 */
	private static final String TITLE = "Sky Battle";

	/**
	 * Starts the JavaFX application.
	 * <p>
	 * It sets the title, disables resizing, and displays the main menu using the
	 * {@link MainMenuController}.
	 * </p>
	 *
	 * @param stage the primary stage for this application
	 * @throws IOException if an I/O error occurs during initialization
	 */
	@Override
	public void start(Stage stage) throws IOException {

		// Initialize the main menu
		MainMenuController mainMenuController = new MainMenuController();
		mainMenuController.showMainMenu(stage);

		stage.setTitle(TITLE);
		stage.setResizable(false);
		stage.show();
	}

	/**
	 * The main method serves as the entry point for the application.
	 * @param args the command-line arguments (which is not used in this app)
	 */
	public static void main(String[] args) {
		launch();
	}
}