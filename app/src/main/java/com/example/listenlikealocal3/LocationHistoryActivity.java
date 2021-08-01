package com.example.listenlikealocal3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.listenlikealocal3.Model.Location;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class LocationHistoryActivity extends AppCompatActivity {

    private RecyclerView rvLocations;
    protected LocationListAdapter adapter;
    protected List<Location> locationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_history);

        rvLocations = findViewById(R.id.rvLocations);

        locationList = new ArrayList<>();
        adapter = new LocationListAdapter(this, locationList);

        rvLocations.setAdapter(adapter);
        rvLocations.setLayoutManager(new LinearLayoutManager(this));
        queryLocations();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvLocations.setLayoutManager(linearLayoutManager);
    }

    private void queryLocations() {
        // specify what type of data we want to query - Post.class
        ParseQuery<Location> query = ParseQuery.getQuery(Location.class);
        // limit query to latest 20 items
        query.setLimit(20);
        // order posts by creation date (newest first)
        query.addDescendingOrder("createdAt");
        // start an asynchronous call for posts
        query.findInBackground(new FindCallback<Location>() {
            @Override
            public void done(List<Location> locations, ParseException e) {
                // check for errors
                if (e != null) {
                    Log.e("TAG", "Issue with getting locations", e);
                    return;
                }

                // for debugging purposes let's print every post description to logcat
                for (Location location : locations) {
                    Log.i("TAG", "Location: " + location.getLocation());
                }

                // save received posts to list and notify adapter of new data
                locationList.addAll(locations);
                adapter.notifyDataSetChanged();
            }
        });
    }
}