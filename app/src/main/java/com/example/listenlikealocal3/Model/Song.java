package com.example.listenlikealocal3.Model;

import com.example.listenlikealocal3.Services.Songs;

//will eventually use this class to fetch song from each individual artist
public class Song {
    private String name;
    private String id;


    public Song(){ }

    public Song(String name, String id){
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String song) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


}
