package com.example.ozakharc.redditclient.model;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Objects;

@Entity
public class ItemComment {

    @NonNull
    @PrimaryKey
    private String comment="";
    private String permalink;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getPermalink() {
        return permalink;
    }

    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemComment comment1 = (ItemComment) o;
        return Objects.equals(comment, comment1.comment) &&
                Objects.equals(permalink, comment1.permalink);
    }

    @Override
    public int hashCode() {

        return Objects.hash(comment, permalink);
    }

    @Override
    public String toString() {
        return "ItemComment{" +
                "comment='" + comment + '\'' +
                ", permalink='" + permalink + '\'' +
                '}';
    }
}
