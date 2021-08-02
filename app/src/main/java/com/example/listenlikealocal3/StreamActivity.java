package com.example.listenlikealocal3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.listenlikealocal3.Model.Location;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class StreamActivity extends AppCompatActivity {

    private RecyclerView rvLocations;
    protected LocationListAdapter adapter;
    protected List<Location> locationList;
    private FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stream);

        rvLocations = findViewById(R.id.rvLocations);

        locationList = new ArrayList<>();
        adapter = new LocationListAdapter(this, locationList);

        rvLocations.setAdapter(adapter);
        rvLocations.setLayoutManager(new LinearLayoutManager(this));

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder, @NonNull @NotNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull @NotNull RecyclerView.ViewHolder viewHolder, int direction) {
                Location deletedLocation = locationList.get(viewHolder.getBindingAdapterPosition());
                int position = viewHolder.getBindingAdapterPosition();
                locationList.remove(viewHolder.getBindingAdapterPosition());
                adapter.notifyItemRemoved(viewHolder.getBindingAdapterPosition());

                Snackbar.make(rvLocations, deletedLocation.getLocation(), Snackbar.LENGTH_LONG).setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        locationList.add(position, deletedLocation);
                        adapter.notifyItemRangeInserted(position, locationList.size());
                    }
                }).show();
            }
        }).attachToRecyclerView(rvLocations);

        queryLocations();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvLocations.setLayoutManager(linearLayoutManager);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StreamActivity.this, LocationInputActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
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

    public void onLogoutButton(View view) {
        ParseUser.logOut();
        ParseUser currentUser = ParseUser.getCurrentUser();
        finish();
    }
}