package brickGame;
import javafx.scene.media.AudioClip;

import java.util.HashMap;
import java.util.Map;

public class Sounds {

    private static final Map<String, AudioClip> audioClips = new HashMap<>();

    static {

        audioClips.put("next-level", new AudioClip(Sounds.class.getResource("/").toExternalForm()));
        audioClips.put("gain-heart", new AudioClip(Sounds.class.getResource("/gain-heart.wav").toExternalForm()));

    }

    public static void playSound(String soundKey) {
        AudioClip audioClip = audioClips.get(soundKey);
        if (audioClip != null) {
            audioClip.play();
        }
    }

}
