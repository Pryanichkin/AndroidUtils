package music;

import android.annotation.SuppressLint;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.pbteamstudio.androidutils.R;

import java.util.ArrayList;


/**
 * Example of {@link ListFragment}, that work with {@link SongAdapter}
 *
 * @author Pryanichkin S.V. pbteamstudio.com
 * @version 1.0
 */
@SuppressWarnings("unused")
public class MusicListFragment extends ListFragment {
    private MusicHelper musicHelper;
    private ArrayList<Song> songs;

    /**
     * Required empty public constructor
     */
    public MusicListFragment() {
    }

    /**
     * Output list of user's songs
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        musicHelper = MusicHelper.getInstance();
        songs = musicHelper.searchForMusic(getActivity());
        SongAdapter adapter = new SongAdapter(getActivity(), songs);
        setListAdapter(adapter);
    }

    @SuppressLint("InflateParams")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_music, null);
    }

    /**
     * OnClick on user's song in the list will play it
     */
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        //super.onListItemClick(l, v, position, id);
        Song currSong = songs.get(position);
        musicHelper.playMusic(getActivity(), currSong);
    }
}
