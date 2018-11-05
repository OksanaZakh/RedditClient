package com.example.ozakharc.redditclient.api;

import com.example.ozakharc.redditclient.api.response.BaseResponse;
import com.example.ozakharc.redditclient.utils.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface APIService {

    @GET(Constants.URL)
    Call<BaseResponse> getLatestNews(@Query(Constants.QUERY_AFTER) String after, @Query(Constants.QUERY_LIMIT) int limit);

    @GET
    Call<List<BaseResponse>> getComments(@Url String url);
}
