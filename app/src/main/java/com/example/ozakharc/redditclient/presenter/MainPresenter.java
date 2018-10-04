package com.example.ozakharc.redditclient.presenter;

import com.example.ozakharc.redditclient.MainActivityContract;
import com.example.ozakharc.redditclient.api.NewsItem;
import com.example.ozakharc.redditclient.api.response.BaseResponse;
import com.example.ozakharc.redditclient.api.response.Child;
import com.example.ozakharc.redditclient.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class MainPresenter extends PresenterBase<MainActivityContract.View>
        implements MainActivityContract.Presenter, NetworkManagerListener {

    private List<NewsItem> newsItems;
    private MainActivityContract.NetworkManager networkManager;


    public MainPresenter(MainActivityContract.NetworkManager networkManager) {
        newsItems = new ArrayList<>();
        this.networkManager = networkManager;
    }

    @Override
    public void loadData() {
        view.showProgressBar();
        if (newsItems.size() > 0) {
            networkManager.setAfter(newsItems.get(newsItems.size() - 1).getAfter());
            networkManager.getDataFromReggit(this);
        } else {
            networkManager.getDataFromReggit(this);
        }
    }

    @Override
    public void onItemClick(NewsItem item) {
        view.startNewActivity(item);
    }

    @Override
    public void addNewsItem(NewsItem item) {
        newsItems.add(item);
        if (isViewAttached()) {
            view.hideProgressBar();
            view.showData(newsItems);
        }
    }

    @Override
    public void onFinished(BaseResponse baseResponse) {
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
    public void onFailure() {
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


}
