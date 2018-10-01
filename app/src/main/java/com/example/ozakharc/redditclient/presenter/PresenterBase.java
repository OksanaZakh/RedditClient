package com.example.ozakharc.redditclient.presenter;

import com.example.ozakharc.redditclient.view.MvpView;

public abstract class PresenterBase<T extends MvpView> implements MvpPresenter<T> {

    protected T view;

    @Override
    public void attachView(T mvpView) {
        view = mvpView;
    }

    @Override
    public void detachView() {
        view = null;
    }

    public T getView() {
        return view;
    }

    protected boolean isViewAttached() {
        return view != null;
    }

    @Override
    public void destroy() {

    }
}