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
        ParseQuery<Location> query = ParseQuery.getQuery(Location.class);
        query.setLimit(20);
        query.addDescendingOrder("createdAt");
        query.findInBackground(new FindCallback<Location>() {
            @Override
            public void done(List<Location> locations, ParseException e) {
                if (e != null) {
                    Log.e("TAG", "Issue with getting locations", e);
                    return;
                }
                for (Location location : locations) {
                    Log.i("TAG", "Location: " + location.getLocation());
                }
                locationList.addAll(locations);
                adapter.notifyDataSetChanged();
            }
        });
    }
}