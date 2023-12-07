package app.audio.Collections;


import app.audio.Files.Episode;

import java.util.ArrayList;

public class PodcastOutput {
    private String name;
    private ArrayList<String> episodes;

    public PodcastOutput(String name, ArrayList<String> episodesName) {
        this.name = name;
        this.episodes = episodesName;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getEpisodes() {
        return episodes;
    }

    public static ArrayList<PodcastOutput> podcastOutput(ArrayList<Podcast> podcasts) {
        ArrayList<PodcastOutput> podcastOutput = new ArrayList<>();

        for(Podcast podcast : podcasts) {
            ArrayList<String> episodesName = new ArrayList<>();

            for (Episode episode : podcast.getEpisodes()) {
                episodesName.add(episode.getName());
            }
            podcastOutput.add(new PodcastOutput(podcast.getName(), episodesName));
        }

        return podcastOutput;
    }

}
