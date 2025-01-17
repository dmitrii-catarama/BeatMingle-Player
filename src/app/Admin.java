package app;

import app.audio.Collections.Album;
import app.audio.Collections.Playlist;
import app.audio.Collections.Podcast;
import app.audio.Files.Episode;
import app.audio.Files.Song;
import app.users.User;
import app.users.userTypes.Artist;
import app.users.userTypes.Host;
import app.users.userTypes.NormalUser;
import app.utils.Enums;

import fileio.input.PodcastInput;
import fileio.input.SongInput;
import fileio.input.CommandInput;
import fileio.input.EpisodeInput;
import fileio.input.UserInput;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * The type Admin.
 */
public final class Admin {
    private static Admin instance = null;
    private static List<User> users = new ArrayList<>();
    private static List<Song> songs = new ArrayList<>();
    private static List<Podcast> podcasts = new ArrayList<>();
    private static int timestamp = 0;
    private static final int LIMIT = 5;

    private Admin() {
    }

    /**
     * Singleton lazy instantiation
     * @return instance of the Admin class
     */
    public static Admin getInstance() {
        if (instance == null) {
            instance = new Admin();
        }
        return instance;
    }

    /**
     * Sets users.
     *
     * @param userInputList the user input list
     */
    public void setUsers(final List<UserInput> userInputList) {
        users = new ArrayList<>();
        for (UserInput userInput : userInputList) {
            users.add(new NormalUser(userInput.getUsername(), userInput.getAge(),
                                            userInput.getCity()));
        }
    }

    /**
     *  Add one new user
     * @param newUser new user
     */
    public static void setUser(final User newUser) {
        users.add(newUser);
    }

    /**
     * Sets songs.
     *
     * @param songInputList the song input list
     */
    public void setSongs(final List<SongInput> songInputList) {
        songs = new ArrayList<>();
        for (SongInput songInput : songInputList) {
            songs.add(new Song(songInput.getName(), songInput.getDuration(), songInput.getAlbum(),
                    songInput.getTags(), songInput.getLyrics(), songInput.getGenre(),
                    songInput.getReleaseYear(), songInput.getArtist()));
        }
    }

    /**
     * Add new song
     * @param song new song
     */
    public static void setSong(final Song song) {
        Admin.songs.add(song);
    }

    /**
     * Sets podcasts.
     *
     * @param podcastInputList the podcast input list
     */
    public void setPodcasts(final List<PodcastInput> podcastInputList) {
        podcasts = new ArrayList<>();
        for (PodcastInput podcastInput : podcastInputList) {
            List<Episode> episodes = new ArrayList<>();
            for (EpisodeInput episodeInput : podcastInput.getEpisodes()) {
                episodes.add(new Episode(episodeInput.getName(),
                                         episodeInput.getDuration(),
                                         episodeInput.getDescription()));
            }
            podcasts.add(new Podcast(podcastInput.getName(), podcastInput.getOwner(), episodes));
        }
    }


    /**
     * Add new podcast
     * @param podcast new podcast
     */
    public static void setPodcast(final Podcast podcast) {
        podcasts.add(podcast);
    }

    /**
     * Gets users.
     *
     * @return the users
     */
    public static List<User> getUsers() {
        return new ArrayList<>(users);
    }

    /**
     * Gets user.
     *
     * @param username the username
     * @return the user
     */
    public static User getUser(final String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Get all existing normal users
     * @return normal users list
     */
    public static List<NormalUser> getNormalUsers() {
        List<NormalUser> normalUsers = new ArrayList<>();

        for (User user : users) {
            if (user.getType().equals("user")) {
                normalUsers.add((NormalUser) user);
            }
        }

        return normalUsers;
    }

    /**
     * Get all existing artists
     * @return artistts list
     */
    public static List<Artist> getArtists() {
        List<Artist> artists = new ArrayList<>();

        for (User user : users) {
            if (user.getType().equals("artist")) {
                artists.add((Artist) user);
            }
        }

        return artists;
    }

    /**
     * get Admin songs
     * @return songs list
     */
    public static List<Song> getAdminSongs() {
        return songs;
    }

    /**
     * Gets songs.
     *
     * @return the songs
     */
    public static List<Song> getSongs() {
        return new ArrayList<>(songs);
    }

    /**
     * Gets podcasts.
     *
     * @return the podcasts
     */
    public static List<Podcast> getPodcasts() {
        return new ArrayList<>(podcasts);
    }
    public static List<Podcast> getAdminPodcasts() {
        return podcasts;
    }

    /**
     * Gets albums.
     *
     * @return the albums
     */
//    public static List<Album> getAlbums() {
//        return new ArrayList<>(albums);
//    }

    /**
     * Gets playlists.
     *
     * @return the playlists
     */
    public static List<Playlist> getPlaylists() {
        List<Playlist> playlists = new ArrayList<>();
        for (User user : users) {
            if (user.getType().equals("user")) {
                playlists.addAll(((NormalUser) user).getPlaylists());
            }
        }
        return playlists;
    }

    /**
     * get all albums of all artists
     * @return list of albums
     */
    public static List<Album> getAllAlbums() {
        List<Album> albums = new ArrayList<>();
        for (User user : users) {
            if (user.getType().equals("artist")) {
                albums.addAll(((Artist) user).getAlbums());
            }
        }
        return albums;
    }

    /**
     * delete one user
     * @param commandInput input command
     * @return the message result of the deleting
     */
    public static String deleteUser(final CommandInput commandInput) {
        User user = getUser(commandInput.getUsername());

        if (user == null) {
            return "The username " + commandInput.getUsername() + " doesn't exist.";
        }

        switch (user.getType()) {
            case "user" -> {
                if (((NormalUser) user).deleteNormalUserData()) {
                    users.remove(user);
                    return commandInput.getUsername() + " was successfully deleted.";
                } else {
                    return commandInput.getUsername() + " can't be deleted.";
                }
            }
            case "artist" -> {
                if (((Artist) user).deleteArtistData()) {
                    users.remove(user);
                    return commandInput.getUsername() + " was successfully deleted.";
                } else {
                    return commandInput.getUsername() + " can't be deleted.";
                }
            }
            case "host" -> {
                if (((Host) user).deleteHostData()) {
                    users.remove(user);
                    return commandInput.getUsername() + " was successfully deleted.";
                } else {
                    return commandInput.getUsername() + " can't be deleted.";
                }
            }
            default -> {
                return "Not existing type of user.";
            }
        }

    }

    /**
     * Update timestamp.
     *
     * @param newTimestamp the new timestamp
     */
    public static void updateTimestamp(final int newTimestamp) {
        int elapsed = newTimestamp - timestamp;
        timestamp = newTimestamp;
        if (elapsed == 0) {
            return;
        }

        for (User user : users) {
            if (user.getType().equals("user")) {
                ((NormalUser) user).simulateTime(elapsed);
            }
        }
    }

    /**
     * Gets top 5 songs.
     *
     * @return the top 5 songs
     */
    public static List<String> getTop5Songs() {
        List<Song> sortedSongs = new ArrayList<>(songs);
        sortedSongs.sort(Comparator.comparingInt(Song::getLikes).reversed());
        List<String> topSongs = new ArrayList<>();
        int count = 0;
        for (Song song : sortedSongs) {
            if (count >= LIMIT) {
                break;
            }
            topSongs.add(song.getName());
            count++;
        }
        return topSongs;
    }

    /**
     * Gets top 5 playlists.
     *
     * @return the top 5 playlists
     */
    public static List<String> getTop5Playlists() {
        List<Playlist> sortedPlaylists = new ArrayList<>(getPlaylists());
        sortedPlaylists.sort(Comparator.comparingInt(Playlist::getFollowers)
                .reversed()
                .thenComparing(Playlist::getTimestamp, Comparator.naturalOrder()));
        List<String> topPlaylists = new ArrayList<>();
        int count = 0;
        for (Playlist playlist : sortedPlaylists) {
            if (count >= LIMIT) {
                break;
            }
            topPlaylists.add(playlist.getName());
            count++;
        }
        return topPlaylists;
    }

    /**
     * Get top 5 albums
     * @return the list of names of top 5 albums
     */
    public static List<String> getTop5Albums() {
        List<Album> sortedAlbums = new ArrayList<>(getAllAlbums());
        sortedAlbums.sort(Comparator.comparingInt(Album::getLikes)
                .reversed()
                .thenComparing(Album::getName, Comparator.naturalOrder()));

        List<String> topAlbums = new ArrayList<>();
        int count = 0;
        for (Album album : sortedAlbums) {
            if (count >= LIMIT) {
                break;
            }
            topAlbums.add(album.getName());
            count++;
        }
        return topAlbums;
    }

    /**
     * Get top 5 artists
     * @return the list of names of top 5 artists
     */
    public static List<String> getTop5Artists() {
        List<Artist> sortedArtists = getArtists();
        sortedArtists.sort(Comparator.comparingInt(Artist::getArtistLikes)
                .reversed()
                .thenComparing(Artist::getUsername, Comparator.naturalOrder()));

        List<String> topArtists = new ArrayList<>();
        int count = 0;

        for (Artist artist : sortedArtists) {
            if (count >= LIMIT) {
                break;
            }

            topArtists.add(artist.getUsername());
            count++;
        }

        return topArtists;
    }

    /**
     * Get online users
     * @return online users
     */
    public static List<String> getOnlineUsers() {
        List<User> normalUsers = new ArrayList<>(users);
        List<String> onlineUsers = new ArrayList<>();
        for (User user : normalUsers) {
            if (user.getType().equals("user")) {
                if (((NormalUser) user).getConnectionStatus() == Enums.ConnectionStatus.ONLINE) {
                    onlineUsers.add(user.getUsername());
                }
            }
        }
        return onlineUsers;

    }

    /**
     * get all existing users in Admin
     * @return all users
     */
    public static List<String> getAllUsers() {
        List<String> normalUsers = new ArrayList<>();
        List<String> artists = new ArrayList<>();
        List<String> hosts = new ArrayList<>();

        for (User user : users) {
            switch (user.getType()) {
                case "user" -> normalUsers.add(user.getUsername());
                case "artist" -> artists.add(user.getUsername());
                case "host" -> hosts.add(user.getUsername());
                default -> System.out.println("Invalid user type");
            }
        }

        List<String> allUsers = new ArrayList<>();
        allUsers.addAll(normalUsers);
        allUsers.addAll(artists);
        allUsers.addAll(hosts);

        return allUsers;
    }

    /**
     * Reset.
     */
    public static void reset() {
        users = new ArrayList<>();
        songs = new ArrayList<>();
        podcasts = new ArrayList<>();
        timestamp = 0;
    }
}
