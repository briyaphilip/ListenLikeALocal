package com.example.listenlikealocal3.Model;

import org.parceler.Parcel;

@Parcel
public class Artist{
    private String name ;
    private String id;
    private String img;

    public Artist() {
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

    public String getImg() {return img;}

    public void setImg(String img) {this.img = img;}
}
