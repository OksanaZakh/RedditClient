package com.example.ozakharc.redditclient.presenter;

import com.example.ozakharc.redditclient.DetailedActivityMvp;
import com.example.ozakharc.redditclient.model.NewsItem;

public class DetailedPresenter extends PresenterBase<DetailedActivityMvp.View> implements DetailedActivityMvp.Presenter {

    private NewsItem item;


    public void setNewsItem(NewsItem item) {
        this.item = item;
    }

    @Override
    public void onImageClicked() {
        if (item != null && isViewAttached()) {
            view.showDialog(item.getPhotoUrl());
        }
    }

    @Override
    public void onLinkClicked() {
        if (item != null && isViewAttached()) {
            view.goToWebPage(item.getUrl());
        }
    }

}
