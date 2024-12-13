package com.example.demo.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * The {@code MainMenuController} class is a newly added class that manages the main menu functionality in the Sky Battle application.
 * <p>
 * It provides user interactions for starting the game, accessing settings, and exiting the application.
 * It also integrates sound effects and background music to enhance the user experience.
 * </p>
 */
public class MainMenuController {

    /**
     * The default height of the main menu screen.
     */
    private static final int SCREEN_HEIGHT = 750;
    /**
     * The default width of the main menu screen.
     */
    private static final int SCREEN_WIDTH = 1300;
    /**
     * The primary stage for displaying the main menu and the game.
     * Carried over to other classes who need it to call the stage/game scene.
     */
    private Stage stage;
    /**
     * The file path to the settings screen FXML file.
     */
    private static final String SETTINGS_FXML = "/fxml/settings.fxml";
    /**
     * The file path to the button click sound effect.
     */
    private static final String BUTTON_CLICK_SFX = "/com/example/demo/sfx/ui_sfx/buttonclick.mp3";
    /**
     * The file path to the main menu background music.
     */
    private static final String BG_MUSIC = "/com/example/demo/sfx/level_music/mainMenuMusic.mp3";
    /**
     * The sound manager for handling sound effects and background music.
     */
    private SoundManager soundManager;

    /**
     * Initializes the main menu controller with the specified stage.
     * <p>
     * This method sets up the sound manager, loads sound effects, and plays background music.
     * </p>
     *
     * @param stage the primary stage for the application
     */
    public void initialize(Stage stage) {
        this.stage = stage;
        soundManager = SoundManager.getInstance();

        // Load sound effects
        soundManager.loadSFX("button_click", BUTTON_CLICK_SFX);

        // Play background music if not already playing
        if (soundManager.getBackgroundMusicPlayer() == null) {
            soundManager.playBackgroundMusic(BG_MUSIC);  // Play background music only if not already playing
        }
    }

    /**
     * Handles the action triggered when the start button is clicked.
     * <p>
     * This method plays a button click sound, changes the background music, and initiates the game by launching the first level.
     * </p>
     *
     * @param event the {@code ActionEvent} triggered by the button click
     * @throws ClassNotFoundException if the level class cannot be found
     * @throws InvocationTargetException if the constructor throws an exception
     * @throws NoSuchMethodException if the required constructor is not found
     * @throws InstantiationException if the level class cannot be instantiated
     * @throws IllegalAccessException if the constructor is not accessible
     */
    @FXML
    private void onStartButtonClicked(ActionEvent event) throws ClassNotFoundException,
            InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        System.out.println("Start button clicked!");
        soundManager.playSFX("button_click");  // Play button click sound
        soundManager.stopBackgroundMusic();
        Controller myController = new Controller(stage);
        myController.launchGame();
    }

    /**
     * Handles the action triggered when the settings button is clicked.
     * <p>
     * This method navigates to the settings screen and initializes its controller.
     * </p>
     *
     * @param event the {@code ActionEvent} triggered by the button click
     * @throws IOException if the FXML file cannot be loaded
     */
    @FXML
    private void onSettingsButtonClicked(ActionEvent event) throws IOException {
        System.out.println("Settings button clicked!");
        soundManager.playSFX("button_click");

        FXMLLoader settingsLoader = new FXMLLoader(getClass().getResource(SETTINGS_FXML));
        Parent root = settingsLoader.load();

        SettingsController settingsController = settingsLoader.getController();
        settingsController.initialize(stage); // Pass MediaPlayer and SoundManager

        Scene scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Handles the action triggered when the exit button is clicked.
     * <p>
     * This method plays a button click sound and exits the application.
     * </p>
     *
     * @param event the {@code ActionEvent} triggered by the button click
     */
    @FXML
    private void onExitButtonClicked(ActionEvent event) {
        System.out.println("Exiting game!");
        soundManager.playSFX("button_click");  // Play button click sound
        System.exit(0);
    }

    /**
     * Displays the main menu on the specified stage.
     * <p>
     * This method loads the main menu FXML file and initializes the controller.
     * </p>
     *
     * @param stage the primary stage for the application
     * @throws IOException if the FXML file cannot be loaded
     */
    public void showMainMenu(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/mainmenu.fxml"));
        Parent root = loader.load();

        // Get the controller and initialize it
        MainMenuController mainMenuController = loader.getController();
        mainMenuController.initialize(stage);

        // Set the scene and show the stage
        Scene scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT);
        stage.setScene(scene);

    }
}