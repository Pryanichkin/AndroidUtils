package sound;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Singleton class for play sounds
 * <p>!Sound should be in folder <q>main/assets</q></p>
 *
 * @author Pryanichkin S.V. pbteamstudio.com
 * @version 1.0
 */

@SuppressWarnings("unused")
class SoundHelper {
    private static SoundHelper instance;
    private SoundPool soundPool;
    private AssetManager assetManager;
    private int lastPlayedSoundId;
    private Map<String, Integer> sounds;

    /**
     * Get instance of this class
     *
     * @param activity   - current Activity
     * @param soundNames - Array of the soundNames (name.extension)
     * @return instance of {@link SoundHelper}
     */
    public static SoundHelper getInstance(Activity activity, String[] soundNames) {
        if (instance == null) {
            instance = new SoundHelper(activity);
            instance.setSoundIDAndLoadIt(soundNames);
        }
        return instance;
    }

    /**
     * Private constructor of this class
     * <p>Also assignment of initial value of variable {@link SoundHelper#assetManager} and
     * {@link SoundHelper#sounds}</p>
     *
     * @param activity - current Activity
     */
    private SoundHelper(Activity activity) {
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            // Для устройств до Android 5.0
            createOldSoundPool();
        } else {
            // Для новых устройств
            createNewSoundPool();
        }

        assetManager = activity.getAssets();
        sounds = new HashMap<>();
    }

    /**
     * Create {@link SoundPool} for Api >= {@link android.os.Build.VERSION_CODES#LOLLIPOP}
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void createNewSoundPool() {
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        soundPool = new SoundPool.Builder()
                .setAudioAttributes(attributes)
                .setMaxStreams(3)
                .build();
    }

    /**
     * Create {@link SoundPool} for Api < {@link android.os.Build.VERSION_CODES#LOLLIPOP}
     */
    @SuppressWarnings("deprecation")
    private void createOldSoundPool() {
        soundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
    }

    private void setSoundIDAndLoadIt(String[] soundNames) {
        for (String soundName : soundNames) {
            sounds.put(soundName, loadSound(soundName));
        }
    }

    /**
     * Free resources and release {@link SoundPool}
     */
    public void unLoadSoundsAndFreeResources() {
        soundPool.release();
        soundPool = null;
        instance = null;
    }

    /**
     * Play sound with given soundId
     *
     * @param soundId - ID of sound received by {@link SoundHelper#loadSound(String)}
     * @return true, if load was successful and false, if not
     */
    private boolean playSound(int soundId) {
        if (lastPlayedSoundId != 0) soundPool.stop(lastPlayedSoundId);
        if (soundId > 0) {
            lastPlayedSoundId = soundId;
            soundPool.play(soundId, 1, 1, 1, 0, 1);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Play sound with given soundId
     *
     * @param soundName - name of the Sound (name.extension)
     * @return int>0, if play was successful and (-1), if not
     */
    public boolean playSound(String soundName) {
        int soundId = sounds.get(soundName);
        return playSound(soundId > 0 ? soundId : (-1));
    }

    /**
     * Load sound with {@link AssetFileDescriptor} and get int id
     *
     * @param soundName - name of the Sound (name.extension)
     * @return int>0, if load was successful and (-1), if not
     */
    private int loadSound(String soundName) {
        AssetFileDescriptor afd;
        try {
            afd = assetManager.openFd(soundName);
        } catch (IOException e) {
            return -1;
        }
        return soundPool.load(afd, 1);
    }

    /**
     * Getter for a Map of soundNames-soundIds
     *
     * @return {@link SoundHelper#sounds}
     */
    public Map<String, Integer> getSounds() {
        return sounds;
    }
}
