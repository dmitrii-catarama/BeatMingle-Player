package app.users.userTypes;

import app.Admin;
import app.audio.Collections.Album;
import app.audio.Collections.AlbumOutput;
import app.audio.Collections.Playlist;
import app.audio.Collections.Utilities.forArtist.Event;
import app.audio.Collections.Utilities.forArtist.Merch;
import app.audio.Files.Song;
import app.player.PlayerStats;
import app.users.User;
import app.utils.Enums;
import fileio.input.CommandInput;
import fileio.input.SongInput;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Artist extends User {
    private ArrayList<Album> albums;
    private ArrayList<Merch> merch;
    private ArrayList<Event> events;

    public Artist(final String username, final int age, final String city) {
        super(username, age, city);
        super.setType("artist");

        albums = new ArrayList<>();
        merch = new ArrayList<>();
        events = new ArrayList<>();
    }

    /**
     * Gets albums of the artist.
     * @return albums
     */
    public ArrayList<Album> getAlbums() {
        return albums;
    }

    /**
     * Gets merch of the artist.
     * @return the merch
     */
    public ArrayList<Merch> getMerch() {
        return merch;
    }

    /**
     * Gets events of the artist.
     * @return events
     */
    public ArrayList<Event> getEvents() {
        return events;
    }

    /**
     * Sets the new album in the albums collection.
     * @param album the new album
     */
    public void setAlbum(final Album album) {
        albums.add(album);
    }

    /**
     * Verify if artist can add a new album.
     * @param commandInput the command input
     * @param user user that called the addAlbum command
     * @return the message of the command
     */
    public static String addAlbum(final CommandInput commandInput, final User user) {
        String nameAlbum = commandInput.getName();

        if (user == null) {
            return "The username " + user.getUsername() + " doesn't exist.";
        } else if (!user.getType().equals("artist")) {
            return user.getUsername() + " is not an artist.";
        } else if (((Artist) user).artistHasAlbum(nameAlbum)) {
            return user.getUsername() + " has another album with the same name.";
        }

        ArrayList<SongInput> songsToAdd = commandInput.getSongs();
        Set<String> set = new HashSet<>();
        boolean songHasDuplicate = false;
        for (SongInput song : songsToAdd) {
            if (!set.add(song.getName())) {
                // if the element is already in the set, it's a duplicate
                songHasDuplicate = true;
            }
        }
        if (songHasDuplicate) {
            return user.getUsername() + " has the same song at least twice in this album.";
        }

        String owner = commandInput.getUsername();
        Integer releaseYear = commandInput.getReleaseYear();
        String description = commandInput.getDescription();
        ArrayList<Song> songs = new ArrayList<>();

        for (SongInput songInput : songsToAdd) {
            songs.add(new Song(songInput.getName(), songInput.getDuration(), songInput.getAlbum(),
                    songInput.getTags(), songInput.getLyrics(), songInput.getGenre(),
                    songInput.getReleaseYear(), songInput.getArtist()));
        }

        Album newAlbum = new Album(nameAlbum, owner, releaseYear, description, songs);
        ((Artist) user).setAlbum(newAlbum);

        for (Song song : newAlbum.getSongs()) {
            Admin.setSong(song);
        }

        return user.getUsername() + " has added new album successfully.";
    }

    /**
     * Verify if an artist can delete the album.
     * @param commandInput the command input
     * @param user user that called the removeAlbum command
     * @return the message of the command
     */
    public static String removeAlbum(final CommandInput commandInput, final User user) {
        String nameAlbum = commandInput.getName();

        if (user == null) {
            return "The username " + commandInput.getUsername() + " doesn't exist.";
        } else if (!user.getType().equals("artist")) {
            return user.getUsername() + " is not an artist.";
        } else if (!((Artist) user).artistHasAlbum(nameAlbum)) {
            return user.getUsername() + " doesn't have an album with the given name.";
        }

        List<NormalUser> normalUsers = Admin.getNormalUsers();
        Artist artist = (Artist) user;
        List<Playlist> playlists = Admin.getPlaylists();
        Album albumToDelete = new Album(null);

        for (Album album : artist.getAlbums()) {
            if (album.getName().equals(nameAlbum)) {
                albumToDelete = album;
                break;
            }
        }

        if (albumToDelete.getName() == null) {
            return "Album nu a fost gasit din cauza unei erori";
        }

        // verificare daca unul din playlisturi contine cantecul din album
        for (Playlist playlist : playlists) {
            for (Song song : albumToDelete.getSongs()) {
                if (playlist.getSongs().contains(song)) {
                    return user.getUsername() + " can't delete this album.";
                }
            }
        }

        // verificare daca la player nu canta audio colectia de album
        for (NormalUser normalUser : normalUsers) {
            if (normalUser.getCurrentAudioCollectionName() != null
                && normalUser.getCurrentAudioCollectionName().equals(albumToDelete.getName())) {

                return user.getUsername() + " can't delete this album.";
            }

            // verificare daca in player canta un song din album
            for (Song song : albumToDelete.getSongs()) {
                if (normalUser.getCurrentAudioFileName() != null
                    && normalUser.getCurrentAudioFileName().equals(song.getName())) {

                    return user.getUsername() + " can't delete this album.";
                }
            }
        }

        List<Song> allSongs = Admin.getAdminSongs();

        for (Song song : albumToDelete.getSongs()) {
            allSongs.remove(song);
        }

        //Admin.getAdminAlbums().remove(albumToDelete);
        artist.albums.remove(albumToDelete);

        return artist.getUsername() + " deleted the album successfully.";
    }

    /**
     * Show all albums of the artist.
     * @param user the artist
     * @return information about all albums
     */
    public static ArrayList<AlbumOutput> showAlbums(final User user) {
        Artist artist = (Artist) user;

        if (artist.albums == null) {
            System.out.println("No albums:(");
            return null;
        }

        return AlbumOutput.albumOutput(artist.albums);
    }

    /**
     * Verify if the artist has the album with a mentioned name
     * @param albumName the mentioned name
     * @return boolean
     */
    public boolean artistHasAlbum(final String albumName) {
        if (this.albums == null) {
            return false;
        }
        for (Album album : this.albums) {
            if (album.getName().equals(albumName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Add a new event.
     * @param commandInput the command input
     * @param user the user which called the command addEvent
     * @return the message of the command
     */
    public static String addEvent(final CommandInput commandInput, final User user) {
        if (user == null) {
            return "The username " + commandInput.getUsername() + " doesn't exist.";
        } else if (!user.getType().equals("artist")) {
            return commandInput.getUsername() + " is not an artist.";
        }

        Artist artist = (Artist) user;
        String eventName = commandInput.getName();

        if (artist.events.stream().anyMatch(event -> eventName.equals(event.getName()))) {
            return artist.getUsername() + " has another event with the same name.";
        }
        if (!Event.dateIsValid(commandInput.getDate())) {
            return "Event for " + artist.getUsername() + " does not have a valid date.";
        }

        artist.events.add(new Event(commandInput.getName(), commandInput.getDescription(),
                commandInput.getDate()));

        return artist.getUsername() + " has added new event successfully.";
    }

    /**
     * Remove an event.
     * @param commandInput the command input
     * @return the message of the command
     */
    public static String removeEvent(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());

        if (user == null) {
            return "The username " + commandInput.getUsername() + " doesn't exist.";
        } else if (!user.getType().equals("artist")) {
            return commandInput.getUsername() + " is not an artist.";
        }

        Artist artist = (Artist) user;
        String eventDelete = commandInput.getName();

        for (Event event : artist.events) {
            if (event.getName().equals(eventDelete)) {
                artist.events.remove(event);
                return artist.getUsername() + " deleted the event successfully.";
            }
        }

        return artist.getUsername() + " doesn't have an event with the given name.";
    }

    /**
     * Add a new merch.
     * @param commandInput the command input
     * @param user the user that called the command
     * @return
     */
    public static String addMerch(final CommandInput commandInput, final User user) {
        if (user == null) {
            return "The username " + commandInput.getUsername() + " doesn't exist.";
        } else if (!user.getType().equals("artist")) {
            return commandInput.getUsername() + " is not an artist.";
        }

        Artist artist = (Artist) user;
        String merchName = commandInput.getName();
        if (artist.merch.stream().anyMatch(event -> merchName.equals(event.getName()))) {
            return artist.getUsername() + " has merchandise with the same name.";
        }
        if (!Merch.priceIsValid(commandInput.getPrice())) {
            return "Price for merchandise can not be negative.";
        }

        artist.getMerch().add(new Merch(commandInput.getName(), commandInput.getPrice(),
                commandInput.getDescription()));

        return artist.getUsername() + " has added new merchandise successfully.";
    }

    /**
     * Delete the artist albums, events and merch.
     * @return boolean
     */
    public boolean deleteArtistData() {
        List<Song> allSongs = Admin.getAdminSongs();
        List<NormalUser> normalUsers = Admin.getNormalUsers();

        // verificare daca nici un user normal nu se afla pe pagina artistului si daca nu se
        // asculta un cantec din albumurile acestuia
        for (NormalUser normalUser : normalUsers) {
            if (normalUser.getConnectionStatus().equals(Enums.ConnectionStatus.OFFLINE)) {
                continue;
            }

            String lastSearchType = normalUser.getSearchBar().getLastSearchType();
            if (lastSearchType == null || !lastSearchType.equals("artist")) {
                continue;
            }



            if (normalUser.getSearchBar().getSelectedUserPage() == null) {
                continue;
            }

            String pageSet = normalUser.getSearchBar().getSelectedUserPage().toString();
            if (normalUser.getSearchBar().getSelectedPageType() != null) {
                if (this.getUsername().equals(pageSet)) {
                    return false;
                }
            }
        }

        for (NormalUser normalUser : normalUsers) {
            PlayerStats playerStats = normalUser.getPlayerStats();
            String songInPlayer = playerStats.getName();

            if (songInPlayer == null) {
                continue;
            }

            for (Album album : this.albums) {
                for (Song song : album.getSongs()) {
                    if (song.getName().equals(songInPlayer)) {
                        return false;
                    }
                }
            }
        }

        for (Album album : this.albums) {
            for (Song song : album.getSongs()) {
                allSongs.remove(song);
                for (NormalUser normalUser : normalUsers) {
                    normalUser.deleteLikedSong(song);
                }
            }
        }

        albums.clear();
        events.clear();
        merch.clear();

        return true;
    }

    /**
     * Gets the artist likes from all songs from his albums.
     * @return likes
     */
    public Integer getArtistLikes() {
        Integer likes = 0;

        for (Album album : this.albums) {
            likes += album.getLikes();
        }

        return likes;
    }


}
