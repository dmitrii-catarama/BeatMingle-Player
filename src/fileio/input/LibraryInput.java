package fileio.input;

import checker.CheckerConstants;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public final class LibraryInput {
    private static LibraryInput instance = null;
    private ArrayList<SongInput> songs;
    private ArrayList<PodcastInput> podcasts;
    private ArrayList<UserInput> users;

    // Singleton lazy instantiation
    private LibraryInput() {
    }


    public static LibraryInput getInstance() throws IOException {
        if (instance == null) {
            ObjectMapper instanceMapper = new ObjectMapper();

            instance = instanceMapper.readValue(new File(CheckerConstants.TESTS_PATH
                            + "library/library.json"),
                    LibraryInput.class);;
        }
        return instance;
    }

    public ArrayList<SongInput> getSongs() {
        return songs;
    }

    public void setSongs(final ArrayList<SongInput> songs) {
        this.songs = songs;
    }

    public ArrayList<PodcastInput> getPodcasts() {
        return podcasts;
    }

    public void setPodcasts(final ArrayList<PodcastInput> podcasts) {
        this.podcasts = podcasts;
    }

    public ArrayList<UserInput> getUsers() {
        return users;
    }

    public void setUsers(final ArrayList<UserInput> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "LibraryInput{"
                + "songs=" + songs
                + ", podcasts=" + podcasts
                + ", users=" + users
                + '}';
    }
}
