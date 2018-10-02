package com.example.ozakharc.redditclient;

import com.example.ozakharc.redditclient.presenter.ListPresenter;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;

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
        mockedView.onItemClick(null);
        presenter.onItemClick(null);


    }




}
