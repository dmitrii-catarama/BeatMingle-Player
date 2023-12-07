package app.audio.Collections.Utilities.forHost;

import app.audio.LibraryEntry;

public class Announcement extends LibraryEntry {
    private String description;

    public Announcement(String name, String description) {
        super(name);
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

}
