package com.example.ozakharc.redditclient.presenter;

import android.util.Log;

import com.example.ozakharc.redditclient.ListActivityMvp;
import com.example.ozakharc.redditclient.model.ListModel;
import com.example.ozakharc.redditclient.model.NewsItem;
import com.example.ozakharc.redditclient.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class ListPresenter extends PresenterBase<ListActivityMvp.View> implements ListActivityMvp.Presenter {

    private static final String TAG = "ListPresenter";


    private ListModel model;
    private ListActivityMvp.View view;
    private List<NewsItem> newsItems;


    @Override
    public void getDataFromModel() {
        if (newsItems.size() > 0) {
            model.getDataFromReddit(newsItems.get(newsItems.size() - 1).getAfter());
            Log.d(TAG, "onScrollStateChanged: " + newsItems.get(newsItems.size() - 1).getAfter() + newsItems.size());
        }
        else{
            model.getDataFromReddit();
        }
    }

    @Override
    public void onItemClick(NewsItem item){
        if(item!=null) {
            view.startNewActivity(item);
        }
    }

    @Override
    public void showFailRequest() {
        view.showMessage(Constants.ERROR_MESSAGE);
    }

    @Override
    public void showNoInternetConnection() {
        view.showMessage(Constants.NO_INTERNET_MESSAGE);
    }

    @Override
    public void attachView(ListActivityMvp.View mvpView) {
        this.view=mvpView;
    }

    @Override
    public void viewIsReady() {
        newsItems=new ArrayList<>();
        this.model = new ListModel();
        model.setPresenter(this);
        model.getDataFromReddit();
    }

    @Override
    public void addNewsItem(NewsItem item){
        newsItems.add(item);
        view.updateList(newsItems);
    }
}
