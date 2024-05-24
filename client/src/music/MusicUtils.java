package music;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class MusicUtils {
    private Player player;
    private File song;
    private Thread playbackThread;

    public MusicUtils(String fileName) {
        this.song = new File(fileName);
    }

    public void playCurrentSong() {
        stopCurrentSong();
        playbackThread = new Thread(() -> {
            try (FileInputStream fis = new FileInputStream(song)) {
                player = new Player(fis);
                player.play();
            } catch (JavaLayerException | IOException ex) {
                ex.printStackTrace();
            }
        });
        playbackThread.start();
    }

    public void stopCurrentSong() {
        if (player != null) {
            player.close();
        }
        if (playbackThread != null && playbackThread.isAlive()) {
            playbackThread.interrupt();
        }
    }

    public void setSong(String fileName) {
        this.song = new File(fileName);
    }
}
