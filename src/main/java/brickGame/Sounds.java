package brickGame;
import javafx.scene.media.AudioClip;

import java.util.HashMap;
import java.util.Map;

public class Sounds {

    private static final Map<String, AudioClip> audioClips = new HashMap<>();

    static {

        audioClips.put("hit-block-sound", new AudioClip(Sounds.class.getResource("/hitblock.wav").toExternalForm()));
        audioClips.put("game-over-sound", new AudioClip(Sounds.class.getResource("/game-over.wav").toExternalForm()));
        audioClips.put("gain-heart-sound", new AudioClip(Sounds.class.getResource("/gain-heart.wav").toExternalForm()));
        audioClips.put("bonus-sound", new AudioClip(Sounds.class.getResource("/bonus.wav").toExternalForm()));
        audioClips.put("breakpaddle-hit-sound", new AudioClip(Sounds.class.getResource("/breakpaddlehit.wav").toExternalForm()));
        audioClips.put("star-block-sound", new AudioClip(Sounds.class.getResource("/starblock.wav").toExternalForm()));
        audioClips.put("lose-heart-sound", new AudioClip(Sounds.class.getResource("/lose-heart.wav").toExternalForm()));
        audioClips.put("next-level-sound", new AudioClip(Sounds.class.getResource("/next-level.wav").toExternalForm()));

    }

    public static void playSound(String soundKey) {
        AudioClip audioClip = audioClips.get(soundKey);
        if (audioClip != null) {
            audioClip.play();
        }
    }

    public static void stopSound(String soundKey) {
        AudioClip audioClip = audioClips.get(soundKey);
        if (audioClip != null) {
            audioClip.stop();
        }
    }

}
