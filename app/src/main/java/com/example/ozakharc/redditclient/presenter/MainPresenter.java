package com.example.ozakharc.redditclient.presenter;


import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;

import com.example.ozakharc.redditclient.App;
import com.example.ozakharc.redditclient.MainActivityContract;
import com.example.ozakharc.redditclient.api.NetworkManagerImpl;
import com.example.ozakharc.redditclient.api.NewsItem;
import com.example.ozakharc.redditclient.api.response.BaseResponse;
import com.example.ozakharc.redditclient.api.response.Child;
import com.example.ozakharc.redditclient.utils.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPresenter extends PresenterBase<MainActivityContract.View> implements MainActivityContract.Presenter {

    private List<NewsItem> newsItems;
    private String after = "";
    private int limit = 20;

    public MainPresenter() {
        newsItems = new ArrayList<>();
    }

    @Override
    public void loadData() {
        view.showProgressBar();
        if (newsItems.size() > 0) {
            setAfter(newsItems.get(newsItems.size() - 1).getAfter());
            getDataFromNetwork();
        } else {
            getDataFromNetwork();
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
            view.showAlert(Constants.ERROR_MESSAGE);
        }
    }

    @Override
    public void showNoInternetConnection() {
        if (isViewAttached()) {
            view.hideProgressBar();
            view.showAlert(Constants.NO_INTERNET_MESSAGE);
        }
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
    public void getDataFromNetwork() {
        if (initConnection()) {
            Call<BaseResponse> call = NetworkManagerImpl.getInstance().getApiService().getLatestNews(after, limit);
            call.enqueue(new Callback<BaseResponse>() {

                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                    if (response.isSuccessful()) {
                        BaseResponse baseResponse = response.body();
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

                    } else {
                        showFailRequest();
                    }
                }

                @Override
                public void onFailure(Call<BaseResponse> call, Throwable t) {
                    showFailRequest();
                }
            });
        } else {
            showNoInternetConnection();
        }
    }

    private boolean initConnection() {
        return ((ConnectivityManager) Objects.requireNonNull(App.getInstance().getSystemService
                (Context.CONNECTIVITY_SERVICE))).getActiveNetworkInfo() != null;
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

}
