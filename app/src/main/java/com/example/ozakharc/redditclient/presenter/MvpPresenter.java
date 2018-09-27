package com.example.ozakharc.redditclient.presenter;

import com.example.ozakharc.redditclient.view.MvpView;

public interface MvpPresenter<V extends MvpView> {

    void attachView(V mvpView);

    void viewIsReady();

    void detachView();

    void destroy();
}