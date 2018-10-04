package com.example.ozakharc.redditclient.api;

import com.example.ozakharc.redditclient.utils.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class NetworkManagerImpl {

    private APIService apiService;
    private static NetworkManagerImpl instance;


    private NetworkManagerImpl(){
        apiService = createApiService();
    }

    public static NetworkManagerImpl getInstance(){
        if(instance==null){
            instance = new NetworkManagerImpl();
        }
        return instance;
    }

    private APIService createApiService() {
        Retrofit retrofit= new Retrofit.Builder().baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
        return retrofit.create(APIService.class);
    }

    public APIService getApiService() {
        return apiService;
    }

}
