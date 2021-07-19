package com.example.listenlikealocal3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.example.listenlikealocal3.Model.Artist;
import com.example.listenlikealocal3.Services.Songs;
import com.example.listenlikealocal3.databinding.ActivityStreamBinding;

import java.util.ArrayList;

public class StreamActivity extends AppCompatActivity {
    private ActivityStreamBinding binding;
    private ArrayList<Artist> artists = new ArrayList<>();
    private ArrayList<String> artistNameList = new ArrayList<>();;
    private Songs songs;
    private RequestQueue q;

    //array adapter for list view
    private ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //data binding for easy access
        binding = DataBindingUtil.setContentView(StreamActivity.this, R.layout.activity_stream);
        SharedPreferences sp = this.getSharedPreferences("SPOTIFY", 0);
        String name = sp.getString("name", null);
        binding.nameTxtViewName.setText(name);
        songs = new Songs(getApplicationContext());
        getArtistsForListView();

        arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                artistNameList
        );
    }

    private void getArtistsForListView(){
        songs.getTopArtists(() -> {
            artists = songs.getArtists();
            updateListView(artists);
        });
    }

    private void updateListView(ArrayList<Artist> a) {
        artists = a;
        for(Artist artist : a ){
            artistNameList.add(artist.getName());
        }

        binding.ArtistListview.setAdapter(arrayAdapter);
        binding.ArtistListview.setOnItemClickListener((parent, view, position, id) -> {
            String name = artistNameList.get(position);

        });
    }
}