package app.audio.Collections.Utilities.forArtist;

import app.audio.LibraryEntry;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Event extends LibraryEntry {
    private String description;
    private String date;


    public Event(String owner, String description, String date) {
        super(owner);
        this.description = description;
        this.date = date;
    }

    public String getDescription() {
        return description;
    }
    public String getDate() {
        return date;
    }

    public static boolean dateIsValid(String dateString) {
        String datePattern = "dd-MM-yyyy";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(datePattern);
        LocalDate date;
        try {
            date = LocalDate.parse(dateString, formatter);
        } catch (DateTimeParseException e) {
            return false;
        }

        int year = date.getYear();

        if (year < 1900 || year > 2023) {
            return false;
        }

        return true;
    }

}
