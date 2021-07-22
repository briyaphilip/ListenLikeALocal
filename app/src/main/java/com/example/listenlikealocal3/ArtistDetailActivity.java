package com.example.listenlikealocal3;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.listenlikealocal3.Model.Wiki;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import okhttp3.Headers;

public class ArtistDetailActivity extends AppCompatActivity {

    String BASE_URL = "https://en.wikipedia.org/w/api.php?action=query&prop=extracts&format=json&exintro=&titles=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_details);

        AsyncHttpClient client = new AsyncHttpClient();
        Log.i("TAG", "log is working");
        String artist_name = "Michael Jackson";
        client.get(BASE_URL+artist_name, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d("TAG", "onSuccess");
                JSONObject jsonObject = json.jsonObject;

                try {
                    JSONObject query = jsonObject.getJSONObject("query");
                    Log.i("TAG", "QUERY: " + query.toString());

                    JSONObject pages = query.getJSONObject("pages");
                    Log.i("TAG", "PAGES: " + pages.toString());

                    for (int i = 0; i<pages.names().length(); i++) {
                        String page_id = pages.names().getString(i);
                        Log.v("TAG", page_id);

                    JSONObject items = pages.getJSONObject(page_id);
                    Log.i("TAG", "ITEMS: " + items.toString());

                    String title = items.getString("title");
                    Log.i("TAG", "TITLE: " + title);

                    String text = items.getString("extract");
                    Log.i("TAG", "TEXT: " + text);

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
