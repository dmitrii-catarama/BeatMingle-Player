package app.audio.Collections.Utilities.forHost;

import app.audio.LibraryEntry;

public class Announcement extends LibraryEntry {
    private String description;

    public Announcement(final String name, final String description) {
        super(name);
        this.description = description;
    }

    /**
     * Gets the announcement description.
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the announcement description.
     * @param description description of the announcement
     */
    public void setDescription(final String description) {
        this.description = description;
    }

}
