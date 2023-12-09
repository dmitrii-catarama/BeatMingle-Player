package app.pageSystem.userPages;


import app.audio.Collections.Playlist;
import app.audio.Files.Song;
import app.pageSystem.BasePage;
import app.users.User;
import app.users.userTypes.NormalUser;
import lombok.Getter;

import java.util.ArrayList;

public class LikedContentPage extends BasePage {

    public LikedContentPage() {
        super(null, null);
    }

    public LikedContentPage(ArrayList<Song> likedSongs, ArrayList<Playlist> followedPlaylists) {
        super(likedSongs, followedPlaylists);
    }

    @Override
    public String printPage(NormalUser normalUser) {
        ArrayList<Song> topLikedSongs = normalUser.getLikedSongs();
        ArrayList<Playlist> followedPlaylist = normalUser.getFollowedPlaylists();

        normalUser.setLikedContentPage(new LikedContentPage(topLikedSongs, followedPlaylist));

        StringBuilder songsPrint = new StringBuilder();
        StringBuilder playlistPrint = new StringBuilder();

        songsPrint.append("[");

        for (Song song : topLikedSongs) {
            songsPrint.append(song.getName()).append(" - ").append(song.getArtist());

            if (!(topLikedSongs.indexOf(song) == topLikedSongs.size() - 1)) {
                playlistPrint.append(",");
            }
        }

        songsPrint.append("]");
        playlistPrint.append("[");

        for (Playlist playlist : followedPlaylist) {
            playlistPrint.append(playlist.getName()).append(" - ").append(playlist.getOwner());

            if (!(followedPlaylist.indexOf(playlist) == followedPlaylist.size() - 1)) {
                playlistPrint.append(",");
            }
        }

        playlistPrint.append("]");


        return "Liked songs:\n\t" + songsPrint + "\n\n" + "Followed playlists:\n\t" +
                playlistPrint;
    }

}

//    @Getter
//    private ArrayList<String> likedSongs;
//    @Getter
//    private ArrayList<Playlist> followedPlaylists;
//
//    public LikedContentPage(String owner, ArrayList<String> likedSongs,
//                            ArrayList<Playlist> followedPlaylists) {
//
//        super(owner);
//        this.likedSongs = likedSongs;
//        this.followedPlaylists = followedPlaylists;
//    }
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
//    @Override
//    public String printPage(NormalUser normalUser) {
//        ArrayList<String> topLikedSongs = LikedContentPage.setLikedSongs(normalUser.getLikedSongs());
//        ArrayList<Playlist> followedPlaylist = normalUser.getPlaylists();
//
//        normalUser.setLikedContentPage(new LikedContentPage(normalUser.getUsername(),
//                topLikedSongs, followedPlaylist));
//
//        StringBuilder playlistPrint = new StringBuilder();
//        playlistPrint.append(":\n\t[");
//
//
//        return "Liked songs:\n\t" + topLikedSongs + "\n\n" + "Followed playlists" +
//                followedPlaylist;
//    }