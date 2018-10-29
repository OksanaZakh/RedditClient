package com.example.ozakharc.redditclient.networkmanager;

import com.example.ozakharc.redditclient.InternetConnection;
import com.example.ozakharc.redditclient.api.APIService;
import com.example.ozakharc.redditclient.api.RetrofitInstance;
import com.example.ozakharc.redditclient.api.response.BaseResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitNetworkManager implements NetworkManager {

    private NetworkManagerListener networkManagerListener;
    private InternetConnection internetConnection;
    private APIService service;

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
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                    if (response.isSuccessful()) {
                        networkManagerListener.onSuccessResponse(response.body());
                    } else {
                        networkManagerListener.onResponseFailure();
                    }
                }
                @Override
                public void onFailure(Call<BaseResponse> call, Throwable t) {
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
}
