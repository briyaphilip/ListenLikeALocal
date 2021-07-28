package com.example.listenlikealocal3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import java.util.List;

public class LocationInputActivity extends AppCompatActivity {

    Button etButton;
    EditText etlocation;
    String country_code;
    Button logoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_input);

        etButton = findViewById(R.id.enterBtn);
        etlocation = findViewById(R.id.locationInput);

        etButton.setOnClickListener(v -> {
            String locationInput = etlocation.getText().toString();
            if (locationInput.isEmpty()){
                Toast.makeText(LocationInputActivity.this, "Location cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            }
            ParseUser currentUser = ParseUser.getCurrentUser();
            saveLocation(locationInput, currentUser);
            Log.i("TAG", "onClick location input");
            country_code = etlocation.getText().toString();

            Intent intent = new Intent(getBaseContext(), PlaylistActivity.class);
            intent.putExtra("country_code", country_code);
            startActivity(intent);
        });

        logoutBtn = findViewById(R.id.logoutBtn);
        logoutBtn.setOnClickListener(v -> onLogoutButton(v));

    }

    private void saveLocation(String locationInput, ParseUser currentUser) {
        Location location = new Location();
        location.setLocation(locationInput);
        location.setUser(currentUser);
        location.saveInBackground(e -> {
            if (e != null) {
                Log.e("TAG", "error while saving", e);
                Toast.makeText(LocationInputActivity.this, "Error while saving", Toast.LENGTH_SHORT).show();
            }
            Log.i("TAG", "Location save was successful!");
            etlocation.setText("");
        });
    }

    private void queryLocation() {
        ParseQuery<Location> query = ParseQuery.getQuery(Location.class);
        query.findInBackground((locations, e) -> {
            if (e != null) {
                Log.e("TAG", "issue getting locations", e);
                return;
            }
            for (Location location: locations) {
                Log.i("TAG", "Location: "+ location.getLocation());
            }
        });
    }

    public void onLogoutButton(View view) {
        ParseUser.logOut();
        ParseUser currentUser = ParseUser.getCurrentUser();
        finish();
    }
}
