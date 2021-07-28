package com.example.listenlikealocal3.Model;

//will eventually use this class to fetch song from each individual artist
public class Song {
    private String name;
    private String id;
    private String artist;


    public Song(){ }

    public Song(String name, String id, String artist){
        this.name = name;
        this.id = id;
        this.artist = artist;
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

    public String getArtist() {
        return this.artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }


}
