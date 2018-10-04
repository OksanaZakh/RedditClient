package com.example.ozakharc.redditclient;

import com.example.ozakharc.redditclient.api.NewsItem;
import com.example.ozakharc.redditclient.presenter.NetworkManagerListener;
import com.example.ozakharc.redditclient.view.MvpView;
import com.example.ozakharc.redditclient.view.adapter.OnItemClickListener;

import java.util.List;

public interface MainActivityContract {

    interface View extends MvpView, OnItemClickListener {

        void showAlert(String message);

        void startNewActivity(NewsItem item);

        void showData(List<NewsItem> newsItems);

        void showProgressBar();

        void hideProgressBar();
    }

    interface Presenter {

        void onItemClick(NewsItem item);

        void addNewsItem(NewsItem item);

        void attachView(View mvpView);

        void detachView();

        void loadData();

    }

    interface NetworkManager {

        void getDataFromReggit(NetworkManagerListener networkManagerListener);

        void setAfter(String after);

        void setLimit(int limit);

    }
}
