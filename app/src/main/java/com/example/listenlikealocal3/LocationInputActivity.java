package com.example.listenlikealocal3;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.listenlikealocal3.Model.Location;
import com.example.listenlikealocal3.Services.SpotifyClient;
import com.parse.CountCallback;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.List;

import okhttp3.Headers;

public class LocationInputActivity extends AppCompatActivity {

    public static final String TAG = "LocationInputActivity";
    public static final String COUNTRY_CODE = "country_code";
    public static final String LOCATION = "Location";

    Button etButton;
    EditText etlocation;
    String country_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_input);

        etButton = findViewById(R.id.enterBtn);
        etlocation = findViewById(R.id.locationInput);

        etButton.setOnClickListener(v -> {
            String locationInput = etlocation.getText().toString();
            if (locationInput.isEmpty()) {
                Toast.makeText(LocationInputActivity.this, "Location cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            }
            ParseUser currentUser = ParseUser.getCurrentUser();
            Log.i(TAG, "about to call save location");
            saveLocation(locationInput, currentUser);
            Log.i(TAG, "onClick location input");
            country_code = etlocation.getText().toString();

            Intent intent = new Intent(getBaseContext(), PlaylistActivity.class);
            intent.putExtra(COUNTRY_CODE, Parcels.wrap(country_code));
            startActivity(intent);
        });
    }

    private void saveLocation(String locationInput, ParseUser currentUser) {
        Location location = new Location(locationInput, currentUser.toString());
        location.setLocation(locationInput);
        location.setUser(currentUser);

        ParseQuery<ParseObject> locationQuery = ParseQuery.getQuery(LOCATION);
        locationQuery.whereEqualTo(LOCATION, locationInput);
        locationQuery.countInBackground((count, e) -> {
            if (e == null) {
                if (count == 0) {
                    addNewLocation(location, locationInput);
                }
                else {
                    Log.i(TAG, "Location already exists");
                }
            } else {
                Toast.makeText(LocationInputActivity.this, "Error while saving", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addNewLocation (Location location, String locationInput) {
        location.saveInBackground(e -> {
            if (e != null) {
                Log.e(TAG, "error while saving", e);
                Toast.makeText(LocationInputActivity.this, "Error while saving", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
