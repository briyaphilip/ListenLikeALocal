package com.example.listenlikealocal3.Services;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
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
    ImageView artistImage;
    String URL = "https://en.wikipedia.org/w/api.php?action=query&format=json&";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_details);

        artistName = findViewById(R.id.artistNm);
        artistDetails = findViewById(R.id.artistDets);
        artistImage = findViewById(R.id.artistImg);

        artistDetails.setMovementMethod(new ScrollingMovementMethod());

        String artistObject = (String) Parcels.unwrap(getIntent().getParcelableExtra("artist"));
        Log.i(TAG, "parcel artist name:" + artistObject);

        AsyncHttpClient client = new AsyncHttpClient();
        Log.i(TAG, "log is working");

        client.get(URL + "formatversion=2&prop=pageimages|pageterms&piprop=original&titles=" + artistObject, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;

                try {
                    JSONObject query = jsonObject.getJSONObject("query");
                    Log.i(TAG, "QUERY1: " + query.toString());

                    JSONArray pages = query.getJSONArray("pages");
                    Log.i(TAG, "PAGES1: " + pages.toString());

                    for (int i = 0; i < pages.length(); i++) {
                        JSONObject items = pages.getJSONObject(i);
                        JSONObject original = items.getJSONObject("original");
                        Log.i(TAG, "ORIGINAL: " + original);
                        String imgUrl = original.getString("source");
                        Log.i(TAG, "IMG URL: " + imgUrl);
                        Glide.with(WikiClient.this)
                                .load(imgUrl)
                                .override(400, 600)
                                .apply(RequestOptions.bitmapTransform(new RoundedCorners(14)))
                                .into(artistImage);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {

            }
        });

        client.get(URL + "prop=extracts&formatversion=2&exsentences=10&exlimit=1&explaintext=1&titles=" +artistObject, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;

                try {
                    JSONObject query = jsonObject.getJSONObject("query");
                    Log.i(TAG, "QUERY2: " + query.toString());

                    JSONArray pages = query.getJSONArray("pages");
                    Log.i(TAG, "PAGES2: " + pages.toString());

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
