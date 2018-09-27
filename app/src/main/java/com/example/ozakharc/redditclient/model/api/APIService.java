package com.example.ozakharc.redditclient.model.api;

import com.example.ozakharc.redditclient.model.api.response.BaseResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface APIService {

    @GET("r/android/new/.json?after={after}&limit=20")
    Call<BaseResponse> getLatestNews(@Path("after") String after);
}
