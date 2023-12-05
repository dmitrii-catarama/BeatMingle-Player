package app.audio.Collections;


import app.audio.Files.Song;

import java.util.ArrayList;

public class AlbumOutput {
    private String name;
    private ArrayList<String> songs;

    public AlbumOutput(String name, ArrayList<String> songsName) {
        this.name = name;
        this.songs = songsName;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getSongs() {
        return songs;
    }


    public static ArrayList<AlbumOutput> albumOutput(ArrayList<Album> albums) {
        ArrayList<AlbumOutput> albumOutput = new ArrayList<>();

        for(Album album : albums) {
            ArrayList<String> songsName = new ArrayList<>();

            for (Song song : album.getSongs()) {
                songsName.add(song.getName());
            }
            albumOutput.add(new AlbumOutput(album.getName(), songsName));
        }

        return albumOutput;
    }

}
