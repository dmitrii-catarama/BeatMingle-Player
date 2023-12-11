package app.users;

import app.Admin;
import app.users.userTypes.Artist;
import app.users.userTypes.Host;
import app.users.userTypes.NormalUser;
import fileio.input.CommandInput;
import lombok.Getter;

public class User {
    @Getter
    private String username;
    @Getter
    private int age;
    @Getter
    private String city;
    @Getter
    private String type;

    public User(final String username, final int age, final String city) {
        this.username = username;
        this.age = age;
        this.city = city;
        type = "user";
    }

    /**
     * Sets the type of the user.
     * @param type the user type
     */
    public void setType(final String type) {
        this.type = type;
    }

    /**
     * Add a new user command.
     * @param commandInput the command input
     * @return the message of the command
     */
    public static String addUser(final CommandInput commandInput) {
        String newUserName = commandInput.getUsername();
        User newUser = Admin.getUser(newUserName);

        if (newUser != null) {
            return "The username " + newUserName + " is already taken.";
        }

        String newUserType = commandInput.getType();
        switch (newUserType) {
            case "user":
                newUser = new
                        NormalUser(newUserName, commandInput.getAge(), commandInput.getCity());
                break;
            case "artist":
                newUser = new Artist(newUserName, commandInput.getAge(), commandInput.getCity());
                break;
            case "host":
                newUser = new Host(newUserName, commandInput.getAge(), commandInput.getCity());
                break;
            default: System.out.println("not a valid type of new user");
        }

        Admin.setUser(newUser);

        return "The username " + newUserName + " has been added successfully.";
    }


}
