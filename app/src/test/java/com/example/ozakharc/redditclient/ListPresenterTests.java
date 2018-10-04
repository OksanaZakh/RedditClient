package com.example.ozakharc.redditclient;

import com.example.ozakharc.redditclient.model.NewsItem;
import com.example.ozakharc.redditclient.presenter.ListPresenter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ListPresenterTests {

    private ListActivityMvp.View mockedView;
    private ListActivityMvp.Model mockedModel;
    private ListPresenter presenter;
    private List<NewsItem> items;

    @Captor
    private ArgumentCaptor<NewsItem> captor;

    @Before
    public void setup() {
        mockedModel = mock(ListActivityMvp.Model.class);
        mockedView = mock(ListActivityMvp.View.class);
        items = new ArrayList<>();
        presenter = new ListPresenter(mockedModel, items);
        presenter.attachView(mockedView);
    }

    @Test
    public void startNewActivity_whenItemClicked() {
        NewsItem item = new NewsItem();
        presenter.onItemClick(item);
        verify(mockedView, times(1)).startNewActivity(item);
    }

    @Test
    public void showFailRequest_whenViewIsAttached() {
        String correctMessage = "Error occur, can't download data from Reddit!";
        presenter.showFailRequest();
        verify(mockedView).hideProgressBar();
        verify(mockedView).showMessage(correctMessage);
    }

    @Test
    public void notShowFailRequest_whenViewIsDetached() {
        presenter.detachView();
        presenter.showFailRequest();
        verifyZeroInteractions(mockedView, mockedModel);
    }

    @Test
    public void showNoInternetConnection_whenViewIsAttached() {
        String correctMessage = "Bad internet connection, can't download a data from Reddit!";
        presenter.showNoInternetConnection();
        verify(mockedView).hideProgressBar();
        verify(mockedView).showMessage(correctMessage);
    }

    @Test
    public void notShowNoInternetConnection_whenViewIsDetached() {
        presenter.detachView();
        presenter.showNoInternetConnection();
        verifyZeroInteractions(mockedView, mockedModel);
    }

    @Test
    public void detachView_isCorrect() {
        presenter.detachView();
        Assert.assertFalse(presenter.isViewAttached());
        Assert.assertNull(presenter.getView());
    }

    @Test
    public void addNewsItem_whenViewIsAttached() {
        NewsItem item = new NewsItem();
        presenter.addNewsItem(item);
        Assert.assertSame(item, items.get(0));
        verify(mockedView).hideProgressBar();
        verify(mockedView).updateList(items);
    }

    @Test
    public void notAddNewsItem_whenViewIsDetached() {
        NewsItem item = new NewsItem();
        presenter.detachView();
        presenter.addNewsItem(item);
        verifyZeroInteractions(mockedModel, mockedView);
    }

    @Test
    public void getDataFromModel_withDefaultValues_whenNoItemsShown() {
        presenter.getDataFromModel();
        verify(mockedView).showProgressBar();
        verify(mockedModel).getDataFromReddit();
    }

    @Test
    public void getDataFromModel_withDefinedAfterValue_withPagination() {
        String afterValueOfLastItem = "after";
        String expectedAfterValueOfLastItem = "after";
        NewsItem newsItem = new NewsItem();
        newsItem.setAfter(afterValueOfLastItem);
        items.add(newsItem);
        presenter.getDataFromModel();
        verify(mockedView).showProgressBar();
        verify(mockedModel).setAfter(expectedAfterValueOfLastItem);
        verify(mockedModel).getDataFromReddit();
    }


}
