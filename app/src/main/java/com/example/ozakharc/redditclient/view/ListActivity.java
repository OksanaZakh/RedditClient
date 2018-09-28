package com.example.ozakharc.redditclient.view;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.ozakharc.redditclient.DetailedActivity;
import com.example.ozakharc.redditclient.ListActivityMvp;
import com.example.ozakharc.redditclient.R;
import com.example.ozakharc.redditclient.model.NewsItem;
import com.example.ozakharc.redditclient.presenter.ListPresenter;
import com.example.ozakharc.redditclient.view.adapter.ListItemsAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListActivity extends AppCompatActivity implements ListActivityMvp.View{

    private static final String TAG = "ListActivity";

    private ListPresenter presenter;

    @BindView(R.id.rvList)
    RecyclerView rvList;

    private ListItemsAdapter adapter;
    private List<NewsItem> newsItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        this.presenter=new ListPresenter();
        presenter.attachView(this);
        presenter.viewIsReady();

        setDataToAdapter();
    }


    @Override
    public void showNoInternetConnection() {
        Toast.makeText(this, "Bad internet connection, can't download a data from Reddit!", Toast.LENGTH_LONG ).show();
    }

    @Override
    public void updateList(NewsItem newsItem) {
        newsItems.add(newsItem);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showError() {
        Toast.makeText(this, "Error occur, can't download a data from Reddit!", Toast.LENGTH_LONG ).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
        if (isFinishing()) {
            presenter.destroy();
        }
    }

    private void setDataToAdapter(){
        newsItems = new ArrayList<>();
        adapter=new ListItemsAdapter();
        adapter.setData(newsItems);
        adapter.setOnItemClickListener(this);

        rvList.setLayoutManager(new LinearLayoutManager(this));
        rvList.setAdapter(adapter);
        rvList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1)) {
                    if (newsItems.size()>0) {
                        presenter.getDataFromModel(newsItems.get(newsItems.size() - 1).getAfter());
                        Log.d(TAG, "onScrollStateChanged: " + newsItems.get(newsItems.size() - 1).getAfter() + newsItems.size());
                    }
                }
            }
        });
    }

    @Override
    public void onItemClick(NewsItem item) {
        Intent activityIntent=new Intent(this, DetailedActivity.class);
        activityIntent.putExtra("newsItem", item);
        startActivity(activityIntent);
    }
}
