package com.example.listenlikealocal3.Model;

import com.example.listenlikealocal3.Services.SpotifyClient;
import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Location")
public class Location extends ParseObject {

    public static final String LOCATION = "Location";
    public static final String USER = "User";
    public static final String FLAG = "Flag";

    public Location() {}

    public Location(String location, String user) {
        String flag = "";
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
