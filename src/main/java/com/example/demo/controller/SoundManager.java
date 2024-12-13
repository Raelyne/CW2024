package com.example.demo.controller;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * The {@code SoundManager} class is newly added, managing all sound-related functionality in the application.
 * <p>
 * This includes playing sound effects (SFX), managing background music, and handling mute states.
 * It uses the {@code Media} and {@code MediaPlayer} classes from JavaFX to manage audio resources.
 * Incorporates the Singleton Design Pattern.
 * </p>
 */
public class SoundManager {
    /**
     * Singleton instance of the {@code SoundManager}.
     */
    private static SoundManager instance;
    /**
     * Map storing sound effects, keyed by their name.
     */
    private final Map<String, Media> soundEffects;
    /**
     * MediaPlayer instance for background music.
     */
    private MediaPlayer backgroundMusicPlayer;
    /**
     * Indicates whether the sound is muted.
     */
    private boolean muted = false;

    /**
     * Private constructor to enforce singleton pattern.
     */
    private SoundManager() {
        soundEffects = new HashMap<>();
    }

    /**
     * Retrieves the singleton instance of the {@code SoundManager}.
     *
     * @return the singleton {@code SoundManager} instance
     */
    public static synchronized SoundManager getInstance() {
        if (instance == null) {
            instance = new SoundManager();
        }
        return instance;
    }

    /**
     * Loads a sound effect into the manager, so that it can be called anytime.
     *
     * @param name     the name to associate with the sound effect
     * @param filePath the file path to the sound effect
     */
    public void loadSFX(String name, String filePath) {
        Media media = new Media(Objects.requireNonNull(getClass().getResource(filePath)).toExternalForm());
        soundEffects.put(name, media); // Store Media instead of MediaPlayer
    }

    /**
     * Plays a sound effect by its name.
     *
     * @param soundName the name of the sound effect to play
     */
    public void playSFX(String soundName) {
        if (!muted && soundEffects.containsKey(soundName)) {
            Media media = soundEffects.get(soundName);
            MediaPlayer sfxPlayer = new MediaPlayer(media); // Create a new MediaPlayer for each playback
            sfxPlayer.setOnEndOfMedia(sfxPlayer::dispose); // Dispose of MediaPlayer after playback
            sfxPlayer.play();
        } else {
            System.out.println("Sound not found or muted: " + soundName); // Debugging line
        }
    }

    /**
     * Stops the background music if it is playing.
     */
    public void stopBackgroundMusic() {
        if (backgroundMusicPlayer != null) {
            backgroundMusicPlayer.stop();
        }
    }

    /**
     * Plays background music from the specified file path.
     * If the same music is already playing, this method does nothing.
     *
     * @param filePath the file path to the background music
     */
    public void playBackgroundMusic(String filePath) {
        if (backgroundMusicPlayer == null || !backgroundMusicPlayer.getMedia().getSource().equals(Objects.requireNonNull(getClass().getResource(filePath)).toExternalForm())) {
            Media media = new Media(Objects.requireNonNull(getClass().getResource(filePath)).toExternalForm());
            backgroundMusicPlayer = new MediaPlayer(media);
            backgroundMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Loop the new music
        }
        if (!muted) {
            backgroundMusicPlayer.play();
        }
    }

    /**
     * Retrieves the current {@code MediaPlayer} instance for background music.
     *
     * @return the {@code MediaPlayer} instance for background music, or {@code null} if none is set
     */
    public MediaPlayer getBackgroundMusicPlayer() {
        return backgroundMusicPlayer;
    }

    /**
     * Sets the volume of the background music.
     *
     * @param volume the new volume level (0.0 to 1.0)
     */
    public void setBackgroundMusicVolume(double volume) {
        if (backgroundMusicPlayer != null) {
            backgroundMusicPlayer.setVolume(volume);
        }
    }

    /**
     * Toggles the mute state of the sound manager.
     * <p>
     * When muted, all sound effects and background music are silenced.
     * Note: stopAllSFX() currently is not fully implemented.
     * </p>
     */
    public void toggleMute() {
        muted = !muted;
        if (muted) {
            stopAllSFX();
            if (backgroundMusicPlayer != null) {
                backgroundMusicPlayer.setMute(true);
            }
        } else {
            if (backgroundMusicPlayer != null) {
                backgroundMusicPlayer.setMute(false);
            }
        }
    }

    /**
     * Stops all currently playing sound effects.
     * <p>
     * Note: This method is yet be implemented, but can be to keep track of active sound effect players if needed.
     * </p>
     */
    public void stopAllSFX() {
        // Optional: Implement if you want to stop all currently playing SFX
    }
}
