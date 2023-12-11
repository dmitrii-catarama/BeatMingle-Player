package app.pageSystem;

import app.audio.Collections.Playlist;
import app.audio.Files.Song;
import app.users.userTypes.NormalUser;
import lombok.Getter;

import java.util.ArrayList;


public abstract class BasePage {
    @Getter
    private ArrayList<Song> likedSongs;
    @Getter
    private ArrayList<Playlist> followedPlaylists;

    public BasePage(final ArrayList<Song> likedSongs, final ArrayList<Playlist> followedPlaylists) {
        this.likedSongs = likedSongs;
        this.followedPlaylists = followedPlaylists;
    }


    /**
     * Update the followedPlaylists field.
     * @param newFollowedPlaylists followedPlaylists
     */
    public void setPlaylists(final ArrayList<Playlist> newFollowedPlaylists) {
        this.followedPlaylists = newFollowedPlaylists;
    }

    /**
     * Update the setLikedSongs field.
     * @param likedSongs likedSongs
     */
    public void setLikedSongs(final ArrayList<Song> likedSongs) {
        this.likedSongs = likedSongs;
    }

    /**
     * Method that print the specific for User pages
     * @param normalUser the user for whom the page will be printed
     * @return the content of the page in a string
     */
    public abstract String printPage(NormalUser normalUser);

}
