package Entry;

import music.MusicUtils;

public class Entry {

    public static void main(String[] args) {
        // Load the MP3 file from the resources folder
        String filePath = Entry.class.getResource("/music/Sakura-Girl-Daisy-chosic.com_.mp3").getFile();
        MusicUtils music = new MusicUtils(filePath);
        music.playCurrentSong();
        
    }
}
