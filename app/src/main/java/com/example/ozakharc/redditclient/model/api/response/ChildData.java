
package com.example.ozakharc.redditclient.model.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChildData {


    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("thumbnail_height")
    @Expose
    private Object thumbnailHeight;

    @SerializedName("thumbnail_width")
    @Expose
    private Object thumbnailWidth;

    @SerializedName("thumbnail")
    @Expose
    private String thumbnail;

    @SerializedName("num_comments")
    @Expose
    private Integer numComments;

    @SerializedName("author")
    @Expose
    private String author;

    @SerializedName("created_utc")
    @Expose
    private Integer createdUtc;

    @SerializedName("selftext")
    @Expose
    private String selftext;


    @SerializedName("preview")
    @Expose
    private Preview preview;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Object getThumbnailHeight() {
        return thumbnailHeight;
    }

    public void setThumbnailHeight(Object thumbnailHeight) {
        this.thumbnailHeight = thumbnailHeight;
    }

    public Object getThumbnailWidth() {
        return thumbnailWidth;
    }

    public void setThumbnailWidth(Object thumbnailWidth) {
        this.thumbnailWidth = thumbnailWidth;
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

    public Integer getCreatedUtc() {
        return createdUtc;
    }

    public void setCreatedUtc(Integer createdUtc) {
        this.createdUtc = createdUtc;
    }

    public String getSelftext() {
        return selftext;
    }

    public void setSelftext(String selftext) {
        this.selftext = selftext;
    }

    public Preview getPreview() {
        return preview;
    }

    public void setPreview(Preview preview) {
        this.preview = preview;
    }
}
