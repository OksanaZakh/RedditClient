package com.example.ozakharc.redditclient.api;

import java.io.Serializable;
import java.util.Objects;

public class NewsItem implements Serializable {

    private String after;

    private String title;

    private String thumbnail;

    private Integer numComments;

    private String author;

    private Long createdUtc;

    private String selftext;

    private String photoUrl;

    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAfter() {
        return after;
    }

    public void setAfter(String after) {
        this.after = after;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Integer getNumComments() {
        return numComments;
    }

    public void setNumComments(Integer numComments) {
        this.numComments = numComments;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public long getCreatedUtc() {
        return createdUtc;
    }

    public void setCreatedUtc(long createdUtc) {
        this.createdUtc = createdUtc;
    }

    public String getSelftext() {
        return selftext;
    }

    public void setSelftext(String selftext) {
        this.selftext = selftext;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewsItem item = (NewsItem) o;
        return Objects.equals(after, item.after) &&
                Objects.equals(title, item.title) &&
                Objects.equals(thumbnail, item.thumbnail) &&
                Objects.equals(numComments, item.numComments) &&
                Objects.equals(author, item.author) &&
                Objects.equals(createdUtc, item.createdUtc) &&
                Objects.equals(selftext, item.selftext) &&
                Objects.equals(photoUrl, item.photoUrl) &&
                Objects.equals(url, item.url);
    }
}
