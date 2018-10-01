package com.example.ozakharc.redditclient;

import com.example.ozakharc.redditclient.model.NewsItem;
import com.example.ozakharc.redditclient.presenter.MvpPresenter;
import com.example.ozakharc.redditclient.view.MvpView;

public interface DetailedActivityMvp {

    interface View extends MvpView {

        void showDialog(String imageUrl);

        void goToWebPage(String url);
    }

    interface Presenter extends MvpPresenter<DetailedActivityMvp.View> {

        void onImageClicked();

        void onLinkClicked();

        void setNewsItem(NewsItem item);
    }
}
