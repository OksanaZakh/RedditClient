package com.example.ozakharc.redditclient.model.api;

import com.example.ozakharc.redditclient.model.api.response.BaseResponse;
import com.example.ozakharc.redditclient.utils.Constants;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIService {

    @GET(Constants.URL)
    Call<BaseResponse> getLatestNews(@Query(Constants.QUERY) String after);
}
