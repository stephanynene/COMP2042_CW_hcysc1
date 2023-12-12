package brickGame;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.HashMap;
import java.util.Map;
/**
 * The Sounds class manages game sound effects and background music.
 *
 * It provides methods to play various sound effects such as hitting blocks, gaining hearts, and game over sounds.
 * Additionally, it handles background music, allowing for playback control, muting, and volume adjustment.
 *
 */
public class Sounds {

    private static final Map<String, AudioClip> audioClips = new HashMap<>();
    private static MediaPlayer backgroundMusicPlayer;


    static {

        audioClips.put("hit-block-sound", new AudioClip(Sounds.class.getResource("/hitblock.wav").toExternalForm()));
        audioClips.put("game-over-sound", new AudioClip(Sounds.class.getResource("/game-over.wav").toExternalForm()));
        audioClips.put("gain-heart-sound", new AudioClip(Sounds.class.getResource("/gain-heart.wav").toExternalForm()));
        audioClips.put("bonus-sound", new AudioClip(Sounds.class.getResource("/bonus.wav").toExternalForm()));
        audioClips.put("bounce-sound", new AudioClip(Sounds.class.getResource("/bounce-sound.wav").toExternalForm()));
        audioClips.put("star-block-sound", new AudioClip(Sounds.class.getResource("/starblock.wav").toExternalForm()));
        audioClips.put("lose-heart-sound", new AudioClip(Sounds.class.getResource("/lose-heart.wav").toExternalForm()));
        audioClips.put("next-level-sound", new AudioClip(Sounds.class.getResource("/next-level.wav").toExternalForm()));
        audioClips.put("sturdy-sound", new AudioClip(Sounds.class.getResource("/sturdy-sound.wav").toExternalForm()));
        audioClips.put("thunder-sound", new AudioClip(Sounds.class.getResource("/thunder-sound.wav").toExternalForm()));


        Media backgroundMusicMedia = new Media(Sounds.class.getResource("/background-music.mp3").toExternalForm());
        backgroundMusicPlayer = new MediaPlayer(backgroundMusicMedia);
        backgroundMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Loop indefinitely

    }


    /**
     * @param soundKey
     * plays the sound according to the name set
     */
    public static void playSound(String soundKey) {
        AudioClip audioClip = audioClips.get(soundKey);
        if (audioClip != null) {
            audioClip.play();
        }
    }

    /**
     * plays the bounce sound using playsound method
     */
    public static void playBounceSound(){
        playSound("bounce-sound");
    }

    /**
     * Stops the bounce sound using stop sound method
     */
    public static void stopBounceSound(){
        stopSound("bounce-sound");
    }

    /**
     * @param soundKey
     * Stops the sound according to its name
     */
    public static void stopSound(String soundKey) {
        AudioClip audioClip = audioClips.get(soundKey);
        if (audioClip != null) {
            audioClip.stop();
        }
    }

    /**
     * Plays the background music.
     */
    public static void playBackgroundMusic() {
        backgroundMusicPlayer.play();
    }

    /**
     * Stops the background music.
     */
    public static void stopBackgroundMusic() {
        backgroundMusicPlayer.stop();
    }

    /**
     * Mutes the background music.
     */
    public static void muteBackgroundMusic() {
        backgroundMusicPlayer.setMute(true);
    }

    /**
     * Unmutes the background music.
     */
    public static void unmuteBackgroundMusic() {
        backgroundMusicPlayer.setMute(false);
    }

    /**
     * Sets the volume for the background music.
     *
     * @param volume The volume level (0.0 to 1.0).
     */
    public static void setBackgroundMusicVolume(double volume) {
        backgroundMusicPlayer.setVolume(volume);
    }

}
