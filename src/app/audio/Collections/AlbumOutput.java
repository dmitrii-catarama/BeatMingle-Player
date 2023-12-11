package app.audio.Collections;

import app.audio.Files.Song;

import java.util.ArrayList;

public class AlbumOutput {
    private String name;
    private ArrayList<String> songs;

    public AlbumOutput(final String name, final ArrayList<String> songsName) {
        this.name = name;
        this.songs = songsName;
    }

    /**
     * Gets name of the album.
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets songs names of the album.
     * @return songs names
     */
    public ArrayList<String> getSongs() {
        return songs;
    }

    /**
     * Transform albums to the output format.
     * @param albums albums to be transformed
     * @return albums output
     */
    public static ArrayList<AlbumOutput> albumOutput(final ArrayList<Album> albums) {
        ArrayList<AlbumOutput> albumOutput = new ArrayList<>();

        for (Album album : albums) {
            ArrayList<String> songsName = new ArrayList<>();

            for (Song song : album.getSongs()) {
                songsName.add(song.getName());
            }
            albumOutput.add(new AlbumOutput(album.getName(), songsName));
        }

        return albumOutput;
    }
}
