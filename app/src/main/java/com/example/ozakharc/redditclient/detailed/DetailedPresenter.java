package com.example.ozakharc.redditclient.detailed;

import com.example.ozakharc.redditclient.api.NewsItem;
import com.example.ozakharc.redditclient.PresenterBase;

public class DetailedPresenter extends PresenterBase<DetailedActivityContract.View> implements DetailedActivityContract.Presenter {

    private NewsItem item;

    @Override
    public void setNewsItem(NewsItem item) {
        this.item = item;
        if (item != null) {
            view.populateView(item);
        }
    }

    @Override
    public void onImageClicked() {
        view.showDialog(item.getPhotoUrl());
    }

    @Override
    public void onLinkClicked() {
        view.goToWebPage(item.getUrl());
    }

    @Override
    public void onBackClicked() {
        view.backButtonClicked();
    }

    @Override
    public void onDialogImageClicked() {
        if (view.isDialogVisible()) {
            view.dismissDialog();
        }
    }
}
