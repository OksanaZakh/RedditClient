package com.example.ozakharc.redditclient.presenter;

import com.example.ozakharc.redditclient.DetailedActivityContract;
import com.example.ozakharc.redditclient.api.NewsItem;

public class DetailedPresenter extends PresenterBase<DetailedActivityContract.View> implements DetailedActivityContract.Presenter {

    private NewsItem item;

    @Override
    public void setNewsItem(NewsItem item) {
        this.item = item;
        if(item!=null){
            view.populateView(item);
        }
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

    @Override
    public void onBackClicked() {
        view.backButtonClicked();
    }

    @Override
    public void onDialogImageClicked() {
        if(view.isDialogVisible()){
            view.dismissDialog();
        }
    }
}
