package com.example.listenlikealocal3;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.example.listenlikealocal3.Connectors.SongService;
import com.example.listenlikealocal3.Model.Playlist;
import com.example.listenlikealocal3.Model.Song;
import com.example.listenlikealocal3.databinding.ActivityPlaylistDetailsBinding;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

public class PlaylistDetailActivity extends AppCompatActivity {
    private ActivityPlaylistDetailsBinding binding;
    private ArrayList<Song> songsList = new ArrayList<>();
    private ArrayList<String> songNameList = new ArrayList<>();;
    private SongService playlistItems;
    private RequestQueue q;

    //array adapter for list view
    private ArrayAdapter arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //data binding for easy access
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

    private void getSongsForListView(String playlist_id){
        playlistItems.getPlaylistItems(() -> {
            songsList = playlistItems.getSongs();
            updateListView(songsList);
        }, playlist_id);
    }

    private void updateListView(ArrayList<Song> s) {
        songsList = s;
        for(Song song : s ){
            songNameList.add(song.getName());
        }

        binding.SongListview.setAdapter(arrayAdapter);
        binding.SongListview.setOnItemClickListener((parent, view, position, id) -> {
            String name = songNameList.get(position);

        });


    }

}



