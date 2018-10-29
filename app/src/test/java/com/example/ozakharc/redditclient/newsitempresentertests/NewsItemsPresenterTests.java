package com.example.ozakharc.redditclient.newsitempresentertests;

import com.example.ozakharc.redditclient.DataGeneratorForMainPresenterTest;
import com.example.ozakharc.redditclient.MainActivityContract;
import com.example.ozakharc.redditclient.adapter.ItemRowView;
import com.example.ozakharc.redditclient.adapter.NewsItemsContract;
import com.example.ozakharc.redditclient.adapter.NewsItemsPresenterImpl;
import com.example.ozakharc.redditclient.adapter.OnItemClickListener;
import com.example.ozakharc.redditclient.adapter.SeparatorRowView;
import com.example.ozakharc.redditclient.api.NewsItem;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class NewsItemsPresenterTests {
    @Mock
    private NewsItemsContract.View mockedAdapter;
    @Mock
    private OnItemClickListener mockedListener;
    @Mock
    private MainActivityContract.Presenter mockedMainPresenter;
    @InjectMocks
    private NewsItemsPresenterImpl presenter;

    @Before
    public void setUp(){
        presenter = new NewsItemsPresenterImpl();
        presenter.attacheMainPresenter(mockedMainPresenter);
        presenter.attachView(mockedAdapter);
        presenter.setClickListener(mockedListener);
    }

    @Test
    public void loadNewData_callLoadData_inMainPresenter(){
        presenter.loadNewData();
        verify(mockedMainPresenter).loadData();
    }

    @Test
    public void setData_callUpdateInAdapter(){
        presenter.setData(new ArrayList<>());
        verify(mockedAdapter).update();
    }

    @Test
    public void onBindViewAtPosition_withZeroPosition_setPageInSeparatorRowView(){
        String expectedPageNumb="2";
        SeparatorRowView separatorRowView=mock(SeparatorRowView.class);
        presenter.onBindViewAtPosition(11, separatorRowView);
        verify(separatorRowView).setPageNumber(eq(expectedPageNumb));
    }

    @Test
    public void onBindViewAtPosition_withNonZeroPosition_setAuthorInItemRowView(){
        List<NewsItem> items=DataGeneratorForMainPresenterTest.createNewsItem();
        presenter.setData(items);
        ItemRowView itemRowView=mock(ItemRowView.class);

        presenter.onBindViewAtPosition(1, itemRowView);

        verify(itemRowView).setAuthor(eq(DataGeneratorForMainPresenterTest.author));
    }

    @Test
    public void onBindViewAtPosition_withNonZeroPosition_setDateInItemRowView(){
        List<NewsItem> items=DataGeneratorForMainPresenterTest.createNewsItem();
        presenter.setData(items);
        ItemRowView itemRowView=mock(ItemRowView.class);

        presenter.onBindViewAtPosition(1, itemRowView);

        verify(itemRowView).setDate(eq(DataGeneratorForMainPresenterTest.date));
    }


    @Test
    public void onBindViewAtPosition_withNonZeroPosition_setTitleInItemRowView(){
        List<NewsItem> items=DataGeneratorForMainPresenterTest.createNewsItem();
        presenter.setData(items);
        ItemRowView itemRowView=mock(ItemRowView.class);

        presenter.onBindViewAtPosition(1, itemRowView);

        verify(itemRowView).setTitle(eq(DataGeneratorForMainPresenterTest.title));
    }


    @Test
    public void onBindViewAtPosition_withNonZeroPosition_setThumbnailInItemRowView(){
        List<NewsItem> items=DataGeneratorForMainPresenterTest.createNewsItem();
        presenter.setData(items);
        ItemRowView itemRowView=mock(ItemRowView.class);

        presenter.onBindViewAtPosition(1, itemRowView);

        verify(itemRowView).setThumbnail(eq(DataGeneratorForMainPresenterTest.thumbnail));
    }

    @Test
    public void onBindViewAtPosition_withNonZeroPosition_setCommentsInItemRowView(){
        List<NewsItem> items=DataGeneratorForMainPresenterTest.createNewsItem();
        presenter.setData(items);
        ItemRowView itemRowView=mock(ItemRowView.class);

        presenter.onBindViewAtPosition(1, itemRowView);

        verify(itemRowView).setComments(eq((DataGeneratorForMainPresenterTest.numComments)+""));
    }

    @Test
    public void onBindViewAtPosition_withNonZeroPosition_setListenerInItemRowView(){
        List<NewsItem> items=DataGeneratorForMainPresenterTest.createNewsItem();
        presenter.setData(items);
        ItemRowView itemRowView=mock(ItemRowView.class);

        presenter.onBindViewAtPosition(1, itemRowView);

        verify(itemRowView).setClickListener(0, mockedListener);
    }

}
