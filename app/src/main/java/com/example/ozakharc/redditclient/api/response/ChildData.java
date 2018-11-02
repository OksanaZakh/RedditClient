
package com.example.ozakharc.redditclient.api.response;

import com.google.gson.annotations.SerializedName;

public class ChildData {

    private String title;

    private String thumbnail;

    @SerializedName("num_comments")
    private Integer numComments;

    private String author;

    @SerializedName("created_utc")
    private long createdUtc;

    private String selftext;

    private String url;

    private String permalink;

    public String getUrl() {
        return url;
    }

    private Preview preview;

    public String getTitle() {
        return title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public Integer getNumComments() {
        return numComments;
    }

    public String getAuthor() {
        return author;
    }

    public long getCreatedUtc() {
        return createdUtc;
    }

    public String getSelftext() {
        return selftext;
    }

    public Preview getPreview() {
        return preview;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void setNumComments(Integer numComments) {
        this.numComments = numComments;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setCreatedUtc(long createdUtc) {
        this.createdUtc = createdUtc;
    }

    public void setSelftext(String selftext) {
        this.selftext = selftext;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setPreview(Preview preview) {
        this.preview = preview;
    }

    public String getPermalink() {
        return permalink;
    }

    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }
}
