package app.pageSystem.userPages;

import app.audio.Collections.Playlist;
import app.audio.Files.Song;
import app.pageSystem.BasePage;
import app.users.userTypes.NormalUser;

import java.util.ArrayList;


public class LikedContentPage extends BasePage {

    public LikedContentPage() {
        super(null, null);
    }

    public LikedContentPage(final ArrayList<Song> likedSongs,
                            final ArrayList<Playlist> followedPlaylists) {
        super(likedSongs, followedPlaylists);
    }

    /**
     * Print the user LikedContentPage
     * @param normalUser the user for whom the page will be printed
     * @return the content of the page in a string
     */
    @Override
    public String printPage(final NormalUser normalUser) {
        ArrayList<Song> topLikedSongs = normalUser.getLikedSongs();
        ArrayList<Playlist> followedPlaylist = normalUser.getFollowedPlaylists();

        normalUser.setLikedContentPage(new LikedContentPage(topLikedSongs, followedPlaylist));

        StringBuilder songsPrint = new StringBuilder();
        StringBuilder playlistPrint = new StringBuilder();

        songsPrint.append("[");

        for (Song song : topLikedSongs) {
            songsPrint.append(song.getName()).append(" - ").append(song.getArtist());

            if (!(topLikedSongs.indexOf(song) >= topLikedSongs.size() - 1)) {
                songsPrint.append(", ");
            }
        }

        songsPrint.append("]");
        playlistPrint.append("[");

        for (Playlist playlist : followedPlaylist) {
            playlistPrint.append(playlist.getName()).append(" - ").append(playlist.getOwner());

            if (!(followedPlaylist.indexOf(playlist) >= followedPlaylist.size() - 1)) {
                playlistPrint.append(", ");
            }
        }

        playlistPrint.append("]");


        return "Liked songs:\n\t" + songsPrint + "\n\n" + "Followed playlists:\n\t"
                + playlistPrint;
    }

}
