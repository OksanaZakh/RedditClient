package com.example.ozakharc.redditclient.view.adapter;

import android.view.View;

import com.example.ozakharc.redditclient.model.NewsItem;


public interface OnItemClickListener {
    void onItemClick(View view, int position, NewsItem item);
}
