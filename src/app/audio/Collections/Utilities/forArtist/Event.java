package app.audio.Collections.Utilities.forArtist;

import app.audio.LibraryEntry;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Event extends LibraryEntry {
    private String description;
    private String date;
    private static final Integer MINIMAL_YEAR = 1900;
    private static final Integer MAXIMAL_YEAR = 2023;


    public Event(final String owner, final String description, final String date) {
        super(owner);
        this.description = description;
        this.date = date;
    }

    /**
     * Gets the event description.
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the event date.
     * @return event date
     */
    public String getDate() {
        return date;
    }

    /**
     * Verify if the new event date is valid.
     * @param dateString the event date
     * @return boolean
     */
    public static boolean dateIsValid(final String dateString) {
        String datePattern = "dd-MM-yyyy";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(datePattern);
        LocalDate date;
        try {
            date = LocalDate.parse(dateString, formatter);
        } catch (DateTimeParseException e) {
            return false;
        }

        int year = date.getYear();

        if (year < MINIMAL_YEAR || year > MAXIMAL_YEAR) {
            return false;
        }

        return true;
    }

}
