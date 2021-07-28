package com.example.listenlikealocal3.Model;

import android.view.LayoutInflater;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Location")
public class Location extends ParseObject {

    public static final String LOCATION = "Location";
    public static final String USER = "User";

    public Location() {
    }

    public String getLocation() {
        return getString(LOCATION);
    }

    public void setLocation(String location) {
        put(LOCATION, location);
    }

    public String getUser() {
        return getString(USER);
    }

    public void setUser(ParseUser user) {
        put(USER, user);
    }
}
