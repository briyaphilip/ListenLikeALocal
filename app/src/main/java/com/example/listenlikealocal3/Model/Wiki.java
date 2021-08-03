package com.example.listenlikealocal3.Model;

public class Wiki {

    private final String title;
    private final String id;
    private String text;

    public Wiki(String title, String id){
        this.title = title;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }


}
