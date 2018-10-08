package com.example.ozakharc.redditclient;

import com.example.ozakharc.redditclient.api.APIService;
import com.example.ozakharc.redditclient.api.response.BaseResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.mock.Calls;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class NetworkManagerTests {
    @Mock
    private InternetConnection mockedInternetConnection;
    @Mock
    private NetworkManagerListener mockedListener;
    @Mock
    private APIService mockedService;
    @InjectMocks
    private NetworkManagerImpl networkManager;
    private final int limit=20;
    private final String after="";

    @Before
    public void setup() {
        networkManager.setListener(mockedListener);
        networkManager.setService(mockedService);
    }

    @Test
    public void getDataFromReddit_whenNoInternet_callOnNetworkIsUnavailable() {
        when(mockedInternetConnection.isAvailable()).thenReturn(false);
        networkManager.getDataFromReddit(after, limit);
        verify(mockedListener).onNetworkIsUnavailable();
    }

    @Test
    public void getDataFromReddit_success_callOnSuccessResponse() {
        BaseResponse baseResponse = new BaseResponse();
        Call<BaseResponse> call = Calls.response(baseResponse);
        when(mockedInternetConnection.isAvailable()).thenReturn(true);
        when(mockedService.getLatestNews(after, limit)).thenReturn(call);
        networkManager.getDataFromReddit(after, limit);
        verify(mockedListener).onSuccessResponse(any(BaseResponse.class));
    }

    @Test
    public void getDataFromReddit_failure_callOnResponseFailure() {
        Call<BaseResponse> call = Calls.failure(new IOException());
        when(mockedInternetConnection.isAvailable()).thenReturn(true);
        when(mockedService.getLatestNews(after, limit)).thenReturn(call);
        networkManager.getDataFromReddit(after, limit);
        verify(mockedListener).onResponseFailure();
    }
}
