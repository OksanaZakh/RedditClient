package com.example.ozakharc.redditclient.adapter;

import com.example.ozakharc.redditclient.api.NewsItem;

import java.util.List;

public class AdapterPresenter {

    int numItemsInPage=11;
    private List<NewsItem> items;
    private OnItemClickListener onItemClickListener;


    public AdapterPresenter(ListItemsAdapter adapter) {
    }
}
