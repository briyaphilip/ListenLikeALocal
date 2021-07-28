package com.example.listenlikealocal3;

import android.view.LayoutInflater;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Location")
public class Location extends ParseObject {

    public static final String LOCATION = "Location";

    public Location() {
    }

    public String getLocation() {
        return getString(LOCATION);
    }

    public void setLocation(String location) {
        put(LOCATION, location);
    }
}
