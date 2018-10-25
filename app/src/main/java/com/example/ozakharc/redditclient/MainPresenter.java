package com.example.ozakharc.redditclient;

import android.support.annotation.VisibleForTesting;

import com.example.ozakharc.redditclient.api.NewsItem;
import com.example.ozakharc.redditclient.api.response.BaseResponse;
import com.example.ozakharc.redditclient.api.response.Child;
import com.example.ozakharc.redditclient.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class MainPresenter extends PresenterBase<MainActivityContract.View>
        implements MainActivityContract.Presenter, NetworkManagerListener {

    private List<NewsItem> newsItems;
    private NetworkManager networkManager;
    private int limit=20;
    private String after="";


    public MainPresenter(NetworkManager networkManager) {
        newsItems = new ArrayList<>();
        this.networkManager = networkManager;
        this.networkManager.setListener(this);
    }

    @Override
    public void loadData() {
        view.showProgressBar();
        if (newsItems.size() >= limit) {
            setAfter(newsItems.get(newsItems.size() - 1).getAfter());
        }
        networkManager.getDataFromReddit(after, limit);
    }

    @Override
    public void onItemClick(int item) {
        view.startNewActivity(newsItems.get(item-1-item/10));
    }

    @Override
    public void addNewsItem(NewsItem item) {
        if (isViewAttached()) {
            view.hideProgressBar();
            newsItems.add(item);
            view.showData(newsItems);
        }
    }

    @Override
    public void onSuccessResponse(BaseResponse baseResponse) {
        String after = baseResponse.getData().getAfter();
        List<Child> children = baseResponse.getData().getChildren();
        for (Child child : children) {
            NewsItem newsItem = new NewsItem();
            newsItem.setAuthor(child.getChildDta().getAuthor());
            newsItem.setCreatedUtc(child.getChildDta().getCreatedUtc());
            newsItem.setNumComments(child.getChildDta().getNumComments());
            newsItem.setSelftext(child.getChildDta().getSelftext());
            newsItem.setThumbnail(child.getChildDta().getThumbnail());
            newsItem.setTitle(child.getChildDta().getTitle());
            newsItem.setPhotoUrl(getPhotoUrl(child));
            newsItem.setAfter(after);
            newsItem.setUrl(child.getChildDta().getUrl());
            addNewsItem(newsItem);
        }
    }

    @Override
    public void onResponseFailure() {
        if (isViewAttached()) {
            view.hideProgressBar();
            view.showAlert(Constants.ERROR_MESSAGE);
        }
    }

    @Override
    public void onNetworkIsUnavailable() {
        if (isViewAttached()) {
            view.hideProgressBar();
            view.showAlert(Constants.NO_INTERNET_MESSAGE);
        }
    }

    private String getPhotoUrl(Child child) {
        String imageUrl = "";
        try {
            imageUrl = child.getChildDta().getPreview().getImages().get(0).getSource().getUrl();
        } catch (Exception e) {
            return imageUrl;
        }
        return imageUrl;
    }

    public void setAfter(String after) {
        this.after = after;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    @VisibleForTesting
    @Override
    public List<NewsItem> getNewsItems() {
        return newsItems;
    }
}
