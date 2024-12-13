package com.example.demo.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.media.MediaPlayer;
import javafx.scene.control.Slider;

import java.io.IOException;
/**
 * The {@code SettingsController} class is a newly added class that manages the settings screen in the application.
 * <p>
 * This class provides functionality for adjusting volume settings and navigating back to the main menu.
 * It uses {@code SoundManager} for handling sound effects and background music volume.
 * </p>
 */
 public class SettingsController {

    /**
     * The primary stage for displaying the main menu and the game.
     * Carried over to other classes who need it to call the stage/game scene.
     */
    private Stage stage;
    /**
     * The file path to the button click sound effect.
     */
    private static final String BUTTON_CLICK_SFX = "/com/example/demo/sfx/ui_sfx/buttonclick.mp3";
    /**
     * The sound manager for handling sound effects and background music.
     */
    private SoundManager soundManager;

    /**
     * The slider for adjusting the volume.
     */
    @FXML
    private Slider volumeSlider;

    /**
     * Initializes the settings screen.
     * <p>
     * This method sets up the volume slider and integrates it with the {@code SoundManager}.
     * It ensures the slider reflects the current background music volume and updates it dynamically.
     * </p>
     *
     * @param stage the primary stage for the settings screen
     */
    public void initialize(Stage stage) {
        this.stage = stage;
        soundManager = SoundManager.getInstance();
        soundManager.loadSFX("button_click",BUTTON_CLICK_SFX);
        // Set up the volume slider
        MediaPlayer backgroundPlayer = soundManager.getBackgroundMusicPlayer();
        if (backgroundPlayer != null) {
            // Set the slider to the current volume
            volumeSlider.setValue(backgroundPlayer.getVolume() * 100);

            // Add listener to update volume in SoundManager
            volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
                soundManager.setBackgroundMusicVolume(newValue.doubleValue() / 100);
            });
        }
    }

    /**
     * Handles the action triggered when the back button is clicked.
     * <p>
     * This method plays a button click sound and navigates back to the main menu.
     * </p>
     *
     * @param event the {@code ActionEvent} triggered by the button click
     * @throws IOException if the main menu FXML file cannot be loaded
     */
    @FXML
    private void onBackButtonClicked(ActionEvent event) throws IOException {
        System.out.println("Returning to the main menu...");

        soundManager.playSFX("button_click");

        MainMenuController mainMenuController = new MainMenuController();
        mainMenuController.showMainMenu(stage);

    }
}
