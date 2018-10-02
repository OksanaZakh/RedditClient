package com.example.ozakharc.redditclient;

import com.example.ozakharc.redditclient.model.NewsItem;
import com.example.ozakharc.redditclient.presenter.ListPresenter;
import com.example.ozakharc.redditclient.presenter.PresenterBase;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ListPresenterTests {

    ListActivityMvp.View mockedView;
    ListActivityMvp.Model mockedModel;
    ListPresenter presenter;

    @Before
    public void setup(){
        mockedModel=mock(ListActivityMvp.Model.class);
        mockedView=mock(ListActivityMvp.View.class);
        presenter = new ListPresenter();
        presenter.attachView(mockedView);
    }

    @Test
    public void testOnItemClick(){
        NewsItem item=new NewsItem();
        mockedView.onItemClick(item);
        presenter.onItemClick(item);
        verify(mockedView, times(1)).startNewActivity(item);
    }




}
