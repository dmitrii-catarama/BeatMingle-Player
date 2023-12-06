package app.users.userTypes;

import app.Admin;
import app.audio.Collections.Album;
import app.audio.Collections.AlbumOutput;
import app.audio.Collections.Utilities.forArtist.Event;
import app.audio.Collections.Utilities.forArtist.Merch;
import app.audio.Files.Song;
import app.users.User;
import fileio.input.CommandInput;
import fileio.input.SongInput;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Artist extends User {
    private ArrayList<Album> albums;
    private ArrayList<Merch> merch;
    private ArrayList<Event> events;

    public Artist(String username, int age, String city) {
        super(username, age, city);
        super.setType("artist");

        albums = new ArrayList<>();
        merch = new ArrayList<>();
        events = new ArrayList<>();
    }

    public ArrayList<Album> getAlbums() {
        return albums;
    }
    public ArrayList<Merch> getMerch() {
        return merch;
    }
    public ArrayList<Event> getEvents() {
        return events;
    }


    public void setAlbum(Album album) {
        albums.add(album);
    }


    public static String addAlbum(CommandInput commandInput, User user) {
        String nameAlbum = commandInput.getName();

        if (user == null) {
            return "The username " + user.getUsername() + " doesn't exist.";
        } else if (!user.getType().equals("artist")) {
            return user.getUsername() + " is not an artist.";
        } else if (artistHasAlbum(((Artist)user).getAlbums(), nameAlbum)) {
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


    public static ArrayList<AlbumOutput> showAlbums(User user) {
        Artist artist = (Artist)user;

        if(artist.getAlbums() == null) {
            System.out.println("No albums:(");
            return null;
        }

        return AlbumOutput.albumOutput(artist.getAlbums());
    }

    public static boolean artistHasAlbum(ArrayList<Album> albums, String albumName) {
        if (albums == null) {
            return false;
        }
        for (Album album : albums) {
            if (album.getName().equals(albumName)) {
                return true;
            }
        }
        return false;
    }

    public static String addEvent(CommandInput commandInput, User user) {
        if (user == null) {
            return "The username " + commandInput.getUsername() + " doesn't exist.";
        } else if (!user.getType().equals("artist")) {
            return commandInput.getUsername() + " is not an artist.";
        }

        Artist artist = (Artist)user ;
        String eventName = commandInput.getName();

        if (artist.getEvents().stream().anyMatch(event -> eventName.equals(event.getName()))) {
            return artist.getUsername() + " has another event with the same name.";
        }
        if (!Event.dateIsValid(commandInput.getDate())) {
            return "Event for " + artist.getUsername() + " does not have a valid date.";
        }

        artist.getEvents().add(new Event(commandInput.getName(), commandInput.getDescription(),
                commandInput.getDate()));

        return artist.getUsername() + " has added new event successfully.";
    }

    public static String addMerch(CommandInput commandInput, User user) {
        if (user == null) {
            return "The username " + commandInput.getUsername() + " doesn't exist.";
        } else if (!user.getType().equals("artist")) {
            return commandInput.getUsername() + " is not an artist.";
        }

        Artist artist = (Artist)user ;
        String merchName = commandInput.getName();
        if (artist.getMerch().stream().anyMatch(event -> merchName.equals(event.getName()))) {
            return artist.getUsername() + " has merchandise with the same name.";
        }
        if(!Merch.priceIsValid(commandInput.getPrice())) {
            return "Price for merchandise can not be negative.";
        }

        artist.getMerch().add(new Merch(commandInput.getName(), commandInput.getPrice(),
                commandInput.getDescription()));

        return artist.getUsername() + " has added new merchandise successfully.";
    }
}
