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

import com.example.listenlikealocal3.Model.Location;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.parceler.Parcels;

import java.util.List;

public class LocationInputActivity extends AppCompatActivity {

    public static final String TAG = "LocationInputActivity";

    Button etButton;
    EditText etlocation;
    String country_code;
    ParseException exception;

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
            saveLocation(locationInput, currentUser, exception);
            Log.i(TAG, "onClick location input");
            country_code = etlocation.getText().toString();

            Intent intent = new Intent(getBaseContext(), PlaylistActivity.class);
            intent.putExtra("country_code", Parcels.wrap(country_code));
            startActivity(intent);
        });
    }

    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void saveLocation(String locationInput, ParseUser currentUser, ParseException ex) {
        Location location = new Location();
        location.setLocation(locationInput);
        location.setUser(currentUser);

        location.saveInBackground(e -> {
            if (e != null) {
                Log.e(TAG, "error while saving", e);
                Toast.makeText(LocationInputActivity.this, "Error while saving", Toast.LENGTH_SHORT).show();
            }
            if (ex != null) {
                final int statusCode = ex.getCode();
                if (statusCode == ParseException.OBJECT_NOT_FOUND) {
                    Log.i(TAG, "Location save was successful!");
                    etlocation.setText("");
                }
            }
        });
    }
}
