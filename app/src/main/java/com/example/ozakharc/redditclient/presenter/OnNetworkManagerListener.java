package com.example.ozakharc.redditclient.presenter;

import com.example.ozakharc.redditclient.api.response.BaseResponse;

public interface OnNetworkManagerListener {

    void onFinished(BaseResponse baseResponse);

    void onFailure();

    void onNetworkIsUnavailable();
}
