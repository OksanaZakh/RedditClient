package com.example.ozakharc.redditclient.model.api;

import com.example.ozakharc.redditclient.utils.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class APIClient {


    private static Retrofit getRetrofitInstance() {
        return new Retrofit.Builder().baseUrl(Constants.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
    }

    public static APIService getApiService() {
        return getRetrofitInstance().create(APIService.class);
    }

}
