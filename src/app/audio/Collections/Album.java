package app.audio.Collections;

import app.audio.Files.AudioFile;
import app.audio.Files.Song;

import java.util.ArrayList;

public class Album extends AudioCollection {

    private Integer releaseYear;
    private String description;
    private ArrayList<Song> songs;


    public Album(final String name) {
        super(name, null);
        this.songs = new ArrayList<>();
    }

    public Album(final String name, final String owner, final Integer releaseYear,
                 final String description, final ArrayList<Song> songs) {

        super(name, owner);
        this.releaseYear = releaseYear;
        this.description = description;
        this.songs = songs;
    }


    /**
     * Gets the release year of the album.
     * @return the release year
     */
    public Integer getReleaseYear() {
        return releaseYear;
    }

    /**
     * Gets the description of the album.
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets songs of the album.
     * @return songs
     */
    public ArrayList<Song> getSongs() {
        return songs;
    }

    /**
     * Set songs of the album
     * @param songs songs
     */
    public void setSongs(final ArrayList<Song> songs) {
        this.songs = songs;
    }

    /**
     * matches description of an album.
     * @param albumDescription the description of album
     * @return boolean
     */
    @Override
    public boolean matchesDescriptions(final String albumDescription) {
        return this.getDescription().equalsIgnoreCase(albumDescription);
    }

    /**
     * Gets number of tracks of the album.
     * @return number of tracks
     */
    @Override
    public int getNumberOfTracks() {
        return songs.size();
    }

    /**
     * Gets the song(track) of the album by index.
     * @param index the index
     * @return the song
     */
    @Override
    public AudioFile getTrackByIndex(final int index) {
        return songs.get(index);
    }

    /**
     * Gets album likes by its songs likes
     * @return album likes
     */
    public Integer getLikes() {
        Integer likes = 0;

        for (Song song : songs) {
            likes += song.getLikes();
        }

        return likes;
    }

}
