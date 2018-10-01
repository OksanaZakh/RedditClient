package com.example.ozakharc.redditclient.model;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;

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

    private ListActivityMvp.Presenter presenter;

    private static final String TAG = "ListModel";


    @Override
    public void setPresenter(ListActivityMvp.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void getDataFromReddit(String after) {
        Log.d(TAG, "getDataFromReddit: " + after);
        if (initConnection()) {
            Call<BaseResponse> call = APIClient.getApiService().getLatestNews(after);
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
                            presenter.addNewsItem(newsItem);
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

    public void getDataFromReddit() {
        getDataFromReddit("");
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



}