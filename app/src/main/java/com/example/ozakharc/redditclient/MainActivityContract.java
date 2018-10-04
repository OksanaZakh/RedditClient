package com.example.ozakharc.redditclient;

import com.example.ozakharc.redditclient.api.NewsItem;
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

        void getDataFromNetwork();

        void showFailRequest();

        void onItemClick(NewsItem item);

        void showNoInternetConnection();

        void addNewsItem(NewsItem item);

        void attachView(View mvpView);

        void detachView();

        void setAfter(String after);

        void setLimit(int limit);

        void loadData();

    }
}
