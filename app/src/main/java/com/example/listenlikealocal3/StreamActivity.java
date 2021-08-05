package com.example.listenlikealocal3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.listenlikealocal3.Model.Location;
import com.example.listenlikealocal3.Services.SpotifyClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class StreamActivity extends AppCompatActivity {

    public static final String TAG = "StreamActivity";
    private RecyclerView rvLocations;
    protected LocationListAdapter adapter;
    protected List<Location> locationList;
    protected List<SpotifyClient> flagList;

    private SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stream);

        rvLocations = findViewById(R.id.rvLocations);

        locationList = new ArrayList<>();
        adapter = new LocationListAdapter(this, locationList);

        queryLocations();

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(() -> {
            locationList.clear();
            queryLocations();
        });
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(StreamActivity.this, LocationInputActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        setUpRecyclerView();

    }

    private void setUpRecyclerView() {
        rvLocations.setAdapter(adapter);
        rvLocations.setLayoutManager(new LinearLayoutManager(this));
        ItemTouchHelper itemTouchHelper = new
                ItemTouchHelper(new SwipeToDeleteCallback(adapter));
        itemTouchHelper.attachToRecyclerView(rvLocations);
    }

    private void queryLocations() {
        ParseQuery<Location> query = ParseQuery.getQuery(Location.class);
        query.setLimit(20);
        query.addDescendingOrder("createdAt");
        query.findInBackground((locations, e) -> {
            if (e != null) {
                Log.e(TAG, "Issue with getting locations", e);
                return;
            }
            for (Location location : locations) {
                Log.i(TAG, "Location: " + location.getLocation());
            }
            locationList.addAll(locations);
            adapter.notifyDataSetChanged();
            swipeContainer.setRefreshing(false);
        });
    }

    public void onLogoutButton(View view) {
        ParseUser.logOut();
        ParseUser currentUser = ParseUser.getCurrentUser();
        finish();
    }
}