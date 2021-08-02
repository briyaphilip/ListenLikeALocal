package com.example.listenlikealocal3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.example.listenlikealocal3.Connectors.SongService;
import com.example.listenlikealocal3.Model.Artist;
import com.example.listenlikealocal3.Model.Playlist;
import com.example.listenlikealocal3.Model.Song;
import com.example.listenlikealocal3.databinding.ActivityPlaylistDetailsBinding;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class PlaylistDetailActivity extends AppCompatActivity {
    private ActivityPlaylistDetailsBinding binding;
    private ArrayList<Song> songsList = new ArrayList<>();
    private ArrayList<String> songNameList = new ArrayList<>();
    private ArrayList<Artist> artistList = new ArrayList<>();
    private ArrayList<String> artistNameList = new ArrayList<>();
    private SongService playlistItems;

    private ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(PlaylistDetailActivity.this, R.layout.activity_playlist_details);

        Playlist playlistObject = (Playlist) Parcels.unwrap(getIntent().getParcelableExtra("playlist"));

        SharedPreferences sp = this.getSharedPreferences("SPOTIFY", 0);
        String name = sp.getString("name", null);
        binding.playlistName.setText(name);
        playlistItems = new SongService(getApplicationContext());
        String playlist_id = "";
        playlist_id = playlistObject.getId();
        getSongsForListView(playlist_id);

        arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                songNameList
        );
    }

    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void getSongsForListView(String playlist_id){
        playlistItems.getPlaylistItems(() -> {
            songsList = playlistItems.getSongs();
            artistList = playlistItems.getArtist();
            updateListView(songsList, artistList);
        }, playlist_id);
    }

    private void updateListView(ArrayList<Song> s, ArrayList<Artist> a) {
        songsList = s;
        artistList = a;

        for (int i = 0; i < songsList.size(); i++) {
            Song song = songsList.get(i);
            Artist artist = artistList.get(i);
            Log.i("TAG", song.getName() + " by " + artist.getName());
            songNameList.add(song.getName() + " by " + artist.getName());
            artistNameList.add(artist.getName());
        }

        binding.SongListview.setAdapter(arrayAdapter);
        binding.SongListview.setOnItemClickListener((parent, view, position, id) -> {
            String name = songNameList.get(position);

            Toast.makeText(this, "Clicked"+name, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ArtistDetailActivity.class);
            intent.putExtra("artist", Parcels.wrap(artistNameList.get(position)));
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

    }

}



