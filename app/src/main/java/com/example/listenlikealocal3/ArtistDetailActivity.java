package com.example.listenlikealocal3;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.listenlikealocal3.Model.Artist;
import com.example.listenlikealocal3.Model.Playlist;
import com.example.listenlikealocal3.Model.Wiki;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.Iterator;

import okhttp3.Headers;

public class ArtistDetailActivity extends AppCompatActivity {

    TextView artistName;
    TextView artistDetails;
    String BASE_URL = "https://en.wikipedia.org/w/api.php?action=query&prop=extracts&format=json&exintro=&titles=";
    //"https://en.wikipedia.org/w/api.php?action=query&format=json&prop=extracts&titles=Pet_door&formatversion=2&exsentences=10&exlimit=1&explaintext=1"

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_details);

        artistName = findViewById(R.id.artistNm);
        artistDetails = findViewById(R.id.artistDets);

        String artistObject = (String) Parcels.unwrap(getIntent().getParcelableExtra("artist"));
        Log.i("TAG", "parcel name:" + artistObject);

        AsyncHttpClient client = new AsyncHttpClient();
        Log.i("TAG", "log is working");

        String artistNm = "Michael Jackson";

        client.get("https://en.wikipedia.org/w/api.php?action=query&format=json&prop=extracts&formatversion=2&exsentences=10&exlimit=1&explaintext=1&titles="+artistObject, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d("TAG", "onSuccess");
                JSONObject jsonObject = json.jsonObject;

                try {
                    JSONObject query = jsonObject.getJSONObject("query");
                    Log.i("TAG", "QUERY: " + query.toString());

                    JSONArray pages = query.getJSONArray("pages");
                    Log.i("TAG", "PAGES: " + pages.toString());

                    for (int i = 0; i < pages.length(); i++) {
                        JSONObject items = pages.getJSONObject(i);
                        String title = items.getString("title");
                        artistName.setText(title);
                        Log.i("TAG", "TITLE: " + title);
                        String text = items.getString("extract");
                        Log.i("TAG", "TEXT: "+ text);
                        artistDetails.setText(text);
                    }

                } catch (JSONException e) {
                    Log.e("TAG", "Hit json exception", e);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable) {
                Log.d("TAG", "onFailure");
            }

        });



}
}
