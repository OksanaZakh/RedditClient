package com.example.ozakharc.redditclient.presenter;

import com.example.ozakharc.redditclient.view.MvpView;

public abstract class PresenterBase<T extends MvpView> {

    protected T view;

    public void attachView(T mvpView) {
        view = mvpView;
    }

    public void detachView() {
        view = null;
    }

    public T getView() {
        return view;
    }

    public boolean isViewAttached() {
        return view != null;
    }
}