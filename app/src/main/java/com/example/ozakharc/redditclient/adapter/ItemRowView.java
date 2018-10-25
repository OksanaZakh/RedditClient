package com.example.ozakharc.redditclient.adapter;

public interface ItemRowView extends RowView{

    void setTitle(String title);
    void setAuthor(String author);
    void setThumbnail(String thumbnail);
    void setComments(String comments);
    void setDate(String date);
    void setClickListener(int pos, OnItemClickListener clickListener);

}
