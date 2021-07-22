package com.example.listenlikealocal3.Model;

public class Wiki {

    private String title;
    private String id;
    private String text;


    public Wiki(){ }

    public Wiki(String title, String id){
        this.title = title;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }


}
