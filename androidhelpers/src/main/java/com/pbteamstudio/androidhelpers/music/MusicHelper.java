package com.pbteamstudio.androidhelpers.music;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * Singleton class for play com.pbteamstudio.androidhelpers.music from {@link Song}
 *
 * @author Pryanichkin S.V. pbteamstudio.com
 * @version 1.0
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class MusicHelper {
    private static MusicHelper instance;
    private MediaPlayer musicPlayer;
    private Map<Long, Integer> timePos;
    private Song curSong;

    /**
     * Private constructor of this class
     * <p>Also assignment of initial value of variable {@link MusicHelper#timePos}</p>
     */
    @SuppressLint("UseSparseArrays")
    private MusicHelper() {
        timePos = new HashMap<>();
    }

    /**
     * Get instance of this class
     *
     * @return instance of {@link MusicHelper}
     */
    public static MusicHelper getInstance() {
        if (instance == null) {
            instance = new MusicHelper();
        }
        return instance;
    }

    /**
     * Play com.pbteamstudio.androidhelpers.music with given parameters
     *
     * @param activity            - current {@link Activity}
     * @param song                - {@link Song}
     * @param isPlayFromBeginning - if true, play from beginning, else - from paused
     * @param isLooping           - looping forever if true
     * @param leftVolume          - from 0.0f to 1.0f
     * @param rightVolume         - from 0.0f to 1.0f
     * @see MusicHelper#playMusic(Activity, Song)
     */
    public void playMusic(Activity activity, Song song, boolean isPlayFromBeginning,
                          boolean isLooping, float leftVolume, float rightVolume) {
        try {
            Uri songUri = ContentUris.withAppendedId(
                    android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, song.getId());
            playMusicFromURI(activity, songUri, song, isPlayFromBeginning, isLooping, leftVolume,
                    rightVolume);
            curSong = song;
        } catch (Exception e) {
            Log.e("MusicHelper", "Problem with get or play com.pbteamstudio.androidhelpers.music fro, Uri");
        }
    }

    /**
     * Play com.pbteamstudio.androidhelpers.music with standard parameters (Play from paused, looping forever, volume 1.0f)
     *
     * @param activity - current {@link Activity}
     * @param song     - {@link Song}
     */
    public void playMusic(Activity activity, Song song) {
        try {
            Uri songUri = ContentUris.withAppendedId(
                    android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, song.getId());
            playMusicFromURI(activity, songUri, song, true, true, 1.0f, 1.0f);
            curSong = song;
        } catch (Exception e) {
            Log.e("MusicHelper", "Problem with get or play com.pbteamstudio.androidhelpers.music fro, Uri");
        }
    }

    /**
     * Private method to play com.pbteamstudio.androidhelpers.music with given parameters
     *
     * @param activity            - current {@link Activity}
     * @param uri                 - {@link Uri} of user's song
     * @param song                - {@link Song}
     * @param isPlayFromBeginning - if true, play from beginning, else - from paused
     * @param isLooping           - looping forever if true
     * @param leftVolume          - from 0.0f to 1.0f
     * @param rightVolume         - from 0.0f to 1.0f
     */
    private void playMusicFromURI(Activity activity, Uri uri, Song song, boolean isPlayFromBeginning,
                                  boolean isLooping, float leftVolume, float rightVolume) {
        stopPlayer();

        int time = 0;
        if (!isPlayFromBeginning) {
            try {
                time = timePos.get(song.getId());
            } catch (Exception e) {
                time = 0;
            }
        }

        musicPlayer = MediaPlayer.create(activity, uri);
        musicPlayer.setLooping(isLooping);
        musicPlayer.start();
        musicPlayer.seekTo(time);
        musicPlayer.setVolume(leftVolume, rightVolume);
    }

    /**
     * Stop play com.pbteamstudio.androidhelpers.music, release resources and clear current song's time position
     */
    public void stopPlayer() {
        if (musicPlayer != null) {

            clearCurSongTimePos();

            musicPlayer.stop();
            musicPlayer.release();
            musicPlayer = null;
        }
    }

    /**
     * Pause play com.pbteamstudio.androidhelpers.music, release resources and remember current song's time position
     */
    public void pausePlayer() {
        if (musicPlayer != null) {

            timePos.put(curSong.getId(), musicPlayer.getCurrentPosition());

            musicPlayer.stop();
            musicPlayer.release();
            musicPlayer = null;
        }
    }

    /**
     * Set all song's time positions to 0
     */
    public void clearAllTimePos() {
        for (Map.Entry<Long, Integer> times : timePos.entrySet()) {
            times.setValue(0);
        }
    }

    /**
     * Set current song time position to 0
     */
    public void clearCurSongTimePos() {
        for (Map.Entry<Long, Integer> times : timePos.entrySet()) {
            if (curSong.getId() == times.getKey()) {
                times.setValue(0);
            }
        }
    }

    /**
     * Search for user's com.pbteamstudio.androidhelpers.music on device and return {@link ArrayList} for {@link SongAdapter}
     * @param activity - current {@link Activity}
     * @return sorted by Artist{@link ArrayList} of user's songs
     */
    public ArrayList<Song> searchForMusic(Activity activity) {
        ArrayList<Song> songList = new ArrayList<>();
        ContentResolver musicResolver = activity.getContentResolver();
        Uri musicUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        Cursor musicCursor = musicResolver.query(musicUri, null, null, null, null);

        if (musicCursor != null && musicCursor.moveToFirst()) {
            int titleColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int idColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media._ID);
            int artistColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);

            do {
                long id = musicCursor.getLong(idColumn);
                String title = musicCursor.getString(titleColumn);
                String artist = musicCursor.getString(artistColumn);
                songList.add(new Song(id, title, artist));
            } while (musicCursor.moveToNext());

            musicCursor.close();
        }
        Collections.sort(songList, new Comparator<Song>() {
            @Override
            public int compare(Song o1, Song o2) {
                return o1.getArtist().compareTo(o2.getArtist());
            }
        });

        return songList;
    }
}
