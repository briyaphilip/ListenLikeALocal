package com.example.listenlikealocal3.Services;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.listenlikealocal3.Connectors.AsyncHandler;
import com.example.listenlikealocal3.Model.Artist;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Songs {
    private ArrayList<Artist> artists = new ArrayList<Artist>();
    private SharedPreferences sp;
    private RequestQueue q;

    public Songs(Context context){
        sp = context.getSharedPreferences("SPOTIFY", 0);
        q = Volley.newRequestQueue(context);
    }

    public  ArrayList<Artist> getArtists(){
        return artists;
    }

    //make call to endpoint to recieve artists data
    public ArrayList<Artist> getTopArtists(final AsyncHandler callback){
        String url = "https://api.spotify.com/v1/me/top/artists";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            Gson gson = new Gson();
            JSONArray items = response.optJSONArray("items");
            for (int i = 0; i < items.length(); i++) {
                try{
                    JSONObject obj = items.getJSONObject(i);
                    int followers = obj.getJSONObject("followers").getInt("total");
                    int popularity = obj.getInt("popularity");
                    String id = obj.getString("id");
                    String name = obj.getString("name");

                    //save artist to array list
                    Artist artist = new Artist(name, id, followers, popularity);
                    artists.add(artist);

                } catch (JSONException e){ }
            }
            callback.finished();

        }, error -> getTopArtists(() -> {

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
        return artists;

    }
}

