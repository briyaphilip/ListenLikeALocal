package com.example.listenlikealocal3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.listenlikealocal3.Model.Playlist;
import com.example.listenlikealocal3.Services.SpotifyClient;
import com.example.listenlikealocal3.databinding.ActivityPlaylistsBinding;


import org.parceler.Parcels;

import java.util.ArrayList;

public class PlaylistActivity extends AppCompatActivity {
    private ActivityPlaylistsBinding binding;
    private ArrayList<Playlist> playlistsList = new ArrayList<>();
    private ArrayList<String> playlistNameList = new ArrayList<>();
    private SpotifyClient playlists;

    private ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlists);

        String country_code = Parcels.unwrap(getIntent().getParcelableExtra("country_code"));
        String limit = "20";

        binding = DataBindingUtil.setContentView(PlaylistActivity.this, R.layout.activity_playlists);
        SharedPreferences sp = this.getSharedPreferences("SPOTIFY", 0);
        String name = sp.getString("name", null);
        binding.nameTxtViewName.setText(name);
        playlists = new SpotifyClient(getApplicationContext());

        getPlaylistsForListView(country_code, limit);

        arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                playlistNameList
        );
    }

    private void getPlaylistsForListView(String country_code, String limit){
        playlists.getFeaturedPlaylists(() -> {
            playlistsList = playlists.getPlaylists();
            updateListView(playlistsList);
        }, country_code, limit);
    }

    private void updateListView(ArrayList<Playlist> p) {
        playlistsList = p;
        for(Playlist playlist : p ){
            playlistNameList.add(playlist.getName());
        }

        binding.PlaylistListview.setAdapter(arrayAdapter);

        binding.PlaylistListview.setOnItemClickListener((parent, view, position, id) -> {
            String name = playlistNameList.get(position);

            Toast.makeText(this, "Clicked"+name, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, PlaylistDetailActivity.class);
            intent.putExtra("playlist", Parcels.wrap(p.get(position)));
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
    }

}

