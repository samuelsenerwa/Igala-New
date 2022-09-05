package com.example.igalanews.Models;

public class VideoModel {
    String size, name, uri;

    public VideoModel(String size, String name, String uri) {
        this.size = size;
        this.name = name;
        this.uri = uri;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
