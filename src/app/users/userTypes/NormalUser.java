package app.users.userTypes;

import app.Admin;
import app.audio.Collections.AudioCollection;
import app.audio.Collections.Playlist;
import app.audio.Collections.PlaylistOutput;
import app.audio.Files.AudioFile;
import app.audio.Files.Song;
import app.audio.LibraryEntry;
import app.pageSystem.creatorsPage.ArtistPage;
import app.pageSystem.creatorsPage.HostPage;
import app.pageSystem.userPages.HomePage;
import app.pageSystem.userPages.LikedContentPage;
import app.player.Player;
import app.player.PlayerStats;
import app.searchBar.Filters;
import app.searchBar.SearchBar;
import app.users.User;
import app.utils.Enums;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class NormalUser extends User {
    @Getter
    private ArrayList<Playlist> playlists;
    @Getter
    private ArrayList<Song> likedSongs;
    @Getter
    private ArrayList<Playlist> followedPlaylists;
    private final Player player;
    @Getter
    private final SearchBar searchBar;
    @Getter
    private boolean lastSearched;
    @Getter
    private Enums.connectionStatus connectionStatus;
    @Getter
    private HomePage homePage;
    @Getter
    private LikedContentPage likedContentPage;
    @Getter
    private String changePage;

    public NormalUser(String username, int age, String city) {
        super(username, age, city);
        super.setType("user");
        playlists = new ArrayList<>();
        likedSongs = new ArrayList<>();
        followedPlaylists = new ArrayList<>();
        player = new Player();
        searchBar = new SearchBar(username);
        lastSearched = false;
        connectionStatus = Enums.connectionStatus.ONLINE;
        changePage = "Home";
    }

    public void setHomePage(HomePage homePage) {
        this.homePage = homePage;
    }

    public void setLikedContentPage(LikedContentPage likedContentPage) {
        this.likedContentPage = likedContentPage;
    }

    public void setChangePage(String changePage) {
        this.changePage = changePage;
    }

    public ArrayList<String> search(Filters filters, String type) {
        searchBar.clearSelection();
        player.stop();

        lastSearched = true;
        ArrayList<String> results = new ArrayList<>();
        List<LibraryEntry> libraryEntries = searchBar.search(filters, type);

        for (LibraryEntry libraryEntry : libraryEntries) {
            results.add(libraryEntry.getName());
        }
        return results;
    }

    public String select(int itemNumber) {
        if (!lastSearched)
            return "Please conduct a search before making a selection.";

        lastSearched = false;

        LibraryEntry selected = searchBar.select(itemNumber);

        if (selected == null)
            return "The selected ID is too high.";

        if (searchBar.getLastSearchType().equals("artist")
            || searchBar.getLastSearchType().equals("host")) {

            changePage = null;
            return "Successfully selected %s's page.".formatted(selected.getName());
        } else {
            return "Successfully selected %s.".formatted(selected.getName());
        }
    }


    public String load() {
        if (searchBar.getLastSelected() == null)
            return "Please select a source before attempting to load.";

        if (!searchBar.getLastSearchType().equals("song") && ((AudioCollection)searchBar.getLastSelected()).getNumberOfTracks() == 0) {
            return "You can't load an empty audio collection!";
        }

        player.setSource(searchBar.getLastSelected(), searchBar.getLastSearchType());
        searchBar.clearSelection();

        player.pause();

        return "Playback loaded successfully.";
    }


    public String playPause() {
        if (player.getCurrentAudioFile() == null)
            return "Please load a source before attempting to pause or resume playback.";

        player.pause();

        if (player.getPaused())
            return "Playback paused successfully.";
        else
            return "Playback resumed successfully.";
    }


    public String repeat() {
        if (player.getCurrentAudioFile() == null)
            return "Please load a source before setting the repeat status.";

        Enums.RepeatMode repeatMode = player.repeat();
        String repeatStatus = "";

        switch(repeatMode) {
            case NO_REPEAT -> repeatStatus = "no repeat";
            case REPEAT_ONCE -> repeatStatus = "repeat once";
            case REPEAT_ALL -> repeatStatus = "repeat all";
            case REPEAT_INFINITE -> repeatStatus = "repeat infinite";
            case REPEAT_CURRENT_SONG -> repeatStatus = "repeat current song";
        }

        return "Repeat mode changed to %s.".formatted(repeatStatus);
    }


    public String shuffle(Integer seed) {
        if (player.getCurrentAudioFile() == null)
            return "Please load a source before using the shuffle function.";

        if (!(player.getType().equals("playlist") || player.getType().equals("album")))
            return "The loaded source is not a playlist or an album.";

        player.shuffle(seed);

        if (player.getShuffle())
            return "Shuffle function activated successfully.";
        return "Shuffle function deactivated successfully.";
    }


    public String forward() {
        if (player.getCurrentAudioFile() == null)
            return "Please load a source before attempting to forward.";

        if (!player.getType().equals("podcast"))
            return "The loaded source is not a podcast.";

        player.skipNext();

        return "Skipped forward successfully.";
    }


    public String backward() {
        if (player.getCurrentAudioFile() == null)
            return "Please select a source before rewinding.";

        if (!player.getType().equals("podcast"))
            return "The loaded source is not a podcast.";

        player.skipPrev();

        return "Rewound successfully.";
    }


    public String like() {
        if (player.getCurrentAudioFile() == null)
            return "Please load a source before liking or unliking.";

        if (!player.getType().equals("song") && !player.getType().equals("playlist"))
            return "Loaded source is not a song.";

        Song song = (Song) player.getCurrentAudioFile();

        if (likedSongs.contains(song)) {
            likedSongs.remove(song);
            song.dislike();

            return "Unlike registered successfully.";
        }

        likedSongs.add(song);
        song.like();
        return "Like registered successfully.";
    }


    public String next() {
        if (player.getCurrentAudioFile() == null)
            return "Please load a source before skipping to the next track.";

        player.next();

        if (player.getCurrentAudioFile() == null)
            return "Please load a source before skipping to the next track.";

        return "Skipped to next track successfully. The current track is %s.".formatted(player.getCurrentAudioFile().getName());
    }


    public String prev() {
        if (player.getCurrentAudioFile() == null)
            return "Please load a source before returning to the previous track.";

        player.prev();

        return "Returned to previous track successfully. The current track is %s.".formatted(player.getCurrentAudioFile().getName());
    }


    public String createPlaylist(String name, int timestamp) {
        if (playlists.stream().anyMatch(playlist -> playlist.getName().equals(name)))
            return "A playlist with the same name already exists.";

        playlists.add(new Playlist(name, getUsername(), timestamp));

        return "Playlist created successfully.";
    }


    public String addRemoveInPlaylist(int Id) {
        if (player.getCurrentAudioFile() == null)
            return "Please load a source before adding to or removing from the playlist.";

        if (player.getType().equals("podcast"))
            return "The loaded source is not a song.";

        if (Id > playlists.size())
            return "The specified playlist does not exist.";

        Playlist playlist = playlists.get(Id - 1);

        if (playlist.containsSong((Song)player.getCurrentAudioFile())) {
            playlist.removeSong((Song)player.getCurrentAudioFile());
            return "Successfully removed from playlist.";
        }

        playlist.addSong((Song)player.getCurrentAudioFile());
        return "Successfully added to playlist.";
    }


    public String switchPlaylistVisibility(Integer playlistId) {
        if (playlistId > playlists.size())
            return "The specified playlist ID is too high.";

        Playlist playlist = playlists.get(playlistId - 1);
        playlist.switchVisibility();

        if (playlist.getVisibility() == Enums.Visibility.PUBLIC) {
            return "Visibility status updated successfully to public.";
        }

        return "Visibility status updated successfully to private.";
    }


    public String switchUserConnectionStatus() {
        if (super.getType().equals("artist") || super.getType().equals("host")) {
            return getUsername() + " is not a normal user.";
        }

        if (connectionStatus == Enums.connectionStatus.ONLINE) {
            connectionStatus = Enums.connectionStatus.OFFLINE;
        } else {
            connectionStatus = Enums.connectionStatus.ONLINE;
        }

        return getUsername() + " has changed status successfully.";
    }


    public ArrayList<PlaylistOutput> showPlaylists() {
        ArrayList<PlaylistOutput> playlistOutputs = new ArrayList<>();
        for (Playlist playlist : playlists) {
            playlistOutputs.add(new PlaylistOutput(playlist));
        }

        return playlistOutputs;
    }


    public String follow() {
        LibraryEntry selection = searchBar.getLastSelected();
        String type = searchBar.getLastSearchType();

        if (selection == null)
            return "Please select a source before following or unfollowing.";

        if (!type.equals("playlist"))
            return "The selected source is not a playlist.";

        Playlist playlist = (Playlist)selection;

        if (playlist.getOwner().equals(getUsername()))
            return "You cannot follow or unfollow your own playlist.";

        if (followedPlaylists.contains(playlist)) {
            followedPlaylists.remove(playlist);
            playlist.decreaseFollowers();

            return "Playlist unfollowed successfully.";
        }

        followedPlaylists.add(playlist);
        playlist.increaseFollowers();

        return "Playlist followed successfully.";
    }


    public PlayerStats getPlayerStats() {
        return player.getStats();
    }


    public ArrayList<String> showPreferredSongs() {
        ArrayList<String> results = new ArrayList<>();
        for (AudioFile audioFile : likedSongs) {
            results.add(audioFile.getName());
        }

        return results;
    }


    public String getPreferredGenre() {
        String[] genres = {"pop", "rock", "rap"};
        int[] counts = new int[genres.length];
        int mostLikedIndex = -1;
        int mostLikedCount = 0;

        for (Song song : likedSongs) {
            for (int i = 0; i < genres.length; i++) {
                if (song.getGenre().equals(genres[i])) {
                    counts[i]++;
                    if (counts[i] > mostLikedCount) {
                        mostLikedCount = counts[i];
                        mostLikedIndex = i;
                    }
                    break;
                }
            }
        }

        String preferredGenre = mostLikedIndex != -1 ? genres[mostLikedIndex] : "unknown";
        return "This user's preferred genre is %s.".formatted(preferredGenre);
    }

//    if (searchBar.getSelectedPageType() == null) {
//        HomePage homePage = new HomePage();
//        return homePage.printPage(this);
//    }
    public String printCurrentPage() {
        if (changePage != null && changePage.equals("Home")) {
            HomePage homePage = new HomePage();
            return homePage.printPage(this);
        } else if (changePage != null && changePage.equals("LikedContent")) {
            LikedContentPage likedContentPage = new LikedContentPage();
            return likedContentPage.printPage(this);
        } else if (searchBar.getSelectedPageType().equals("artist")) {
            String artistName = searchBar.getSelectedUserPage().toString();
            return ArtistPage.printPage(artistName);
        } else if (searchBar.getSelectedPageType().equals("host")) {
            String hostName = searchBar.getSelectedUserPage().toString();
            return HostPage.printPage(hostName);
        }

        return null;
    }

    public String changePage(String nextPage) {
        if (!nextPage.equals("Home") && !nextPage.equals("LikedContent")) {
            return this.getUsername() + " is trying to access a non-existent page.";
        }
        changePage = nextPage;

        return this.getUsername() + " accessed " + nextPage + " successfully.";
    }

    public void simulateTime(int time) {
        if (connectionStatus == Enums.connectionStatus.ONLINE) {
            player.simulatePlayer(time);
        }
    }

    public void deleteNormalUserData() {
        List<User> users = Admin.getUsers();

        // stergem pentru fiecare userNormal din followedPlaylists playlisturile userului
        // pe care vrem sa il eliminam
        for (User user : users) {
            if (user.getType().equals("user")) {
                for (Playlist playlist : this.getPlaylists()) {
                    ((NormalUser)user).unfollowPlaylist(playlist);
                }
            }
        }
        this.playlists.clear();

        for (Song song : this.likedSongs) {
            song.dislike();
        }
        this.likedSongs.clear();

        for (Playlist playlist : this.followedPlaylists) {
            playlist.decreaseFollowers();
        }
        this.followedPlaylists.clear();
    }

    public void unfollowPlaylist(Playlist playlist) {
        this.followedPlaylists.remove(playlist);
    }

    /**
     * Delete the song from user Liked Songs
     * @param song song to be deleted from Liked Songs
     */
    public void deleteLikedSong(Song song) {
        this.likedSongs.remove(song);
    }

    public String getCurrentAudioFileName() {
        if (player.getCurrentAudioFile() != null) {
            return player.getCurrentAudioFile().getName();
        }
        return null;

    }

    public String getCurrentAudioCollectionName() {
        if (player.getCurrentAudioCollection() != null) {
            return player.getCurrentAudioCollection().getName();
        }
        return null;
    }

}
