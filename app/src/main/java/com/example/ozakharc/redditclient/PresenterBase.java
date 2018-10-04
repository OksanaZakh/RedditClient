package com.example.ozakharc.redditclient;

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