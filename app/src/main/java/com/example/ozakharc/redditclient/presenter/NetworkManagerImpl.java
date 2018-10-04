package com.example.ozakharc.redditclient.presenter;


import android.content.Context;
import android.net.ConnectivityManager;

import com.example.ozakharc.redditclient.App;
import com.example.ozakharc.redditclient.MainActivityContract;
import com.example.ozakharc.redditclient.api.APIService;
import com.example.ozakharc.redditclient.api.RetrofitInstance;
import com.example.ozakharc.redditclient.api.response.BaseResponse;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetworkManagerImpl implements MainActivityContract.NetworkManager {

    private String after = "";
    private int limit = 20;
    private static NetworkManagerImpl instance;

    private NetworkManagerImpl() {
    }

    public static NetworkManagerImpl getInstance() {
        if (instance==null){
            instance=new NetworkManagerImpl();
        }
        return instance;
    }

    @Override
    public void getDataFromReggit(NetworkManagerListener onApiCallListener) {
        APIService service = RetrofitInstance.getRetrofitInstance().create(APIService.class);

        if (initConnection()) {
            Call<BaseResponse> call = service.getLatestNews(after, limit);
            call.enqueue(new Callback<BaseResponse>() {

                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                    if (response.isSuccessful()) {
                        onApiCallListener.onFinished(response.body());
                    } else {
                        onApiCallListener.onFailure();
                    }
                }

                @Override
                public void onFailure(Call<BaseResponse> call, Throwable t) {
                    onApiCallListener.onFailure();
                }
            });
        } else {
            onApiCallListener.onNetworkIsUnavailable();
        }
    }

    private boolean initConnection() {
        return ((ConnectivityManager) Objects.requireNonNull(App.getInstance().getSystemService
                (Context.CONNECTIVITY_SERVICE))).getActiveNetworkInfo() != null;
    }

    @Override
    public void setAfter(String after) {
        this.after = after;
    }

    @Override
    public void setLimit(int limit) {
        this.limit = limit;
    }

}
