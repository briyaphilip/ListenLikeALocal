package com.example.listenlikealocal3;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.android.volley.RequestQueue;
import com.example.listenlikealocal3.Connectors.SongService;
import com.example.listenlikealocal3.Model.Song;
import com.example.listenlikealocal3.databinding.ActivityPlaylistDetailsBinding;

import java.util.ArrayList;

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
        SharedPreferences sp = this.getSharedPreferences("SPOTIFY", 0);
        String name = sp.getString("name", null);
        binding.playlistName.setText(name);
        playlistItems = new SongService(getApplicationContext());
        getSongsForListView();

        arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                songNameList
        );
    }

    private void getSongsForListView(){
        playlistItems.getPlaylistItems(() -> {
            songsList = playlistItems.getSongs();
            updateListView(songsList);
        }, "37i9dQZF1DX8TvdyVZSYFY");
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

