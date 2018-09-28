package com.example.ozakharc.redditclient.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

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

        newsItems = new ArrayList<>();

        adapter=new ListItemsAdapter();
        adapter.setData(newsItems);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this);
        rvList.setLayoutManager(layoutManager);
        rvList.setAdapter(adapter);

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
    public void itemClicked() {

    }

    @Override
    public void showDetailedFragment() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
        if (isFinishing()) {
            presenter.destroy();
        }
    }
}
