package app.pageSystem.userPages;

import app.audio.Collections.Playlist;
import app.audio.Files.Song;
import app.pageSystem.BasePage;
import app.users.User;
import app.users.userTypes.NormalUser;
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


    public HomePage() {
        super(null, null);
        this.topFollowedPlaylists = null;
    }

    public HomePage(ArrayList<Song> likedSongs, ArrayList<Playlist> playlists,
                    ArrayList<String> topLikedSongs, ArrayList<String> topFollowedPlaylists) {

        super(likedSongs, playlists);
        this.topLikedSongs = topLikedSongs;
        this.topFollowedPlaylists = topFollowedPlaylists;
    }

    public static ArrayList<String> setTopLikedSongs(ArrayList<Song> songs) {
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

    @Override
    public String printPage(NormalUser normalUser) {
        ArrayList<Song> likedSongs = normalUser.getLikedSongs();
        ArrayList<String> topLikedSongs = HomePage.setTopLikedSongs(likedSongs);
        ArrayList<String> topFollowedPlaylistsName =
                setFollowedPlaylists(normalUser.getFollowedPlaylists());

        normalUser.setHomePage(new HomePage(likedSongs, normalUser.getFollowedPlaylists(),
                                topLikedSongs, topFollowedPlaylistsName));

        return "Liked songs:\n\t" + topLikedSongs + "\n\n" + "Followed playlists:\n\t" +
                topFollowedPlaylistsName;
    }

}

//    @Getter
//    private ArrayList<String> topLikedSongs;
//    @Getter
//    private ArrayList<String> topFollowedPlaylists;
//
//
//    public HomePage() {
//        super(null);
//    }
//    public HomePage(ArrayList<String> topLikedSongs, ArrayList<String> topFollowedPlaylists,
//                    String owner) {
//
//        super(owner);
//        this.topLikedSongs = topLikedSongs;
//        this.topFollowedPlaylists = topFollowedPlaylists;
//    }
//
//
//    public static ArrayList<String> setLikedSongs(ArrayList<Song> songs) {
//        ArrayList<String> songsName = new ArrayList<>();
//        List<Song> sortedSongs = new ArrayList<>(songs);
//        sortedSongs.sort(Comparator.comparingInt(Song::getLikes).reversed());
//        for (Song song : sortedSongs) {
//            songsName.add(song.getName());
//
//            if (songsName.size() == 5) {
//                break;
//            }
//        }
//
//        return songsName;
//    }
//
//
//    public static ArrayList<String> setFollowedPlaylists(ArrayList<Playlist> playlists) {
//        ArrayList<String> followedPlaylists = new ArrayList<>();
//        ArrayList<Playlist> sortedPlaylists = new ArrayList<>(playlists);
//
//        sortedPlaylists.sort(Comparator.comparingInt(Playlist::getTotalLikes).reversed());
//        for (Playlist playlist : sortedPlaylists) {
//            followedPlaylists.add(playlist.getName());
//
//            if (followedPlaylists.size() == 5) {
//                break;
//            }
//        }
//
//        return followedPlaylists;
//    }
//
//
//    @Override
//    public String printPage(NormalUser normalUser) {
//        ArrayList<String> topLikedSongs = HomePage.setLikedSongs(normalUser.getLikedSongs());
//        ArrayList<String> followedPlaylist =
//                HomePage.setFollowedPlaylists(normalUser.getPlaylists());
//
//        normalUser.setHomePage(new HomePage(topLikedSongs, followedPlaylist,
//                normalUser.getUsername()));
//
//        return "Liked songs:\n\t" + topLikedSongs + "\n\n" + "Followed playlists:\n\t" +
//                followedPlaylist;
//    }