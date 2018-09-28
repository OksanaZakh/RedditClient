package com.example.ozakharc.redditclient;

import com.example.ozakharc.redditclient.model.NewsItem;
import com.example.ozakharc.redditclient.presenter.MvpPresenter;
import com.example.ozakharc.redditclient.view.MvpView;

public interface ListActivityMvp {

    interface View extends MvpView {
        void showError();
        void itemClicked();
        void showDetailedFragment();
        void showNoInternetConnection();
        void updateList(NewsItem newsItem);
    }

    interface Presenter extends MvpPresenter<View> {
        void getDataFromModel();
        void showNewItem(NewsItem newsItem);
        void showFailRequest();
        void showNoInternetConnection();
    }

    interface Model{
        void getDataFromReddit();
        void setPresenter(ListActivityMvp.Presenter presenter);
    }

}
