package app;

import app.audio.Collections.AlbumOutput;
import app.audio.Collections.PodcastOutput;
import app.audio.Collections.PlaylistOutput;
import app.player.PlayerStats;
import app.searchBar.Filters;
import app.users.User;
import app.users.userTypes.Artist;
import app.users.userTypes.Host;
import app.users.userTypes.NormalUser;
import app.utils.Enums;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.input.CommandInput;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Command runner.
 */
public final class CommandRunner {
    /**
     * The Object mapper.
     */
    private static ObjectMapper objectMapper = new ObjectMapper();

    private CommandRunner() {
    }

    /**
     * Search object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode search(final CommandInput commandInput) {
        NormalUser normalUser = (NormalUser) Admin.getUser(commandInput.getUsername());
        if (normalUser.getConnectionStatus() == Enums.ConnectionStatus.OFFLINE) {
            return userOffline(commandInput);
        }
        Filters filters = new Filters(commandInput.getFilters());
        String type = commandInput.getType();

        ArrayList<String> results = normalUser.search(filters, type);
        String message = "Search returned " + results.size() + " results";

        return setCommandOutput(commandInput, message, results, null, null);
    }

    /**
     * Select object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode select(final CommandInput commandInput) {
        NormalUser normalUser = (NormalUser) Admin.getUser(commandInput.getUsername());
        if (normalUser.getConnectionStatus() == Enums.ConnectionStatus.OFFLINE) {
            return userOffline(commandInput);
        }
        String message = normalUser.select(commandInput.getItemNumber());

        return setCommandOutput(commandInput, message, null, null, null);
    }

    /**
     * Load object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode load(final CommandInput commandInput) {
        NormalUser normalUser = (NormalUser) Admin.getUser(commandInput.getUsername());
        if (normalUser.getConnectionStatus() == Enums.ConnectionStatus.OFFLINE) {
            return userOffline(commandInput);
        }
        String message = normalUser.load();

        return setCommandOutput(commandInput, message, null, null, null);
    }

    /**
     * Play pause object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode playPause(final CommandInput commandInput) {
        NormalUser normalUser = (NormalUser) Admin.getUser(commandInput.getUsername());
        if (normalUser.getConnectionStatus() == Enums.ConnectionStatus.OFFLINE) {
            return userOffline(commandInput);
        }
        String message = normalUser.playPause();

        return setCommandOutput(commandInput, message, null, null, null);
    }

    /**
     * Repeat object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode repeat(final CommandInput commandInput) {
        NormalUser user = (NormalUser) Admin.getUser(commandInput.getUsername());
        if (user.getConnectionStatus() == Enums.ConnectionStatus.OFFLINE) {
            return userOffline(commandInput);
        }
        String message = user.repeat();

        return setCommandOutput(commandInput, message, null, null, null);
    }

    /**
     * Shuffle object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode shuffle(final CommandInput commandInput) {
        NormalUser normalUser = (NormalUser) Admin.getUser(commandInput.getUsername());
        if (normalUser.getConnectionStatus() == Enums.ConnectionStatus.OFFLINE) {
            return userOffline(commandInput);
        }
        Integer seed = commandInput.getSeed();
        String message = normalUser.shuffle(seed);

        return setCommandOutput(commandInput, message, null, null, null);
    }

    /**
     * Forward object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode forward(final CommandInput commandInput) {
        NormalUser normalUser = (NormalUser) Admin.getUser(commandInput.getUsername());
        if (normalUser.getConnectionStatus() == Enums.ConnectionStatus.OFFLINE) {
            return userOffline(commandInput);
        }
        String message = normalUser.forward();

        return setCommandOutput(commandInput, message, null, null, null);
    }

    /**
     * Backward object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode backward(final CommandInput commandInput) {
        NormalUser normalUser = (NormalUser) Admin.getUser(commandInput.getUsername());
        if (normalUser.getConnectionStatus() == Enums.ConnectionStatus.OFFLINE) {
            return userOffline(commandInput);
        }
        String message = normalUser.backward();

        return setCommandOutput(commandInput, message, null, null, null);
    }

    /**
     * Like object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode like(final CommandInput commandInput) {
        NormalUser normalUser = (NormalUser) Admin.getUser(commandInput.getUsername());
        if (normalUser.getConnectionStatus() == Enums.ConnectionStatus.OFFLINE) {
            return userOffline(commandInput);
        }
        String message = normalUser.like();

        return setCommandOutput(commandInput, message, null, null, null);
    }

    /**
     * Next object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode next(final CommandInput commandInput) {
        NormalUser normalUser = (NormalUser) Admin.getUser(commandInput.getUsername());
        if (normalUser.getConnectionStatus() == Enums.ConnectionStatus.OFFLINE) {
            return userOffline(commandInput);
        }
        String message = normalUser.next();

        return setCommandOutput(commandInput, message, null, null, null);
    }

    /**
     * Prev object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode prev(final CommandInput commandInput) {
        NormalUser normalUser = (NormalUser) Admin.getUser(commandInput.getUsername());
        if (normalUser.getConnectionStatus() == Enums.ConnectionStatus.OFFLINE) {
            return userOffline(commandInput);
        }
        String message = normalUser.prev();

        return setCommandOutput(commandInput, message, null, null, null);
    }

    /**
     * Create playlist object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode createPlaylist(final CommandInput commandInput) {
        NormalUser normalUser = (NormalUser) Admin.getUser(commandInput.getUsername());
        if (normalUser.getConnectionStatus() == Enums.ConnectionStatus.OFFLINE) {
            return userOffline(commandInput);
        }
        String message = normalUser.createPlaylist(commandInput.getPlaylistName(),
                                                    commandInput.getTimestamp());

        return setCommandOutput(commandInput, message, null, null, null);
    }

    /**
     * Add remove in playlist object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode addRemoveInPlaylist(final CommandInput commandInput) {
        NormalUser normalUser = (NormalUser) Admin.getUser(commandInput.getUsername());
        if (normalUser.getConnectionStatus() == Enums.ConnectionStatus.OFFLINE) {
            return userOffline(commandInput);
        }
        String message = normalUser.addRemoveInPlaylist(commandInput.getPlaylistId());

        return setCommandOutput(commandInput, message, null, null, null);
    }

    /**
     * Switch visibility object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode switchVisibility(final CommandInput commandInput) {
        NormalUser normalUser = (NormalUser) Admin.getUser(commandInput.getUsername());
        if (normalUser.getConnectionStatus() == Enums.ConnectionStatus.OFFLINE) {
            return userOffline(commandInput);
        }
        String message = normalUser.switchPlaylistVisibility(commandInput.getPlaylistId());

        return setCommandOutput(commandInput, message, null, null, null);
    }

    /**
     * Switch connection status object node.
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode switchConnectionStatus(final CommandInput commandInput) {
        String message = NormalUser.switchUserConnectionStatus(commandInput);

        return setCommandOutput(commandInput, message, null, null, null);
    }


    /**
     * Show playlists object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode showPlaylists(final CommandInput commandInput) {
        NormalUser normalUser = (NormalUser) Admin.getUser(commandInput.getUsername());
        ArrayList<PlaylistOutput> playlists = normalUser.showPlaylists();

        JsonNode results = objectMapper.valueToTree(playlists);

        return setCommandOutput(commandInput, null, null, results, null);
    }

    /**
     * Follow object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode follow(final CommandInput commandInput) {
        NormalUser normalUser = (NormalUser) Admin.getUser(commandInput.getUsername());
        if (normalUser.getConnectionStatus() == Enums.ConnectionStatus.OFFLINE) {
            return userOffline(commandInput);
        }
        String message = normalUser.follow();

        return setCommandOutput(commandInput, message, null, null, null);
    }

    /**
     * Status object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode status(final CommandInput commandInput) {
        NormalUser normalUser = (NormalUser) Admin.getUser(commandInput.getUsername());
        PlayerStats stats = normalUser.getPlayerStats();

        return setCommandOutput(commandInput, null, null, null, stats);
    }

    /**
     * Show liked songs object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode showLikedSongs(final CommandInput commandInput) {
        NormalUser normalUser = (NormalUser) Admin.getUser(commandInput.getUsername());
        ArrayList<String> songs = normalUser.showPreferredSongs();

        JsonNode showResults = objectMapper.valueToTree(songs);

        return setCommandOutput(commandInput, null, null, showResults, null);
    }

    /**
     * Add new user object node.
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode addUser(final CommandInput commandInput) {
        String message = User.addUser(commandInput);

        return setCommandOutput(commandInput, message, null, null, null);
    }

    /**
     * Delete the user.
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode deleteUser(final CommandInput commandInput) {
        String message = Admin.deleteUser(commandInput);

        return setCommandOutput(commandInput, message, null, null, null);
    }

    /**
     * Add a new album.
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode addAlbum(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = Artist.addAlbum(commandInput, user);

        return setCommandOutput(commandInput, message, null, null, null);
    }

    /**
     * Remove an album.
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode removeAlbum(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = Artist.removeAlbum(commandInput, user);

        return setCommandOutput(commandInput, message, null, null, null);
    }

    /**
     * Show an album.
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode showAlbums(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        ArrayList<AlbumOutput> albumsOutput = Artist.showAlbums(user);

        JsonNode resultShow = objectMapper.valueToTree(albumsOutput);

        return setCommandOutput(commandInput, null, null, resultShow, null);
    }

    /**
     * Add new podcast.
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode addPodcast(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = Host.addPodcast(commandInput, user);

        return setCommandOutput(commandInput, message, null, null, null);
    }

    /**
     * Remove a podcast.
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode removePodcast(final CommandInput commandInput) {
        String message = Host.removePodcast(commandInput);

        return setCommandOutput(commandInput, message, null, null, null);
    }

    /**
     * Show podcasts.
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode showPodcasts(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        ArrayList<PodcastOutput> podcastsOutput = Host.showPodcasts(user);

        JsonNode showResults = objectMapper.valueToTree(podcastsOutput);

        return setCommandOutput(commandInput, null, null, showResults, null);
    }

    /**
     * Print current page.
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode printCurrentPage(final CommandInput commandInput) {
        NormalUser normalUser = (NormalUser) Admin.getUser(commandInput.getUsername());
        if (normalUser.getConnectionStatus() == Enums.ConnectionStatus.OFFLINE) {
            return userOffline(commandInput);
        }
        String message = normalUser.printCurrentPage();

        return setCommandOutput(commandInput, message, null, null, null);
    }

    /**
     * Change page.
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode changePage(final CommandInput commandInput) {
        NormalUser normalUser = (NormalUser) Admin.getUser(commandInput.getUsername());
        if (normalUser.getConnectionStatus() == Enums.ConnectionStatus.OFFLINE) {
            return userOffline(commandInput);
        }
        String message = normalUser.changePage(commandInput.getNextPage());

        return setCommandOutput(commandInput, message, null, null, null);
    }

    /**
     * Add a new event.
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode addEvent(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = Artist.addEvent(commandInput, user);

        return setCommandOutput(commandInput, message, null, null, null);
    }

    /**
     * Remove an event.
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode removeEvent(final CommandInput commandInput) {
        String message = Artist.removeEvent(commandInput);

        return setCommandOutput(commandInput, message, null, null, null);
    }

    /**
     * Add merch.
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode addMerch(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = Artist.addMerch(commandInput, user);

        return setCommandOutput(commandInput, message, null, null, null);
    }

    /**
     * Add announcement.
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode addAnnouncement(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = Host.addAnnouncement(commandInput, user);

        return setCommandOutput(commandInput, message, null, null, null);
    }

    /**
     * Remove announcement.
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode removeAnnouncement(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = Host.removeAnnouncement(commandInput, user);

        return setCommandOutput(commandInput, message, null, null, null);
    }

    /**
     * Gets preferred genre.
     *
     * @param commandInput the command input
     * @return the preferred genre
     */
    public static ObjectNode getPreferredGenre(final CommandInput commandInput) {
        NormalUser normalUser = (NormalUser) Admin.getUser(commandInput.getUsername());
        String preferredGenre = normalUser.getPreferredGenre();

        JsonNode showResult = objectMapper.valueToTree(preferredGenre);

        return setCommandOutput(commandInput, null, null, showResult, null);
    }

    /**
     * Gets top 5 songs.
     *
     * @param commandInput the command input
     * @return the top 5 songs
     */
    public static ObjectNode getTop5Songs(final CommandInput commandInput) {
        List<String> songs = Admin.getTop5Songs();

        return  setStatisticsCommandOutput(commandInput, songs);
    }

    /**
     * Gets top 5 playlists.
     *
     * @param commandInput the command input
     * @return the top 5 playlists
     */
    public static ObjectNode getTop5Playlists(final CommandInput commandInput) {
        List<String> playlists = Admin.getTop5Playlists();

        return setStatisticsCommandOutput(commandInput, playlists);
    }

    /**
     * Gets top 5 albums.
     *
     * @param commandInput the command input
     * @return the top 5 albums
     */
    public static ObjectNode getTop5Albums(final CommandInput commandInput) {
        List<String> albums = Admin.getTop5Albums();

        return  setStatisticsCommandOutput(commandInput, albums);
    }

    /**
     * Gets top 5 artists.
     *
     * @param commandInput the command input
     * @return the top 5 artists
     */
    public static ObjectNode getTop5Artists(final CommandInput commandInput) {
        List<String> artists = Admin.getTop5Artists();

        return setStatisticsCommandOutput(commandInput, artists);
    }

    /**
     * Gets online users.
     *
     * @param commandInput the command input
     * @return all online users
     */
    public static ObjectNode getOnlineUsers(final CommandInput commandInput) {
        List<String> users = Admin.getOnlineUsers();

        return setStatisticsCommandOutput(commandInput, users);
    }

    /**
     * Gets all users.
     *
     * @param commandInput the command input
     * @return all users
     */
    public static ObjectNode getAllUsers(final CommandInput commandInput) {
        List<String> allUsers = Admin.getAllUsers();

        return setStatisticsCommandOutput(commandInput, allUsers);
    }


    /**
     * Sets output for all commands less than general statistics commands.
     * @param commandInput the command input.
     * @param message the return message of a command, it can be null.
     * @param searchResults the return result for search command, it can be null.
     * @param showResults the return result for some commands, it can be null.
     * @param stats the return result for status command, it can be null.
     * @return the object node
     */
    public static ObjectNode setCommandOutput(final CommandInput commandInput,
                                              final String message,
                                              final ArrayList<String> searchResults,
                                              final JsonNode showResults,
                                              final PlayerStats stats) {

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());

        if (message != null) {
            objectNode.put("message", message);
        }
        if (searchResults != null) {
            objectNode.put("results", objectMapper.valueToTree(searchResults));
        } else if (showResults != null) {
            objectNode.put("result", showResults);
        } else if (stats != null) {
            objectNode.put("stats", objectMapper.valueToTree(stats));
        }

        return objectNode;
    }


    /**
     * Sets output for general statistics commands.
     * @param commandInput the input command
     * @param results results of statistics commands
     * @return the object node
     */
    public static ObjectNode setStatisticsCommandOutput(final CommandInput commandInput,
                                                        final List<String> results) {

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(results));

        return objectNode;
    }


    /**
     * Sets output for offline users.
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode userOffline(final CommandInput commandInput) {
        String commandName = commandInput.getCommand();
        String message = commandInput.getUsername() + " is offline.";

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);
        if (commandName.equals("search")) {
            ArrayList<String> results = new ArrayList<>();
            objectNode.put("results", objectMapper.valueToTree(results));
        }
        return objectNode;
    }

}
