package app.users.userTypes;

import app.Admin;
import app.audio.Collections.PodcastOutput;
import app.audio.Collections.Podcast;
import app.audio.Collections.Utilities.forHost.Announcement;
import app.audio.Files.Episode;
import app.users.User;
import app.utils.Enums;
import fileio.input.CommandInput;
import fileio.input.EpisodeInput;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

    public static String removePodcast(CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());

        if (user == null) {
            return "The username " + commandInput.getUsername() + " doesn't exist.";
        } else if (!user.getType().equals("host")) {
            return user.getUsername() + " is not a host.";
        } else if (!((Host)user).hostHasPodcast(commandInput.getName())) {
            return user.getUsername() + " doesn't have a podcast with the given name.";
        }

        List<NormalUser> normalUsers = Admin.getNormalUsers();
        Host host = (Host) user;
        Podcast podcastToDelete = new Podcast();

        for (Podcast podcast : host.getPodcasts()) {
            if (podcast.getName().equals(commandInput.getName())) {
                podcastToDelete = podcast;
                break;
            }
        }

        // Verificare daca nimeni din useri normali nu are incarcat podcastul
        for (NormalUser normalUser : normalUsers) {
            if (normalUser.getCurrentAudioCollectionName() != null
                && normalUser.getCurrentAudioCollectionName().equals(podcastToDelete.getName())) {

                return user.getUsername() + " can't delete this podcast.";
            }
        }

        Admin.getAdminPodcasts().remove(podcastToDelete);
        host.podcasts.remove(podcastToDelete);

        return host.getUsername() + " deleted the podcast successfully.";

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

//        if (host.getAnnouncements().stream().noneMatch
//                (announcement -> announcementName.equals(announcement.getName()))) {
//            return host.getUsername() + " has no announcement with the given name.";
//        }

        for (Announcement announcement : host.getAnnouncements()) {
            if (announcement.getName().equals(announcementName)) {
                host.getAnnouncements().remove(announcement);
                return host.getUsername() + " has successfully deleted the announcement.";
            }
        }

        return host.getUsername() + " has no announcement with the given name.";
    }

    public boolean deleteHostData() {
        List<NormalUser> normalUsers = Admin.getNormalUsers();

        //verificare daca nici un user normal nu se afla pe pagina artistului
        for (NormalUser normalUser : normalUsers) {
            if (normalUser.getConnectionStatus().equals(Enums.connectionStatus.OFFLINE)) {
                continue;
            }

            String lastSearchType = normalUser.getSearchBar().getLastSearchType();
            if (lastSearchType == null || !lastSearchType.equals("host")) {
                continue;
            }


            if (normalUser.getSearchBar().getSelectedUserPage() == null) {
                continue;
            }

            String pageSet = normalUser.getSearchBar().getSelectedUserPage().toString();
            if (normalUser.getSearchBar().getSelectedPageType() != null) {
                if (this.getUsername().equals(pageSet)) {
                    return false;
                }
            }

        }

        // Verificare daca nimeni din useri normali nu are incarcat podcastul
        for (NormalUser normalUser : normalUsers) {
            for (Podcast podcast : this.podcasts) {
                if (normalUser.getCurrentAudioCollectionName() != null
                        && normalUser.getCurrentAudioCollectionName().equals(podcast.getName())) {

                    return false;
                }
            }
        }

        for (Podcast podcast : this.podcasts) {
            Admin.getAdminPodcasts().remove(podcast);
        }

        podcasts.clear();
        announcements.clear();

        return true;
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
