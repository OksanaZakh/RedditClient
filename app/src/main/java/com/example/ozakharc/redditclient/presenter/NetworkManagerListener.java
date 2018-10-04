package com.example.ozakharc.redditclient.presenter;

import com.example.ozakharc.redditclient.api.response.BaseResponse;

public interface NetworkManagerListener {

    void onFinished(BaseResponse baseResponse);

    void onFailure();

    void onNetworkIsUnavailable();
}
