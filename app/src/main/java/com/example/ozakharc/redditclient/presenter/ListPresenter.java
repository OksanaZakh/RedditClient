package com.example.ozakharc.redditclient.presenter;

import android.util.Log;

import com.example.ozakharc.redditclient.ListActivityMvp;
import com.example.ozakharc.redditclient.model.ListModel;
import com.example.ozakharc.redditclient.model.NewsItem;

public class ListPresenter extends PresenterBase<ListActivityMvp.View> implements ListActivityMvp.Presenter {

    private ListModel model;
    private ListActivityMvp.View view;


    @Override
    public void getDataFromModel(String after) {
        model.getDataFromReddit(after);
    }

    @Override
    public void showNewItem(NewsItem newsItem) {
        view.updateList(newsItem);
    }

    @Override
    public void showFailRequest() {
        view.showError();
    }

    @Override
    public void showNoInternetConnection() {
        view.showNoInternetConnection();
    }

    @Override
    public void attachView(ListActivityMvp.View mvpView) {
        this.view=mvpView;
    }

    @Override
    public void viewIsReady() {
        this.model = new ListModel();
        model.setPresenter(this);
        model.getDataFromReddit();
    }
}
