package com.example.ozakharc.redditclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.ozakharc.redditclient.model.NewsItem;
import com.example.ozakharc.redditclient.utils.Constants;

public class DetailedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        NewsItem item=(NewsItem) getIntent().getExtras().get(Constants.NEWS_ITEM);
    }
}
