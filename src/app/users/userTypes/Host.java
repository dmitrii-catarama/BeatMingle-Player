package app.users.userTypes;

import app.audio.Collections.Podcast;
import app.users.User;

import java.util.ArrayList;

public class Host extends User {
    private ArrayList<Podcast> podcasts;
    private ArrayList<String> announcements;

    public Host(String username, int age, String city) {
        super(username, age, city);
        super.setType("host");

        podcasts = new ArrayList<>();
        announcements = new ArrayList<>();

    }

    public ArrayList<Podcast> getPodcasts() {
        return podcasts;
    }
    public void setPodcasts(ArrayList<Podcast> podcasts) {
        this.podcasts = podcasts;
    }

    public ArrayList<String> getAnnouncements() {
        return announcements;
    }
    public void setAnnouncements(ArrayList<String> announcements) {
        this.announcements = announcements;
    }
}
