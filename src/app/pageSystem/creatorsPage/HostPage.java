package app.pageSystem.creatorsPage;

import app.Admin;
import app.audio.Collections.Podcast;
import app.audio.Collections.Utilities.forHost.Announcement;
import app.audio.Files.Episode;
import app.users.userTypes.Host;


public final class HostPage {

    private HostPage() {
    }

    /**
     * print host page
     * @param artistName name of host to be printed
     * @return content that should pe printed in a string
     */
    public static String printPage(final String artistName) {
        Host host = (Host) Admin.getUser(artistName);
        StringBuilder printPodcasts = new StringBuilder();
        StringBuilder printEpisode = new StringBuilder();
        StringBuilder printAnnouncement = new StringBuilder();

        printPodcasts.append("[");

        for (Podcast podcast : host.getPodcasts()) {
            printPodcasts.append(podcast.getName()).append(":\n\t[");

            for (Episode episode : podcast.getEpisodes()) {
                printEpisode.append(episode.getName()).append(" - ").
                        append(episode.getDescription());

                if (!(podcast.getEpisodes().indexOf(episode)
                        == podcast.getEpisodes().size() - 1)) {

                    printEpisode.append(", ");
                }
            }

            printEpisode.append("]");
            printPodcasts.append(printEpisode).append("\n");
            printEpisode.setLength(0);

            if (!(host.getPodcasts().indexOf(podcast) == host.getPodcasts().size() - 1)) {
                printPodcasts.append(", ");
            }
        }

        printPodcasts.append("]").append("\n\n");
        printAnnouncement.append("[");

        for (Announcement announcement : host.getAnnouncements()) {
            printAnnouncement.append(announcement.getName()).append(":\n\t")
                    .append(announcement.getDescription());

            if (!(host.getAnnouncements().indexOf(announcement)
                    == host.getAnnouncements().size() - 1)) {

                printAnnouncement.append(", ");
            }
        }

        printAnnouncement.append("\n]");

        return "Podcasts:\n\t" + printPodcasts + "Announcements:\n\t" + printAnnouncement;
    }

}
