package com.example.ozakharc.redditclient.networkmanager;

import android.support.annotation.NonNull;

import com.example.ozakharc.redditclient.InternetConnection;
import com.example.ozakharc.redditclient.api.APIService;
import com.example.ozakharc.redditclient.api.RetrofitInstance;
import com.example.ozakharc.redditclient.api.response.BaseResponse;
import com.example.ozakharc.redditclient.model.RepositoryManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitNetworkManager implements NetworkManager {

    private NetworkManagerListener networkManagerListener;
    private InternetConnection internetConnection;
    private APIService service;
    private RepositoryManager repository;

    public RetrofitNetworkManager(InternetConnection internetConnection) {
        this.internetConnection=internetConnection;
        service=RetrofitInstance.getRetrofitInstance().create(APIService.class);
    }

    @Override
    public void setListener(NetworkManagerListener networkManagerListener) {
        this.networkManagerListener = networkManagerListener;
    }

    @Override
    public void getDataFromReddit(String after, int limit) {
        if (internetConnection.isAvailable()) {
            Call<BaseResponse> call = service.getLatestNews(after, limit);
            call.enqueue(new Callback<BaseResponse>() {

                @Override
                public void onResponse(@NonNull Call<BaseResponse> call, @NonNull Response<BaseResponse> response) {
                    if (response.isSuccessful()) {
                        networkManagerListener.onSuccessResponse(response.body());
                    } else {
                        networkManagerListener.onResponseFailure();
                    }
                }
                @Override
                public void onFailure(@NonNull Call<BaseResponse> call, @NonNull Throwable t) {
                    networkManagerListener.onResponseFailure();
                }
            });
        } else {
            networkManagerListener.onNetworkIsUnavailable();
        }
    }


    @Override
    public void getComments(String url) {
        if (internetConnection.isAvailable()) {
            String responseUrl=url+".json";
            Call<List<BaseResponse>> call = service.getComments(responseUrl);
            call.enqueue(new Callback<List<BaseResponse>>() {

                @Override
                public void onResponse(@NonNull Call<List<BaseResponse>> call, @NonNull Response<List<BaseResponse>> response) {
                    if (response.isSuccessful()) {
                        if(response.body().size()>1) {
                            repository.onSuccessCommentsResponse(response.body().get(1), url);
                        }
                    } else {
                        networkManagerListener.onResponseFailure();
                    }
                }
                @Override
                public void onFailure(@NonNull Call<List<BaseResponse>> call, @NonNull Throwable t) {
                    networkManagerListener.onResponseFailure();
                }
            });
        } else {
            networkManagerListener.onNetworkIsUnavailable();
        }
    }

    @Override
    public void cleanUp() {

    }

    @Override
    public void setRepository(RepositoryManager repository) {
        this.repository=repository;
    }
}
