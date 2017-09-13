package com.pbteamstudio.androidhelpers.music;

import android.annotation.SuppressLint;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.pbteamstudio.androidhelpers.R;

import java.util.ArrayList;


/**
 * Example of {@link ListFragment}, that work with {@link SongAdapter}
 *
 * @author Pryanichkin S.V. pbteamstudio.com
 * @version 1.0
 */
@SuppressWarnings("unused")
public class MusicListFragment extends ListFragment implements View.OnClickListener {
    private MusicHelper musicHelper;
    private ArrayList<Song> songs;

    /**
     * Required empty public constructor
     */
    public MusicListFragment() {
    }

    @SuppressLint("InflateParams")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_music, null);
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

        setListeners();
    }

    /**
     * Set listeners for buttons Close and Choose
     */
    private void setListeners() {
        View view = getView();
        if (view != null) {
            Button btnChoose = (Button) view.findViewById(R.id.choose_button);
            Button btnClose = (Button) view.findViewById(R.id.close_button);

            btnClose.setOnClickListener(this);
            btnChoose.setOnClickListener(this);
        }
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

    /**
     * Standard onClick callback for buttons
     *
     * @param v - current {@link View}
     */
    @Override
    public void onClick(View v) {
        reactForClick(v);
    }

    /**
     * Reaction for clicked on buttons (must be overridden).
     * <p>All buttons just stop the playing</p>
     *
     * @param v - current {@link View}
     */
    public void reactForClick(View v) {
        musicHelper.stopPlayer();
    }
}
