package com.example.listenlikealocal3.Model;

//will eventually use this class to fetch song from each individual artist
public class Song {
    private String song;
    private String id;

    public Song(){ }

    public Song(String song, String id){
        this.song = song;
        this.id = id;
    }

    public String getSong() {
        return song;
    }

    public void setSong(String song) {
        this.song = song;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
