package com.example.listenlikealocal3.Services;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.listenlikealocal3.Connectors.AsyncHandler;
import com.example.listenlikealocal3.Model.Artist;
import com.example.listenlikealocal3.Model.Playlist;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Playlists {
    private ArrayList<Playlist> playlists = new ArrayList<Playlist>();
    private SharedPreferences sp;
    private RequestQueue q;

    public Playlists(Context context){
        sp = context.getSharedPreferences("SPOTIFY", 0);
        q = Volley.newRequestQueue(context);
    }

    public  ArrayList<Playlist> getPlaylists(){
        return playlists;
    }

    //make call to endpoint to recieve featured playlists
    public ArrayList<Playlist> getFeaturedPlaylists(final AsyncHandler callback){
        String url = "https://api.spotify.com/v1/browse/featured-playlists";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            Gson gson = new Gson();
            JSONObject playlistObj = response.optJSONObject("playlists");
            JSONArray items = null;
            try {
                items = playlistObj.getJSONArray("items");
                Log.d("PLAYLISTS", items.toString());
                for (int i = 0; i < items.length(); i++) {
                    try{
                        JSONObject obj = items.getJSONObject(i);
                        String id = obj.getString("id");
                        String name = obj.getString("name");
                        Log.d("PLAYLIST ID", id);
                        Log.d("PLAYLIST NAME", name);

                        //save artist to array list
                        Playlist playlist = new Playlist(name, id);
                        playlists.add(playlist);

                    } catch (JSONException e){ }
                }
                callback.finished();


            } catch (JSONException e) {
                e.printStackTrace();
            }


        }, error -> getFeaturedPlaylists(() -> {

        })) {
            @Override
            //need this override method to provide header to request
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap();
                String token = sp.getString("token", "");
                String auth = "Bearer " + token;
                headers.put("Authorization", auth);
                return headers;
            }
        };

        q.add(jsonObjectRequest);
        return playlists;

    }
}
