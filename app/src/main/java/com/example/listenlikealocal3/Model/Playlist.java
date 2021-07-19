package com.example.listenlikealocal3.Model;

import android.content.Context;

public class Playlist {
    private String country;
    private String name;
    private String id;


    public Playlist(){ }


    public Playlist(String name, String id){
        this.name = name;
        this.id = id;
    }

    public Playlist(String name, String id, String country){
        this.name = name;
        this.id = id;
        this.country = country;
    }

    public Playlist(Context applicationContext) {
    }

    public String getCountry() {
        country = "ES";
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }}