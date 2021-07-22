package com.example.listenlikealocal3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.example.listenlikealocal3.Model.Artist;
import com.example.listenlikealocal3.Model.Playlist;
import com.example.listenlikealocal3.Services.Playlists;
import com.example.listenlikealocal3.Services.Songs;
import com.example.listenlikealocal3.databinding.ActivityPlaylistsBinding;


import java.util.ArrayList;

public class PlaylistActivity extends AppCompatActivity {
    private ActivityPlaylistsBinding binding;
    private ArrayList<Playlist> playlistsList = new ArrayList<>();
    private ArrayList<String> playlistNameList = new ArrayList<>();;
    private Playlists playlists;
    private RequestQueue q;

    //array adapter for list view
    private ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlists);
        //data binding for easy access
        binding = DataBindingUtil.setContentView(PlaylistActivity.this, R.layout.activity_playlists);
        SharedPreferences sp = this.getSharedPreferences("SPOTIFY", 0);
        String name = sp.getString("name", null);
        binding.nameTxtViewName.setText(name);
        playlists = new Playlists(getApplicationContext());
        getPlaylistsForListView();

        arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                playlistNameList
        );
    }

    private void getPlaylistsForListView(){
        playlists.getFeaturedPlaylists(() -> {
            playlistsList = playlists.getPlaylists();
            updateListView(playlistsList);
        }, "ES");
    }

    private void updateListView(ArrayList<Playlist> p) {
        playlistsList = p;
        for(Playlist playlist : p ){
            playlistNameList.add(playlist.getName());
        }

        binding.PlaylistListview.setAdapter(arrayAdapter);
        binding.PlaylistListview.setOnItemClickListener((parent, view, position, id) -> {
            String name = playlistNameList.get(position);

        });


    }

}

