package app.pageSystem.creatorsPage;

import app.Admin;
import app.audio.Collections.Album;
import app.audio.Collections.Utilities.forArtist.Event;
import app.audio.Collections.Utilities.forArtist.Merch;
import app.users.userTypes.Artist;

import java.util.ArrayList;

public final class ArtistPage {

    private ArtistPage() {
    }

    /**
     * print artist page
     * @param artistName name of artist to be printed
     * @return content that should pe printed in a string
     */
    public static String printPage(final String artistName) {
        Artist artist = (Artist) Admin.getUser(artistName);
        ArrayList<String> albumNames = new ArrayList<>();
        for (Album album : artist.getAlbums()) {
            albumNames.add(album.getName());
        }

        StringBuilder infoMerch = new StringBuilder();
        infoMerch.append("[");
        for (Merch merch : artist.getMerch()) {
            infoMerch.append(merch.getName()).append(" - ").append(merch.getPrice()).append(":\n\t")
                    .append(merch.getDescription());

            if (!(artist.getMerch().indexOf(merch) == artist.getMerch().size() - 1)) {
                infoMerch.append(", ");
            }
        }
        infoMerch.append("]");

        StringBuilder infoEvent = new StringBuilder();
        infoEvent.append("[");
        for (Event event : artist.getEvents()) {
            infoEvent.append(event.getName()).append(" - ").append(event.getDate()).append(":\n\t")
                    .append(event.getDescription());

            if (!(artist.getEvents().indexOf(event) == artist.getEvents().size() - 1)) {
                infoEvent.append(", ");
            }
        }
        infoEvent.append("]");

        return "Albums:\n\t" + albumNames + "\n\n" + "Merch:\n\t" + infoMerch + "\n\n"
                + "Events:\n\t" + infoEvent;
    }
}
