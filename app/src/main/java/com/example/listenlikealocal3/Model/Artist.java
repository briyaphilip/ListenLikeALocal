package com.example.listenlikealocal3.Model;

//added this extra class to allow user to click on artist and fetch information
public class Artist {
    private String name ;
    private String id;
    private int followers;
    private int popularity;

    public Artist() {
    }

    public Artist(String name) {
        this.name = name;
    }

    public Artist(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public Artist(String name, String id, int followers) {
        this.name = name;
        this.id = id;
        this.followers = followers;
    }

    public Artist(String name, String id, int followers, int popularity) {
        this.name = name;
        this.id = id;
        this.followers = followers;
        this.popularity = popularity;
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
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }
}
