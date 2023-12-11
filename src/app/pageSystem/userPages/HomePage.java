package app.pageSystem.userPages;

import app.audio.Collections.Playlist;
import app.audio.Files.Song;
import app.pageSystem.BasePage;
import app.users.userTypes.NormalUser;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class HomePage extends BasePage {
    @Getter
    private ArrayList<String> topLikedSongs;
    @Getter
    private ArrayList<String> topFollowedPlaylists;
    private static final int LIMIT = 5;


    public HomePage() {
        super(null, null);
        this.topFollowedPlaylists = null;
    }

    public HomePage(final ArrayList<Song> likedSongs, final ArrayList<Playlist> playlists,
                    final ArrayList<String> topLikedSongs,
                    final ArrayList<String> topFollowedPlaylists) {

        super(likedSongs, playlists);
        this.topLikedSongs = topLikedSongs;
        this.topFollowedPlaylists = topFollowedPlaylists;
    }

    /**
     * get topLikedSongs
     * @param songs all user liked songs
     * @return topLikedSongs list
     */
    public static ArrayList<String> setTopLikedSongs(final ArrayList<Song> songs) {
        ArrayList<String> songsName = new ArrayList<>();
        List<Song> sortedSongs = new ArrayList<>(songs);
        sortedSongs.sort(Comparator.comparingInt(Song::getLikes).reversed());
        for (Song song : sortedSongs) {
            songsName.add(song.getName());

            if (songsName.size() == LIMIT) {
                break;
            }
        }

        return songsName;
    }

    /**
     * get topFollowedPlaylists
     * @param playlists all user playlists
     * @return topFollowedPlaylists list
     */
    public static ArrayList<String> setFollowedPlaylists(final ArrayList<Playlist> playlists) {
        ArrayList<String> followedPlaylists = new ArrayList<>();
        ArrayList<Playlist> sortedPlaylists = new ArrayList<>(playlists);

        sortedPlaylists.sort(Comparator.comparingInt(Playlist::getTotalLikes).reversed());
        for (Playlist playlist : sortedPlaylists) {
            followedPlaylists.add(playlist.getName());

            if (followedPlaylists.size() == LIMIT) {
                break;
            }
        }

        return followedPlaylists;
    }

    /**
     * Print HomePage of the user
     * @param normalUser the user for whom the page will be printed
     * @return content of the page in a string
     */
    @Override
    public String printPage(final NormalUser normalUser) {
        ArrayList<Song> likedSongs = normalUser.getLikedSongs();
        ArrayList<String> topLikedSongsName = HomePage.setTopLikedSongs(likedSongs);
        ArrayList<String> topFollowedPlaylistsName =
                setFollowedPlaylists(normalUser.getFollowedPlaylists());

        normalUser.setHomePage(new HomePage(likedSongs, normalUser.getFollowedPlaylists(),
                                topLikedSongsName, topFollowedPlaylistsName));

        return "Liked songs:\n\t" + topLikedSongsName + "\n\n" + "Followed playlists:\n\t"
                + topFollowedPlaylistsName;
    }

}
