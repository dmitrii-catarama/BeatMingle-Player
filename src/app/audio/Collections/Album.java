package app.audio.Collections;

import app.audio.Files.AudioFile;
import app.audio.Files.Song;

import java.util.ArrayList;

public class Album extends AudioCollection{

    private Integer releaseYear;
    private String description;
    private ArrayList<Song> songs;


    public Album(String name) {
        super(name, null);
        this.songs = new ArrayList<>();
    }

    public Album(String name, String owner, Integer releaseYear, String description,
                 ArrayList<Song> songs) {

        super(name, owner);
        this.releaseYear = releaseYear;
        this.description = description;
        this.songs = songs;
    }


    public Integer getReleaseYear() {
        return releaseYear;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<Song> getSongs() {
        return songs;
    }

    public void setSongs(ArrayList<Song> songs) {
        this.songs = songs;
    }


    @Override
    public int getNumberOfTracks() {
        return songs.size();
    }


    @Override
    public AudioFile getTrackByIndex(int index) {
        return songs.get(index);
    }



}
