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
import fileio.input.CommandInput;
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
    private Enums.ConnectionStatus connectionStatus;
    @Getter
    private HomePage homePage;
    @Getter
    private LikedContentPage likedContentPage;
    @Getter
    private String changePage;

    public NormalUser(final String username, final int age, final String city) {
        super(username, age, city);
        super.setType("user");
        playlists = new ArrayList<>();
        likedSongs = new ArrayList<>();
        followedPlaylists = new ArrayList<>();
        player = new Player();
        searchBar = new SearchBar(username);
        lastSearched = false;
        connectionStatus = Enums.ConnectionStatus.ONLINE;
        changePage = "Home";
    }

    /**
     * Sets the home page for the normal user.
     * @param homePage home page to be set
     */
    public void setHomePage(final HomePage homePage) {
        this.homePage = homePage;
    }

    /**
     * Sets the liked content page for the normal user.
     * @param likedContentPage liked content page to be set
     */
    public void setLikedContentPage(final LikedContentPage likedContentPage) {
        this.likedContentPage = likedContentPage;
    }

    /**
     * Sets the change page of the normal user.
     * @param changePage change page
     */
    public void setChangePage(final String changePage) {
        this.changePage = changePage;
    }

    /**
     * Search command.
     * @param filters the filters to filter object
     * @param type type of the object to be filtered
     * @return results of search
     */
    public ArrayList<String> search(final Filters filters, final String type) {
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

    /**
     * Select command.
     * @param itemNumber the index of the item to be selected
     * @return the message of the command
     */
    public String select(final int itemNumber) {
        if (!lastSearched) {
            return "Please conduct a search before making a selection.";
        }

        lastSearched = false;

        LibraryEntry selected = searchBar.select(itemNumber);

        if (selected == null) {
            return "The selected ID is too high.";
        }

        if (searchBar.getLastSearchType().equals("artist")
            || searchBar.getLastSearchType().equals("host")) {

            changePage = null;
            return "Successfully selected %s's page.".formatted(selected.getName());
        } else {
            return "Successfully selected %s.".formatted(selected.getName());
        }
    }


    /**
     * Load the source in the user player.
     * @return the message of the command
     */
    public String load() {
        if (searchBar.getLastSelected() == null) {
            return "Please select a source before attempting to load.";
        }

        if (!searchBar.getLastSearchType().equals("song")
                && ((AudioCollection) searchBar.getLastSelected()).getNumberOfTracks() == 0) {
            return "You can't load an empty audio collection!";
        }

        player.setSource(searchBar.getLastSelected(), searchBar.getLastSearchType());
        searchBar.clearSelection();

        player.pause();

        return "Playback loaded successfully.";
    }

    /**
     * Play or pause player.
     * @return the message of the command
     */
    public String playPause() {
        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before attempting to pause or resume playback.";
        }

        player.pause();

        if (player.getPaused()) {
            return "Playback paused successfully.";
        } else {
            return "Playback resumed successfully.";
        }
    }

    /**
     * Apply repeat modes on the player.
     * @return the message of the command
     */
    public String repeat() {
        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before setting the repeat status.";
        }

        Enums.RepeatMode repeatMode = player.repeat();
        String repeatStatus = "";

        switch (repeatMode) {
            case NO_REPEAT:
                repeatStatus = "no repeat";
                break;
            case REPEAT_ONCE:
                repeatStatus = "repeat once";
                break;
            case REPEAT_ALL:
                repeatStatus = "repeat all";
                break;
            case REPEAT_INFINITE:
                repeatStatus = "repeat infinite";
                break;
            case REPEAT_CURRENT_SONG:
                repeatStatus = "repeat current song";
                break;
            default:
                repeatStatus = "repeat mode set error.";
        }

        return "Repeat mode changed to %s.".formatted(repeatStatus);
    }


    /**
     * Shuffle setted collection in the player.
     * @param seed shuffle seed
     * @return the message of the command
     */
    public String shuffle(final Integer seed) {
        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before using the shuffle function.";
        }

        if (!(player.getType().equals("playlist") || player.getType().equals("album"))) {
            return "The loaded source is not a playlist or an album.";
        }

        player.shuffle(seed);

        if (player.getShuffle()) {
            return "Shuffle function activated successfully.";
        }

        return "Shuffle function deactivated successfully.";
    }


    /**
     * The forward command.
     * @return the message of the command
     */
    public String forward() {
        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before attempting to forward.";
        }

        if (!player.getType().equals("podcast")) {
            return "The loaded source is not a podcast.";
        }

        player.skipNext();

        return "Skipped forward successfully.";
    }


    /**
     * The backward command.
     * @return The message of the command
     */
    public String backward() {
        if (player.getCurrentAudioFile() == null) {
            return "Please select a source before rewinding.";
        }

        if (!player.getType().equals("podcast")) {
            return "The loaded source is not a podcast.";
        }

        player.skipPrev();

        return "Rewound successfully.";
    }


    /**
     * Set like or dislike to the loaded song.
     * @return the message of the command
     */
    public String like() {
        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before liking or unliking.";
        }

        if (!player.getType().equals("song") && !player.getType().equals("playlist")
                && !player.getType().equals("album")) {

            return "Loaded source is not a song.";
        }

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


    /**
     * Go to the next source in the audio collection loaded in the player.
     * @return the message of the command
     */
    public String next() {
        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before skipping to the next track.";
        }

        player.next();

        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before skipping to the next track.";
        }

        return "Skipped to next track successfully. The current track is %s."
                .formatted(player.getCurrentAudioFile().getName());
    }


    /**
     * Go to the previous source in the audio collection loaded in the player.
     * @return the message of the command
     */
    public String prev() {
        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before returning to the previous track.";
        }

        player.prev();

        return "Returned to previous track successfully. The current track is %s."
                .formatted(player.getCurrentAudioFile().getName());
    }


    /**
     * Create playlist.
     * @param name name of the playlist
     * @param timestamp timestamp of the command
     * @return the message of the command
     */
    public String createPlaylist(final String name, final int timestamp) {
        if (playlists.stream().anyMatch(playlist -> playlist.getName().equals(name))) {
            return "A playlist with the same name already exists.";
        }

        playlists.add(new Playlist(name, getUsername(), timestamp));

        return "Playlist created successfully.";
    }

    /**
     * Add or remove from a playlist a song.
     * @param id id of the playlist
     * @return the message of the command
     */
    public String addRemoveInPlaylist(final int id) {
        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before adding to or removing from the playlist.";
        }

        if (player.getType().equals("podcast")) {
            return "The loaded source is not a song.";
        }

        if (id > playlists.size()) {
            return "The specified playlist does not exist.";
        }

        Playlist playlist = playlists.get(id - 1);

        if (playlist.containsSong((Song) player.getCurrentAudioFile())) {
            playlist.removeSong((Song) player.getCurrentAudioFile());
            return "Successfully removed from playlist.";
        }

        playlist.addSong((Song) player.getCurrentAudioFile());
        return "Successfully added to playlist.";
    }


    /**
     * Switch the playlist visibility.
     * @param playlistId playlist ID
     * @return the message of the command
     */
    public String switchPlaylistVisibility(final Integer playlistId) {
        if (playlistId > playlists.size()) {
            return "The specified playlist ID is too high.";
        }

        Playlist playlist = playlists.get(playlistId - 1);
        playlist.switchVisibility();

        if (playlist.getVisibility() == Enums.Visibility.PUBLIC) {
            return "Visibility status updated successfully to public.";
        }

        return "Visibility status updated successfully to private.";
    }


    /**
     * Switch user connection status.
     * @param commandInput the command to be executed
     * @return the message of the command
     */
    public static String switchUserConnectionStatus(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());

        if (user == null) {
            return "The username " + commandInput.getUsername() + " doesn't exist.";
        }

        if (user.getType().equals("artist") || user.getType().equals("host")) {
            return user.getUsername() + " is not a normal user.";
        }

        NormalUser normalUser = (NormalUser) user;

        if (normalUser.connectionStatus == Enums.ConnectionStatus.ONLINE) {
            normalUser.connectionStatus = Enums.ConnectionStatus.OFFLINE;
        } else {
            normalUser.connectionStatus = Enums.ConnectionStatus.ONLINE;
        }

        return normalUser.getUsername() + " has changed status successfully.";
    }

    /**
     * Show playlists of a user.
     * @return playlists in output format
     */
    public ArrayList<PlaylistOutput> showPlaylists() {
        ArrayList<PlaylistOutput> playlistOutputs = new ArrayList<>();
        for (Playlist playlist : playlists) {
            playlistOutputs.add(new PlaylistOutput(playlist));
        }

        return playlistOutputs;
    }

    /**
     * Follow or unfollow loaded source in user player.
     * @return the message of the command
     */
    public String follow() {
        LibraryEntry selection = searchBar.getLastSelected();
        String type = searchBar.getLastSearchType();

        if (selection == null) {
            return "Please select a source before following or unfollowing.";
        }

        if (!type.equals("playlist")) {
            return "The selected source is not a playlist.";
        }

        Playlist playlist = (Playlist) selection;

        if (playlist.getOwner().equals(getUsername())) {
            return "You cannot follow or unfollow your own playlist.";
        }

        if (followedPlaylists.contains(playlist)) {
            followedPlaylists.remove(playlist);
            playlist.decreaseFollowers();

            return "Playlist unfollowed successfully.";
        }

        followedPlaylists.add(playlist);
        playlist.increaseFollowers();

        return "Playlist followed successfully.";
    }

    /**
     * Gets the statistics of the loaded source in the player.
     * @return player statistics
     */
    public PlayerStats getPlayerStats() {
        return player.getStats();
    }

    /**
     * Show preferred songs of a user.
     * @return preferred songs names
     */
    public ArrayList<String> showPreferredSongs() {
        ArrayList<String> results = new ArrayList<>();
        for (AudioFile audioFile : likedSongs) {
            results.add(audioFile.getName());
        }

        return results;
    }

    /**
     * Gets preferred genre of the user.
     * @return preferred genre
     */
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

    /**
     * Print current page.
     * @return the page content
     */
    public String printCurrentPage() {
        if (changePage != null && changePage.equals("Home")) {
            HomePage userHomePage = new HomePage();
            return userHomePage.printPage(this);
        } else if (changePage != null && changePage.equals("LikedContent")) {
            LikedContentPage userLikedContentPage = new LikedContentPage();
            return userLikedContentPage.printPage(this);
        } else if (searchBar.getSelectedPageType().equals("artist")) {
            String artistName = searchBar.getSelectedUserPage().toString();
            return ArtistPage.printPage(artistName);
        } else if (searchBar.getSelectedPageType().equals("host")) {
            String hostName = searchBar.getSelectedUserPage().toString();
            return HostPage.printPage(hostName);
        }

        return null;
    }

    /**
     * Change the page.
     * @param nextPage next page to be printed
     * @return the message of the page
     */
    public String changePage(final String nextPage) {
        if (!nextPage.equals("Home") && !nextPage.equals("LikedContent")) {
            return this.getUsername() + " is trying to access a non-existent page.";
        }

        changePage = nextPage;
        searchBar.setSelectedUserPage(null);
        searchBar.setSelectedPageType(null);

        return this.getUsername() + " accessed " + nextPage + " successfully.";
    }

    /**
     * Simulate time for users.
     * @param time the current time
     */
    public void simulateTime(final int time) {
        if (connectionStatus == Enums.ConnectionStatus.ONLINE) {
            player.simulatePlayer(time);
        }
    }

    /**
     * Delete normal user data from everywhere it could be saved.
     * @return boolean
     */
    public boolean deleteNormalUserData() {
        List<NormalUser> normalUsers = Admin.getNormalUsers();

        // veificam daca nici un user normal nu asculta playliste lui
        // userul pe care vrem sa-l stergem
        for (NormalUser normalUser : normalUsers) {
            for (Playlist playlist : this.playlists) {
                if (normalUser.getCurrentAudioCollectionName() != null
                        && normalUser.getCurrentAudioCollectionName().equals(playlist.getName())) {

                    return false;
                }
            }
        }

        // stergem pentru fiecare userNormal din followedPlaylists playlisturile userului
        // pe care vrem sa il eliminam
        for (NormalUser normalUser : normalUsers) {
            for (Playlist playlist : this.getPlaylists()) {
                normalUser.unfollowPlaylist(playlist);
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

        return true;
    }

    /**
     * Remove a playlist from the followed playlists list.
     * @param playlist playlist to be removed
     */
    public void unfollowPlaylist(final Playlist playlist) {
        this.followedPlaylists.remove(playlist);
    }

    /**
     * Delete the song from user Liked Songs
     * @param song song to be deleted from Liked Songs
     */
    public void deleteLikedSong(final Song song) {
        this.likedSongs.remove(song);
    }

    /**
     * Gets the current loaded in the player audio file name.
     * @return the audio file name
     */
    public String getCurrentAudioFileName() {
        if (player.getCurrentAudioFile() != null) {
            return player.getCurrentAudioFile().getName();
        }
        return null;

    }

    /**
     * Gets the current loaded in the player audio collection name.
     * @return the audio collection name
     */
    public String getCurrentAudioCollectionName() {
        if (player.getCurrentAudioCollection() != null) {
            return player.getCurrentAudioCollection().getName();
        }
        return null;
    }

}
