package com.example.listenlikealocal3.Services;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.listenlikealocal3.Connectors.AsyncHandler;
import com.example.listenlikealocal3.Model.Artist;
import com.example.listenlikealocal3.Model.Playlist;
import com.example.listenlikealocal3.Model.Song;
import com.example.listenlikealocal3.Model.User;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PlaylistItems {
    private ArrayList<Song> songs = new ArrayList<Song>();
    private SharedPreferences sp;
    private RequestQueue q;
    private String country_code;
    private User user;

    public PlaylistItems(Context context){
        sp = context.getSharedPreferences("SPOTIFY", 0);
        q = Volley.newRequestQueue(context);
    }

    public  ArrayList<Song> getSongs(){
        return songs;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }


    //make call to endpoint to recieve playlist data
    public ArrayList<Song> getPlaylistItems(final AsyncHandler callback, String playlist_id){
        String url = "https://api.spotify.com/v1/playlists/37i9dQZF1DX8TvdyVZSYFY/tracks?market=ES";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            Gson gson = new Gson();
            //user = gson.fromJson(response.toString(), User.class);
            JSONArray items = response.optJSONArray("items");
            for (int i = 0; i < items.length(); i++) {
                try{
                    JSONObject obj = items.getJSONObject(i);
                    String id = obj.getString("id");
                    String name = obj.getString("name");

                    Log.d("SONG ID", id);
                    Log.d("SONG NAME", name);

                    //save songs to array list
                    Song song = new Song(name, id);
                    songs.add(song);

                } catch (JSONException e){ }
            }
            callback.finished();

        }, error -> getPlaylistItems(() -> {

        }, playlist_id) ){
            @Override
            //need this override method to provide header to request
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap();
                String token = sp.getString("token", "");
                String auth = "Bearer " + token;
                headers.put("Authorization", auth);
                headers.put("market", "DK");
                return headers;
            }
        };

        q.add(jsonObjectRequest);
        return songs;

    }
}


