package com.example.ozakharc.redditclient;

import com.example.ozakharc.redditclient.api.response.BaseResponse;

public interface NetworkManagerListener {

    void onSuccessResponse(BaseResponse baseResponse);

    void onResponseFailure();

    void onNetworkIsUnavailable();

}
