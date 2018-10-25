package com.example.ozakharc.redditclient;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.ozakharc.redditclient.adapter.ListItemsContract;
import com.example.ozakharc.redditclient.adapter.ListItemsPresenterImpl;
import com.example.ozakharc.redditclient.detailed.DetailedActivity;
import com.example.ozakharc.redditclient.api.NewsItem;
import com.example.ozakharc.redditclient.utils.Constants;
import com.example.ozakharc.redditclient.adapter.ListItemsAdapterImpl;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainActivityContract.View {

    private MainActivityContract.Presenter presenter;

    private ProgressListener listener;
    private boolean isInProgress;

    private InternetConnection connection;

    @BindView(R.id.rvList)
    RecyclerView rvList;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private ListItemsAdapterImpl adapter;
    ListItemsContract.ListItemsPresenter listItemsPresenterImpl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ButterKnife.bind(this);
        connection=new InternetConnectionImpl(this);
        this.presenter = new MainPresenter(new NetworkManagerImpl(connection));
        presenter.attachView(this);
        presenter.loadData();
        setAdapter();
    }

//    @Override
//    public void showData(List<NewsItem> newsItems) {
//        listItemsPresenterImpl.setData(newsItems);
//    }

    @Override
    public void showAlert(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
        if (isFinishing()) {
            presenter = null;
        }
    }

    private void setAdapter() {
        listItemsPresenterImpl =new ListItemsPresenterImpl();
        presenter.setAdapterPresenter(listItemsPresenterImpl);
       // listItemsPresenterImpl.setClickListener(this);
        adapter = new ListItemsAdapterImpl(listItemsPresenterImpl);

        rvList.setLayoutManager(new LinearLayoutManager(this));
        rvList.setAdapter(adapter);
        rvList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1)) {
                    presenter.loadData();
                }
            }
        });
    }

//    @Override
//    public void onItemClick(int item) {
//        presenter.onItemClick(item);
//    }

    @Override
    public void startNewActivity(NewsItem item) {
        Intent activityIntent = new Intent(this, DetailedActivity.class);
        activityIntent.putExtra(Constants.NEWS_ITEM, item);
        startActivity(activityIntent);
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
        isInProgress=true;
        if(listener!=null){
            listener.onProgressShown();
        }
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
        isInProgress=false;
        if(listener!=null){
            listener.onProgressDismissed();
        }
    }

    @VisibleForTesting
    public boolean isInProgress() {
        return isInProgress;
    }

    @VisibleForTesting
    public List<NewsItem> getAllNewsItems(){
        return this.presenter.getNewsItems();
    }

    @VisibleForTesting
    public void setProgressListener(ProgressListener progressListener) {
        listener = progressListener;
    }

    @VisibleForTesting
    public InternetConnection getConnection() {
        return connection;
    }
}
