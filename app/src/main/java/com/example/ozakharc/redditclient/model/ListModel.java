package com.example.ozakharc.redditclient.model;

import android.content.Context;
import android.net.ConnectivityManager;

import com.example.ozakharc.redditclient.App;
import com.example.ozakharc.redditclient.ListActivityMvp;
import com.example.ozakharc.redditclient.model.api.APIClient;
import com.example.ozakharc.redditclient.model.api.response.BaseResponse;
import com.example.ozakharc.redditclient.model.api.response.Child;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListModel implements ListActivityMvp.Model {

    private List<NewsItem> newsItems = new ArrayList<>();
    private String after = "";

    private ListActivityMvp.Presenter presenter;

    @Override
    public void setPresenter(ListActivityMvp.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void getDataFromReddit() {
        if (initConnection()) {
            Call<BaseResponse> call = APIClient.getApiService().getLatestNews(after);
            call.enqueue(new Callback<BaseResponse>() {

                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                    if (response.isSuccessful()) {
                        BaseResponse baseResponse = response.body();
                        if (!getAfter().equals(baseResponse.getData().getAfter())) {
                            setAfter(baseResponse.getData().getAfter());
                        }
                        List<Child> children = baseResponse.getData().getChildren();
                        for (Child child : children) {
                            NewsItem newsItem = new NewsItem();
                            newsItem.setAuthor(child.getChildDta().getAuthor());
                            newsItem.setCreatedUtc(child.getChildDta().getCreatedUtc());
                            newsItem.setNumComments(child.getChildDta().getNumComments());
                            newsItem.setSelftext(child.getChildDta().getSelftext());
                            newsItem.setThumbnail(child.getChildDta().getThumbnail());
                            newsItem.setTitle(child.getChildDta().getTitle());
                            newsItem.setPhotoUrl(child.getChildDta().getPreview().getImages().get(0).getSource().getUrl());
                            newsItems.add(newsItem);
                            presenter.showNewItem(newsItem);
                        }

                    } else {
                        presenter.showFailRequest();
                    }
                }

                @Override
                public void onFailure(Call<BaseResponse> call, Throwable t) {
                   presenter.showFailRequest();
                }
            });
        } else {
            presenter.showNoInternetConnection();
        }
    }

    public boolean initConnection() {
        return ((ConnectivityManager) Objects.requireNonNull(App.getInstance().getSystemService
                (Context.CONNECTIVITY_SERVICE))).getActiveNetworkInfo() != null;
    }

    public void setAfter(String after) {
        this.after = after;
    }

    public String getAfter() {
        return after;
    }
}