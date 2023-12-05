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

    public User(String username, int age, String city) {
        this.username = username;
        this.age = age;
        this.city = city;
        type = "user";
    }

    public void setType(String type) {
        this.type = type;
    }

    public static String addUser(CommandInput commandInput) {
        String newUserName = commandInput.getUsername();
        User newUser = Admin.getUser(newUserName);

        if (newUser != null) {
            return "The username " + newUserName + " is already taken.";
        }

        String newUserType = commandInput.getType();
        switch(newUserType) {
            case "user" -> newUser = new NormalUser(newUserName, commandInput.getAge(), commandInput.getCity());
            case "artist" -> newUser = new Artist(newUserName, commandInput.getAge(), commandInput.getCity());
            case "host" -> newUser = new Host(newUserName, commandInput.getAge(), commandInput.getCity());
            default -> System.out.println("not a valid type of new user");
        }

        Admin.setUser(newUser);

        return "The username " + newUserName + " has been added successfully.";
    }


}
