package com.example.ozakharc.redditclient.api.response;


import java.util.List;

public class Preview {

    private List<Image> images = null;

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }
}