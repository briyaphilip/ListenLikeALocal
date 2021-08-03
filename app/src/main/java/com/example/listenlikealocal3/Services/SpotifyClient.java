package com.example.listenlikealocal3.Services;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
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

public class SpotifyClient extends AppCompatActivity {

    public static final String TAG = "SpotifyClient";
    private final ArrayList<Song> songs = new ArrayList<>();
    private final ArrayList<Artist> artists = new ArrayList<>();
    private final SharedPreferences sp;
    private final RequestQueue q;
    private final ArrayList<Playlist> playlists = new ArrayList<Playlist>();
    private User user;

    public SpotifyClient(Context context) {
        sp = context.getSharedPreferences("SPOTIFY", 0);
        q = Volley.newRequestQueue(context);
    }

    public ArrayList<Song> getSongs() {
        return songs;
    }

    public ArrayList<Artist> getArtist() {
        return artists;
    }

    public  ArrayList<Playlist> getPlaylists(){
        return playlists;
    }

    public User getUser() {
        return user;
    }

    public void getPlaylistItems(final AsyncHandler callBack, String playlist_id) {
        String endpoint = "https://api.spotify.com/v1/playlists/" + playlist_id +"/tracks?market=US";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, endpoint, null, response -> {
                    //use either gson or json
                    Gson gson = new Gson();
                    JSONArray jsonArray = response.optJSONArray("items");
                    Log.i(TAG, "ITEMS: " + jsonArray);
                    for (int n = 0; n < jsonArray.length(); n++) {
                        try {
                            Log.i(TAG, "PLAYLIST ID: " + playlist_id);
                            JSONObject object = jsonArray.getJSONObject(n);
                            object = object.optJSONObject("track");
                            Log.i(TAG, "TRACK: " + object.toString());

                            JSONArray album = object.getJSONArray("artists");
                            Log.i(TAG, "LIST: " + album.toString());

                            for(int i = 0; i < album.length(); i++) {
                                JSONObject info = album.getJSONObject(i);
                                Artist artistNm = new Artist();
                                artistNm.setName(info.getString("name"));
                                artists.add(artistNm);
                                Log.i(TAG,"NAME: " + artistNm.toString());
                            }

                            Song song = gson.fromJson(object.toString(), Song.class);
                            Log.i(TAG, "SONG: " + song);
                            songs.add(song);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    callBack.finished();
                }, error -> {
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                String token = sp.getString("token", "");
                String auth = "Bearer " + token;
                headers.put("Authorization", auth);
                return headers;
            }
        };
        q.add(jsonObjectRequest);
    }

    public void getFeaturedPlaylists(final AsyncHandler callback, String country_code, String limit){
        String url = "https://api.spotify.com/v1/browse/featured-playlists?country=" + country_code + "&limit=" + limit;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            JSONObject playlistObj = response.optJSONObject("playlists");
            JSONArray items = null;
            try {
                items = playlistObj.getJSONArray("items");
                Log.d("PLAYLISTS", items.toString());
                for (int i = 0; i < items.length(); i++) {
                    try{
                        JSONObject obj = items.getJSONObject(i);

                        String id = obj.getString("id");
                        Log.d(TAG,"PLAYLIST ID: " + id);

                        String name = obj.getString("name");
                        Log.d(TAG,"PLAYLIST NAME: " + name);

                        Playlist playlist = new Playlist(name, id);
                        playlists.add(playlist);

                    } catch (JSONException e){
                        Log.e(TAG, "ERROR: " + e.toString());
                    }
                }
                callback.finished();

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }, error -> getFeaturedPlaylists(() -> {

        }, country_code, limit)) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                String token = sp.getString("token", "");
                String auth = "Bearer " + token;
                headers.put("Authorization", auth);
                headers.put("country", country_code);
                headers.put("limit", limit);
                return headers;
            }
        };
        q.add(jsonObjectRequest);

    }

    public void get(final AsyncHandler callBack) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest("https://api.spotify.com/v1/me", null, response -> {
            Gson gson = new Gson();
            user = gson.fromJson(response.toString(), User.class);
            callBack.finished();
        }, error -> get(() -> {

        })) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                String token = sp.getString("token", "");
                String auth = "Bearer " + token;
                headers.put("Authorization", auth);
                return headers;
            }
        };
        q.add(jsonObjectRequest);
    }

}
