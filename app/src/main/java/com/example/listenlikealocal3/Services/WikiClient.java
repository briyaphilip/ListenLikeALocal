package com.example.listenlikealocal3.Services;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.listenlikealocal3.Model.Artist;
import com.example.listenlikealocal3.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import okhttp3.Headers;

public class WikiClient extends AppCompatActivity {

    public static final String TAG = "WikiClient";

    TextView artistName;
    TextView artistDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_details);

        artistName = findViewById(R.id.artistNm);
        artistDetails = findViewById(R.id.artistDets);

        String artistObject = (String) Parcels.unwrap(getIntent().getParcelableExtra("artist"));
        Log.i(TAG, "parcel artist name:" + artistObject);

        AsyncHttpClient client = new AsyncHttpClient();
        Log.i(TAG, "log is working");

        client.get("https://en.wikipedia.org/w/api.php?action=query&format=json&prop=extracts&formatversion=2&exsentences=10&exlimit=1&explaintext=1&titles="+artistObject, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;

                try {
                    JSONObject query = jsonObject.getJSONObject("query");
                    Log.i(TAG, "QUERY: " + query.toString());

                    JSONArray pages = query.getJSONArray("pages");
                    Log.i(TAG, "PAGES: " + pages.toString());

                    for (int i = 0; i < pages.length(); i++) {
                        JSONObject items = pages.getJSONObject(i);
                        String title = items.getString("title");
                        artistName.setText(title);
                        Log.i(TAG, "TITLE: " + title);
                        String text = items.getString("extract");
                        Log.i(TAG, "TEXT: "+ text);
                        artistDetails.setText(text);
                    }

                } catch (JSONException e) {
                    Log.e(TAG, "Hit json exception", e);
                    artistDetails.setText("No info found");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable) {
                Log.d(TAG, "onFailure");
            }
        });
    }

    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

}