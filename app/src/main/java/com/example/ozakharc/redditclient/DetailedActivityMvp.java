package com.example.ozakharc.redditclient;

import com.example.ozakharc.redditclient.model.NewsItem;
import com.example.ozakharc.redditclient.view.MvpView;

public interface DetailedActivityMvp {

    interface View extends MvpView {

        void showDialog(String imageUrl);

        void goToWebPage(String url);

        void backButtonClicked();

        void dismissDialog();

        boolean isDialogVisible();
    }

    interface Presenter {

        void onImageClicked();

        void onLinkClicked();

        void setNewsItem(NewsItem item);

        void attachView(View mvpView);

        void detachView();

        void onBackClicked();

        void onDialogImageClicked();
    }
}
