package com.example.listenlikealocal3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class LocationInputActivity extends AppCompatActivity {

    Button etButton;
    EditText location;
    String country_code;
    Button logoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_input);

        etButton = findViewById(R.id.enterBtn);
        location = findViewById(R.id.locationInput);

        etButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("TAG", "onClick location input");
                country_code = location.getText().toString();

                Intent intent = new Intent(getBaseContext(), PlaylistActivity.class);
                intent.putExtra("country_code", country_code);
                startActivity(intent);
            }
        });

        logoutBtn = findViewById(R.id.logoutBtn);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLogoutButton(v);
            }
        });

        queryLocation();



    }

    private void queryLocation() {
        ParseQuery<Location> query = ParseQuery.getQuery(Location.class);
        query.findInBackground(new FindCallback<Location>() {
            @Override
            public void done(List<Location> locations, ParseException e) {
                if (e!=null) {
                    Log.e("TAG", "issue getting locations", e);
                    return;
                }
                for (Location location: locations) {
                    Log.i("TAG", "Location: "+ location.getLocation());
                }
            }
        });
    }

    public void onLogoutButton(View view) {
        ParseUser.logOut();
        ParseUser currentUser = ParseUser.getCurrentUser();
        finish();
    }
}
