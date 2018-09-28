package com.example.ozakharc.redditclient.model.api;

import com.example.ozakharc.redditclient.model.api.response.BaseResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIService {

    @GET("r/android/new/.json?after=&limit=20")
    Call<BaseResponse> getLatestNews();
}
