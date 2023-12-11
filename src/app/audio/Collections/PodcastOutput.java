package app.audio.Collections;


import app.audio.Files.Episode;

import java.util.ArrayList;

public class PodcastOutput {
    private String name;
    private ArrayList<String> episodes;

    public PodcastOutput(final String name, final ArrayList<String> episodesName) {
        this.name = name;
        this.episodes = episodesName;
    }

    /**
     * Gets name of the podcast.
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets episodes names of the podcast.
     * @return array list of episodes names
     */
    public ArrayList<String> getEpisodes() {
        return episodes;
    }

    /**
     * Transform podcasts to the output format.
     * @param podcasts podcasts
     * @return array list of podcast output
     */
    public static ArrayList<PodcastOutput> podcastOutput(final ArrayList<Podcast> podcasts) {
        ArrayList<PodcastOutput> podcastOutput = new ArrayList<>();

        for (Podcast podcast : podcasts) {
            ArrayList<String> episodesName = new ArrayList<>();

            for (Episode episode : podcast.getEpisodes()) {
                episodesName.add(episode.getName());
            }
            podcastOutput.add(new PodcastOutput(podcast.getName(), episodesName));
        }

        return podcastOutput;
    }

}
