package com.example.ozakharc.redditclient;

import android.support.annotation.VisibleForTesting;

import com.example.ozakharc.redditclient.adapter.ListItemsContract;
import com.example.ozakharc.redditclient.api.NewsItem;
import com.example.ozakharc.redditclient.adapter.OnItemClickListener;

import java.util.List;

public interface MainActivityContract {

    interface View extends MvpView {

        void showAlert(String message);

        void startNewActivity(NewsItem item);

        void showProgressBar();

        void hideProgressBar();
    }

    interface Presenter extends OnItemClickListener{

        void setAdapterPresenter(ListItemsContract.ListItemsPresenter presenter);

        void addNewsItem(NewsItem item);

        void attachView(View mvpView);

        void detachView();

        void loadData();

        void cleanUp();

        @VisibleForTesting
        List<NewsItem> getNewsItems();
    }


}
