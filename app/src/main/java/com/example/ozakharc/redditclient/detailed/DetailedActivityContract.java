package com.example.ozakharc.redditclient.detailed;

import com.example.ozakharc.redditclient.api.NewsItem;
import com.example.ozakharc.redditclient.MvpView;

public interface DetailedActivityContract {

    interface View extends MvpView {

        void showDialog(String imageUrl);

        void goToWebPage(String url);

        void backButtonClicked();

        void dismissDialog();

        boolean isDialogVisible();

        void populateView(NewsItem item);

        void showProgressBar();

        void hideProgressBar();
    }

    interface Presenter {

        void onImageClicked();

        void onLinkClicked();

        void setNewsItem(NewsItem item);

        void attachView(View mvpView);

        void detachView();

        void onBackClicked();

        void onDialogImageClicked();

        void dialogImageLoaded();
    }
}
