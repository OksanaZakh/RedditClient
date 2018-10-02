
package com.example.ozakharc.redditclient.model.api.response;

import com.google.gson.annotations.Expose;
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

}
