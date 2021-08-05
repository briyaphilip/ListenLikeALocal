package com.example.listenlikealocal3.Model;

public class Song {
    private String name;
    private String id;
    private String uri;

    public Song(){ }

    public Song(String name, String uri){
        this.name = name;
        this.uri = uri;
    }
//
//    public Song(String name, String id){
//        this.name = name;
//        this.id = id;
//    }

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

    public String getUri() {return uri;}

    public void setUri(String songURI) {this.uri = uri;
    }
}
