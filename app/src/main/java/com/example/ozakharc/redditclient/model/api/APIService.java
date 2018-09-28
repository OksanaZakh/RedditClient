package com.example.ozakharc.redditclient.model.api;

import com.example.ozakharc.redditclient.model.api.response.BaseResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIService {

    @GET("r/android/new/.json?limit=20")
    Call<BaseResponse> getLatestNews(@Query("after") String after);
}
