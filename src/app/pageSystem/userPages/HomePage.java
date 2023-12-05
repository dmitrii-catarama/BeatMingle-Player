package app.pageSystem.userPages;

import app.audio.Collections.Playlist;
import app.audio.Files.Song;
import app.pageSystem.BasePage;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class HomePage extends BasePage {
    @Getter
    private ArrayList<String> topLikedSongs;
    @Getter
    private ArrayList<String> topFollowedPlaylists;

    public HomePage(ArrayList<String> topLikedSongs, ArrayList<String> topFollowedPlaylists,
                    String owner) {

        super(owner);
        this.topLikedSongs = topLikedSongs;
        this.topFollowedPlaylists = topFollowedPlaylists;
    }


    public static ArrayList<String> setLikedSongs(ArrayList<Song> songs) {
        ArrayList<String> songsName = new ArrayList<>();
        List<Song> sortedSongs = new ArrayList<>(songs);
        sortedSongs.sort(Comparator.comparingInt(Song::getLikes).reversed());
        for (Song song : sortedSongs) {
            songsName.add(song.getName());

            if (songsName.size() == 5) {
                break;
            }
        }

        return songsName;
    }


    public static ArrayList<String> setFollowedPlaylists(ArrayList<Playlist> playlists) {
        ArrayList<String> followedPlaylists = new ArrayList<>();
        ArrayList<Playlist> sortedPlaylists = new ArrayList<>(playlists);

        sortedPlaylists.sort(Comparator.comparingInt(Playlist::getTotalLikes).reversed());
        for (Playlist playlist : sortedPlaylists) {
            followedPlaylists.add(playlist.getName());

            if (followedPlaylists.size() == 5) {
                break;
            }
        }

        return followedPlaylists;
    }

}