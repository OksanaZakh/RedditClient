package com.example.ozakharc.redditclient;

import com.example.ozakharc.redditclient.adapter.NewsItemsContract;
import com.example.ozakharc.redditclient.api.NewsItem;
import com.example.ozakharc.redditclient.api.response.BaseResponse;
import com.example.ozakharc.redditclient.networkmanager.NetworkManager;
import com.example.ozakharc.redditclient.utils.Constants;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;


@RunWith(MockitoJUnitRunner.class)
public class MainPresenterTests {

    @Mock
    private NewsItemsContract.Presenter mockedItemsPresenter;
    @Mock
    private NetworkManager mockedNetworkManager;
    @Mock
    private MainActivityContract.View mockedView;
    @InjectMocks
    private MainPresenter presenter;

    @Before
    public void setup() {
        presenter.attachView(mockedView);
        presenter.setAdapterPresenter(mockedItemsPresenter);
    }

    @Test
    public void setAdapterPresenter_setClickListener(){
        verify(mockedItemsPresenter).setClickListener(presenter);
    }

    @Test
    public void setAdapterPresenter_attachMainPresenter_toAdapterPresenter(){
        verify(mockedItemsPresenter).attacheMainPresenter(presenter);
    }

    @Test
    public void loadData_progressBar_isShown() {
        presenter.loadData();
        verify(mockedView).showProgressBar();
    }

    @Test
    public void loadData_firstLoading_callToNetworkManager_withCorrectAfterParameter() {
        String defaultAfter = "";
        presenter.loadData();
        verify(mockedNetworkManager).getDataFromReddit(eq(defaultAfter), anyInt());
    }

    @Test
    public void loadData_withPagination_callToNetworkManager_withCorrectAfterParameter() {
        String after = "after";
        for (int i = 0; i < 21; i++) {
            NewsItem item = new NewsItem();
            item.setAfter(after);
            presenter.addNewsItem(item);
        }
        presenter.loadData();
        verify(mockedNetworkManager).getDataFromReddit(eq(after), anyInt());
    }

    @Test
    public void loadData_callToNetworkManager_withCorrectLimitParameter() {
        int expectedLimit = 20;
        presenter.loadData();
        verify(mockedNetworkManager).getDataFromReddit(anyString(), eq(expectedLimit));
    }

    @Test
    public void onItemClick_callView_toStartNewActivity() {
        NewsItem item = new NewsItem();
        presenter.addNewsItem(item);
        presenter.onItemClick(0);
        verify(mockedView).startNewActivity(eq(item));
    }

    @Test
    public void addNewsItem_noInteractions_whenViewIsDetached() {
        NewsItem item = new NewsItem();
        presenter.detachView();

        presenter.addNewsItem(item);

        verify(mockedNetworkManager, times(1)).setListener(presenter);
        verifyNoMoreInteractions(mockedView, mockedNetworkManager);
    }

    @Test
    public void addNewsItem_hideProgressBar_whenViewIsAttached() {
        NewsItem item = new NewsItem();
        presenter.addNewsItem(item);
        verify(mockedView).hideProgressBar();
    }

    @Test
    public void addNewsItem_showItemInView_whenViewIsAttached() {
        NewsItem item = new NewsItem();
        List<NewsItem> items = new ArrayList<>();
        items.add(item);
        presenter.addNewsItem(item);
        verify(mockedItemsPresenter).setData(eq(items));
    }

    @Test
    public void onResponseFailure_noInteraction_whenViewIsDetached() {
        presenter.detachView();
        presenter.onResponseFailure();
        verify(mockedNetworkManager, times(1)).setListener(presenter);
        verifyNoMoreInteractions(mockedView, mockedNetworkManager);
    }

    @Test
    public void onResponseFailure_hideProgressBar_whenViewIsAttached() {
        presenter.onResponseFailure();
        verify(mockedView).hideProgressBar();
    }

    @Test
    public void onResponseFailure_showAlert_whenViewIsAttached() {
        String expectedAlert = Constants.ERROR_MESSAGE;
        presenter.onResponseFailure();
        verify(mockedView).showAlert(eq(expectedAlert));
    }

    @Test
    public void onNetworkIsUnavailable_noInteraction_whenViewIsDetached() {
        presenter.detachView();
        presenter.onNetworkIsUnavailable();
        verify(mockedNetworkManager, times(1)).setListener(presenter);
        verifyNoMoreInteractions(mockedView, mockedNetworkManager);
    }

    @Test
    public void onNetworkIsUnavailable_hideProgressBar_whenViewIsAttached() {
        presenter.onNetworkIsUnavailable();
        verify(mockedView).hideProgressBar();
    }

    @Test
    public void onNetworkIsUnavailable_showAlert_whenViewIsAttached() {
        String expectedAlert = Constants.NO_INTERNET_MESSAGE;
        presenter.onNetworkIsUnavailable();
        verify(mockedView).showAlert(eq(expectedAlert));
    }

    @Test
    public void cleanUp_callNetworkManager(){
        presenter.cleanUp();
        verify(mockedNetworkManager).cleanUp();
    }

    @Test
    public void onGettingSuccessResponse_retrieveCorrectDataFromResponse_withCorrectImageUrl() {
        BaseResponse baseResponse=DataGeneratorForMainPresenterTest
                .createBaseResponse();
        List<NewsItem> items=DataGeneratorForMainPresenterTest.createNewsItem();
        presenter.onSuccessResponse(baseResponse);
        verify(mockedItemsPresenter).setData(eq(items));
    }

    @Test
    public void onGettingSuccessResponse_retrieveCorrectDataFromResponse_withoutImageUrl() {
        BaseResponse baseResponse=DataGeneratorForMainPresenterTest
                .createBaseResponseWithEmptyImageUrl();
        List<NewsItem> items=DataGeneratorForMainPresenterTest.createNewsItemWithEmptyImageUrl();
        presenter.onSuccessResponse(baseResponse);
        verify(mockedItemsPresenter).setData(eq(items));
    }
}
