package com.example.listenlikealocal3.Connectors;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.listenlikealocal3.Model.Song;
import com.example.listenlikealocal3.Model.User;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SongService {
    private ArrayList<Song> songs = new ArrayList<>();
    private SharedPreferences msharedPreferences;
    private RequestQueue q;
    private User user;

    public SongService(Context context) {
        msharedPreferences = context.getSharedPreferences("SPOTIFY", 0);
        q = Volley.newRequestQueue(context);
    }
    public ArrayList<Song> getSongs() {
        return songs;
    }

    public ArrayList<Song> getPlaylistItems(final AsyncHandler callBack, String playlist_id) {
        String endpoint = "https://api.spotify.com/v1/playlists/37i9dQZF1DX8TvdyVZSYFY/tracks?market=ES";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, endpoint, null, response -> {
                    Gson gson = new Gson();

                    JSONArray jsonArray = response.optJSONArray("items");
                    for (int n = 0; n < jsonArray.length(); n++) {
                        try {
                            JSONObject object = jsonArray.getJSONObject(n);
                            object = object.optJSONObject("track");
                            Song song = gson.fromJson(object.toString(), Song.class);
                            songs.add(song);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    callBack.finished();
                }, error -> {
                    return;

                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                String token = msharedPreferences.getString("token", "");
                String auth = "Bearer " + token;
                headers.put("Authorization", auth);
                return headers;
            }
        };
        q.add(jsonObjectRequest);
        return songs;
    }

}

