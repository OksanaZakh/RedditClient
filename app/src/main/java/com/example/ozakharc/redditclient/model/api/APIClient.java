package com.example.ozakharc.redditclient.model.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {

    public static final String BASE_URL="https://www.reddit.com/";

    private static Retrofit getRetrofitInstance() {
        return new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
    }

    public static APIService getApiService() {
        return getRetrofitInstance().create(APIService.class);
    }


}
