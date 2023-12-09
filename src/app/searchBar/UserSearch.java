package app.searchBar;

import app.audio.LibraryEntry;
import app.users.User;


import java.util.ArrayList;
import java.util.List;

/**
 * For search artists and hosts
 */
public class UserSearch extends LibraryEntry {


    public UserSearch(String creatorName) {
        super(creatorName);
    }

    public static ArrayList<LibraryEntry> getCreators(List<User> users, String type) {
        ArrayList<LibraryEntry> artistsName = new ArrayList<>();

        if (type.equals("artist")) {
            for (User user : users) {
                if (user.getType().equals("artist")) {
                    artistsName.add(new UserSearch(user.getUsername()));
                }
            }
        } else {
            for (User user : users) {
                if (user.getType().equals("host")) {
                    artistsName.add(new UserSearch(user.getUsername()));
                }
            }
        }

        return artistsName;
    }
}
