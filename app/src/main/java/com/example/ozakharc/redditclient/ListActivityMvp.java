package com.example.ozakharc.redditclient;

import com.example.ozakharc.redditclient.model.NewsItem;
import com.example.ozakharc.redditclient.presenter.MvpPresenter;
import com.example.ozakharc.redditclient.view.MvpView;
import com.example.ozakharc.redditclient.view.adapter.OnItemClickListener;

import java.util.List;

public interface ListActivityMvp {

    interface View extends MvpView, OnItemClickListener {

        void showMessage(String message);

        void startNewActivity(NewsItem item);

        void updateList(List<NewsItem> newsItems);

        void showProgressBar();

        void hideProgressBar();
    }

    interface Presenter extends MvpPresenter<View> {

        void getDataFromModel();

        void showFailRequest();

        void onItemClick(NewsItem item);

        void showNoInternetConnection();

        void addNewsItem(NewsItem item);

        void viewIsReady();

    }

    interface Model {

        void getDataFromReddit();

        void getDataFromReddit(String after);

        void setPresenter(ListActivityMvp.Presenter presenter);
    }

}
