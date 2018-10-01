package com.example.ozakharc.redditclient.view;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.ozakharc.redditclient.ListActivityMvp;
import com.example.ozakharc.redditclient.R;
import com.example.ozakharc.redditclient.model.NewsItem;
import com.example.ozakharc.redditclient.presenter.ListPresenter;
import com.example.ozakharc.redditclient.utils.Constants;
import com.example.ozakharc.redditclient.view.adapter.ListItemsAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListActivity extends AppCompatActivity implements ListActivityMvp.View {

    private static final String TAG = "ListActivity";

    private ListActivityMvp.Presenter presenter;

    @BindView(R.id.rvList)
    RecyclerView rvList;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private ListItemsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ButterKnife.bind(this);

        this.presenter = new ListPresenter();
        presenter.attachView(this);
        presenter.viewIsReady();

        setDataToAdapter();
    }

    @Override
    public void updateList(List<NewsItem> newsItems) {
        adapter.setData(newsItems);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
        if (isFinishing()) {
            presenter=null;
        }
    }

    private void setDataToAdapter() {
        adapter = new ListItemsAdapter();
        adapter.setOnItemClickListener(this);

        rvList.setLayoutManager(new LinearLayoutManager(this));
        rvList.setAdapter(adapter);
        rvList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1)) {
                    presenter.getDataFromModel();
                }
            }
        });
    }

    @Override
    public void onItemClick(NewsItem item) {
        presenter.onItemClick(item);
    }

    @Override
    public void startNewActivity(NewsItem item) {
        Intent activityIntent = new Intent(this, DetailedActivity.class);
        activityIntent.putExtra(Constants.NEWS_ITEM, item);
        startActivity(activityIntent);
    }

    @Override
    public void showProgressBar(){
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar(){
        progressBar.setVisibility(View.GONE);
    }

}
