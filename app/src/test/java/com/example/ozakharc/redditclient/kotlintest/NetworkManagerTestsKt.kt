package com.example.ozakharc.redditclient.kotlintest

import com.example.ozakharc.redditclient.InternetConnection
import com.example.ozakharc.redditclient.NetworkManagerImpl
import com.example.ozakharc.redditclient.NetworkManagerListener
import com.example.ozakharc.redditclient.api.APIService
import com.example.ozakharc.redditclient.api.response.BaseResponse

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

import java.io.IOException

import retrofit2.mock.Calls

import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

@RunWith(MockitoJUnitRunner::class)
class NetworkManagerTestsKt {

    @Mock
    private val mockedInternetConnection: InternetConnection? = null
    @Mock
    private val mockedListener: NetworkManagerListener? = null
    @Mock
    private val mockedService: APIService? = null
    @InjectMocks
    private val networkManager = NetworkManagerImpl(mockedInternetConnection)
    private val limit = 20
    private val after = ""

    @Before
    fun setup() {
        networkManager.setListener(mockedListener)
    }

    @Test
    fun getDataFromReddit_whenNoInternet_callOnNetworkIsUnavailable() {
        `when`(mockedInternetConnection!!.isAvailable).thenReturn(false)
        networkManager.getDataFromReddit(after, limit)
        verify<NetworkManagerListener>(mockedListener).onNetworkIsUnavailable()
    }

    @Test
    fun getDataFromReddit_success_callOnSuccessResponse() {
        val baseResponse = BaseResponse()
        val call = Calls.response(baseResponse)
        `when`(mockedInternetConnection!!.isAvailable).thenReturn(true)
        `when`(mockedService!!.getLatestNews(after, limit)).thenReturn(call)
        networkManager.getDataFromReddit(after, limit)
        verify<NetworkManagerListener>(mockedListener).onSuccessResponse(any(BaseResponse::class.java))
    }

    @Test
    fun getDataFromReddit_failure_callOnResponseFailure() {
        val call = Calls.failure<BaseResponse>(IOException())
        `when`(mockedInternetConnection!!.isAvailable).thenReturn(true)
        `when`(mockedService!!.getLatestNews(after, limit)).thenReturn(call)
        networkManager.getDataFromReddit(after, limit)
        verify<NetworkManagerListener>(mockedListener).onResponseFailure()
    }

}
