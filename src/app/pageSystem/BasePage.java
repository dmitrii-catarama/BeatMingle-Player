package app.pageSystem;

import app.audio.Collections.Playlist;
import app.audio.Files.Song;
import app.player.Player;
import app.users.User;
import app.users.userTypes.NormalUser;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public abstract class BasePage {
    @Getter
    private ArrayList<Song> likedSongs;
    @Getter
    private ArrayList<Playlist> followedPlaylists;

    public BasePage(ArrayList<Song> likedSongs, ArrayList<Playlist> followedPlaylists) {
        this.likedSongs = likedSongs;
        this.followedPlaylists = followedPlaylists;
    }

    public static ArrayList<String> setLikedSongsName(ArrayList<Song> likedSongs) {
        ArrayList<String> songsName = new ArrayList<>();

        for(Song song : likedSongs) {
            songsName.add(song.getName());
        }

        return songsName;
    }

    public void setPlaylists(ArrayList<Playlist> followedPlaylists) {
        this.followedPlaylists = followedPlaylists;
    }

    public abstract String printPage(NormalUser normalUser);

}

//    @Getter
//    private String owner;
//
//    public BasePage(String owner) {
//        this.owner = owner;
//    }
//
//    public abstract String printPage(NormalUser normalUser);