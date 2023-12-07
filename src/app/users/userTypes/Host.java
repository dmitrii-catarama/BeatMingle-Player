package app.users.userTypes;

import app.Admin;
import app.audio.Collections.PodcastOutput;
import app.audio.Collections.Podcast;
import app.audio.Collections.Utilities.forHost.Announcement;
import app.audio.Files.Episode;
import app.users.User;
import fileio.input.CommandInput;
import fileio.input.EpisodeInput;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Host extends User {
    private ArrayList<Podcast> podcasts;
    private ArrayList<Announcement> announcements;

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

    public void setPodcast(Podcast podcast) {
        podcasts.add(podcast);
    }

    public ArrayList<Announcement> getAnnouncements() {
        return announcements;
    }
    public void setAnnouncements(ArrayList<Announcement> announcements) {
        this.announcements = announcements;
    }


    public static String addPodcast(CommandInput commandInput, User user) {
        if (user == null) {
            return "The username " + user.getUsername() + " doesn't exist.";
        } else if (!user.getType().equals("host")) {
            return user.getUsername() + " is not a host.";
        } else if (((Host)user).hostHasPodcast(commandInput.getName())) {
            return user.getUsername() + " has another podcast with the same name.";
        }

        ArrayList<EpisodeInput> episodesInput = commandInput.getEpisodes();

        Set<String> set = new HashSet<>();
        boolean episodeHasDuplicate = false;
        for (EpisodeInput episode : episodesInput) {
            if (!set.add(episode.getName())) {
                // if the element is already in the set, it's a duplicate
                episodeHasDuplicate = true;
            }
        }
        if (episodeHasDuplicate) {
            return user.getUsername() + " has the same episode in this podcast.";
        }

        String podcastName = commandInput.getName();
        String owner = commandInput.getUsername();
        ArrayList<Episode> episodes = new ArrayList<>();

        for (EpisodeInput episodeInput : episodesInput) {
            episodes.add(new Episode(episodeInput.getName(), episodeInput.getDuration(),
                    episodeInput.getDescription()));
        }

        Podcast newPodcast = new Podcast(podcastName, owner, episodes);
        Admin.setPodcast(newPodcast);
        ((Host)user).setPodcast(newPodcast);

        return user.getUsername() + " has added new podcast successfully.";
    }

    public static ArrayList<PodcastOutput> showPodcasts(User user) {
        Host host = (Host)user;

        if(host.getPodcasts() == null) {
            System.out.println("No podcasts:(");
            return null;
        }

        return PodcastOutput.podcastOutput(host.getPodcasts());
    }

    public static String addAnnouncement(CommandInput commandInput, User user) {
        if (user == null) {
            return "The username " + commandInput.getUsername() + "doesn't exist.";
        } else if (!user.getType().equals("host")) {
            return commandInput.getUsername() + " is not a host.";
        }

        Host host = (Host)user;
        String announcementName = commandInput.getName();

        if (host.getAnnouncements().stream().anyMatch
                (announcement -> announcementName.equals(announcement.getName()))) {
            return host.getUsername() + " has already added an announcement with this name.";
        }

        host.getAnnouncements().add(new Announcement(announcementName, commandInput.getDescription()));

        return host.getUsername() + " has successfully added new announcement.";
    }

    public static String removeAnnouncement(CommandInput commandInput, User user) {
        if (user == null) {
            return "The username " + commandInput.getUsername() + "doesn't exist.";
        } else if (!user.getType().equals("host")) {
            return commandInput.getUsername() + " is not a host.";
        }

        Host host = (Host)user;
        String announcementName = commandInput.getName();

        if (host.getAnnouncements().stream().noneMatch
                (announcement -> announcementName.equals(announcement.getName()))) {
            return host.getUsername() + " has no announcement with the given name.";
        }

        for (Announcement announcement : host.getAnnouncements()) {
            if (announcement.getName().equals(announcementName)) {
                host.getAnnouncements().remove(announcement);
            }
        }

        return host.getUsername() + " has successfully deleted the announcement.";
    }

    public boolean hostHasPodcast(String podcastName) {
        if (this.podcasts == null) {
            return false;
        } else {
            for (Podcast podcast : this.podcasts) {
                if (podcast.getName().equals(podcastName)) {
                    return true;
                }
            }
        }

        return false;
    }
}
