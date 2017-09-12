package music;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pbteamstudio.androidutils.R;

import java.util.ArrayList;

/**
 * Adapter with {@link Song}
 * <p>Use with {@link MusicListFragment}</p>
 *
 * @author Pryanichkin S.V. pbteamstudio.com
 * @version 1.0
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class SongAdapter extends BaseAdapter {
    private ArrayList<Song> songs;
    private Context context;

    /**
     * Constructor of this class
     *
     * @param context - current {@link Context}
     * @param songs   - {@link java.util.List} of {@link Song}
     */
    public SongAdapter(Context context, ArrayList<Song> songs) {
        this.songs = songs;
        this.context = context;
    }

    /**
     * Class ViewHolder for pattern.
     */
    private static class ViewHolder {
        TextView songTitle;
        TextView songArtist;
    }

    /**
     * Getters
     */
    @Override
    public int getCount() {
        return songs.size();
    }

    @Override
    public Song getItem(int position) {
        return songs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.song, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.songTitle = (TextView) convertView.findViewById(R.id.song_title);
            viewHolder.songArtist = (TextView) convertView.findViewById(R.id.song_artist);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //get song using position
        Song currSong = getItem(position);
        //get title and artist strings
        viewHolder.songTitle.setText(currSong.getTitle());
        viewHolder.songArtist.setText(currSong.getArtist());
        return convertView;
    }
}