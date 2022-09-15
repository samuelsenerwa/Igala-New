package com.example.igalanews.Models;

public class ImageModel {

    ImageModel(){

    }

    String url;

    public ImageModel(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
