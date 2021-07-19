package com.example.listenlikealocal3;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.android.volley.RequestQueue;
import com.example.listenlikealocal3.Connectors.SongService;
import com.example.listenlikealocal3.Model.Playlist;
import com.example.listenlikealocal3.Model.Song;
import com.example.listenlikealocal3.Services.PlaylistItems;
import com.example.listenlikealocal3.Services.Playlists;
import com.example.listenlikealocal3.Services.Songs;
import com.example.listenlikealocal3.databinding.ActivityPlaylistsBinding;

import java.util.ArrayList;

public class PlaylistDetailActivity extends AppCompatActivity {
    private TextView playlistName;
    private TextView songName;
    private Song song;

    private SongService songService;
    private ArrayList<Song> playlistItems;
    private Playlist playlist_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist_details);

        songService = new SongService(getApplicationContext());
        playlistName = (TextView) findViewById(R.id.name_txtView_name);
        songName = (TextView) findViewById(R.id.Songs);
        playlist_id = new Playlist(getApplicationContext());

        SharedPreferences sharedPreferences = this.getSharedPreferences("SPOTIFY", 0);
        String name = sharedPreferences.getString("name", null);
        playlistName.setText(name);

        getTracks();
    }

    private void getTracks() {
        songService.getPlaylistItems(() -> {
            playlistItems = songService.getSongs();
            updateSong();
        }, "37i9dQZF1DX8TvdyVZSYFY");
    }

    private void updateSong() {
        if (playlistItems.size() > 0) {
            songName.setText(playlistItems.get(0).getName());
            song = playlistItems.get(0);
        }
    }

}
