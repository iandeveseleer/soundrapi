package fr.slashandroses.musicapi.model;

import lombok.Data;

/**
 * Created by snr on 20/10/2017.
 */

public @Data
class Song {

    private String artist;
    private String album;
    private String trackNb;
    private String track;
    private String tag;
    private String url;
    private String year;
}
