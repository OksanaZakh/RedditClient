package com.example.ozakharc.redditclient.presenter;


import com.example.ozakharc.redditclient.ListActivityMvp;
import com.example.ozakharc.redditclient.model.ListModel;
import com.example.ozakharc.redditclient.model.NewsItem;
import com.example.ozakharc.redditclient.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class ListPresenter extends PresenterBase<ListActivityMvp.View> implements ListActivityMvp.Presenter {

    private static final String TAG = "ListPresenter";

    private ListActivityMvp.Model model;
    private List<NewsItem> newsItems;

    public ListPresenter(ListActivityMvp.Model model, List<NewsItem> newsItems) {
        this.model = model;
        this.newsItems = newsItems;
    }

    public ListPresenter() {
    }

    @Override
    public void getDataFromModel() {
        view.showProgressBar();
        if (newsItems.size() > 0) {
            model.setAfter(newsItems.get(newsItems.size() - 1).getAfter());
            model.getDataFromReddit();
        } else {
            model.getDataFromReddit();
        }
    }

    @Override
    public void onItemClick(NewsItem item) {
        view.startNewActivity(item);
    }

    @Override
    public void showFailRequest() {
        if (isViewAttached()) {
            view.hideProgressBar();
            view.showMessage(Constants.ERROR_MESSAGE);
        }
    }

    @Override
    public void showNoInternetConnection() {
        if (isViewAttached()) {
            view.hideProgressBar();
            view.showMessage(Constants.NO_INTERNET_MESSAGE);
        }
    }

    @Override
    public void viewIsReady() {
        newsItems = new ArrayList<>();
        this.model = new ListModel();
        model.setPresenter(this);
        model.setAfter("");
        model.setLimit(20);
        getDataFromModel();
    }

    @Override
    public void addNewsItem(NewsItem item) {
        newsItems.add(item);
        if (isViewAttached()) {
            view.hideProgressBar();
            view.updateList(newsItems);
        }
    }
}
