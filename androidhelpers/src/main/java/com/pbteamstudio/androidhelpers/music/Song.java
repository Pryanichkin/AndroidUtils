package com.pbteamstudio.androidhelpers.music;

import java.io.Serializable;

/**
 * Secondary class for MusicHelper
 *
 * @author Pryanichkin S.V. pbteamstudio.com
 * @version 1.0
 */

@SuppressWarnings({"unused", "WeakerAccess"})
public class Song implements Serializable {
    private long id;
    private String title;
    private String artist;

    /**
     * Public constructor of this class
     *
     * @param id     - song's id
     * @param title  - song's title
     * @param artist - song's artist
     */
    public Song(long id, String title, String artist) {
        this.id = id;
        this.title = title;
        this.artist = artist;
    }

    /**
     * Getters for id, title, artist
     */
    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }
}
