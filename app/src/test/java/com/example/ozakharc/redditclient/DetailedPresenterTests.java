package com.example.ozakharc.redditclient;

import com.example.ozakharc.redditclient.api.NewsItem;
import com.example.ozakharc.redditclient.detailed.DetailedActivityContract;
import com.example.ozakharc.redditclient.detailed.DetailedPresenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DetailedPresenterTests {

    private DetailedPresenter presenter;
    private NewsItem item;
    @Mock
    private DetailedActivityContract.View mockedView;

    @Before
    public void setup() {
        presenter = new DetailedPresenter();
        presenter.attachView(mockedView);
        item = new NewsItem();
    }

    @Test
    public void setNewsItem_noInteraction_whenItemIsNull() {
        presenter.setNewsItem(null);
        verifyZeroInteractions(mockedView);
    }

    @Test
    public void setNewsItem_callPopulateView_whenItemIsNotNull() {
        presenter.setNewsItem(item);
        verify(mockedView).populateView(eq(item));
    }

    @Test
    public void onImageClicked_showImageDialog() {
        String photoUrl = "photoUrl";
        item.setPhotoUrl(photoUrl);
        presenter.setNewsItem(item);
        presenter.onImageClicked();
        verify(mockedView).showDialog(eq(photoUrl));
    }

    @Test
    public void onDialogImageClicked_whenDialogIsVisible() {
        when(mockedView.isDialogVisible()).thenReturn(true);
        presenter.onDialogImageClicked();
        verify(mockedView).isDialogVisible();
        verify(mockedView).dismissDialog();
    }

    @Test
    public void onDialogImageClicked_whenDialogIsNotVisible() {
        when(mockedView.isDialogVisible()).thenReturn(false);
        presenter.onDialogImageClicked();
        verify(mockedView).isDialogVisible();
        verifyNoMoreInteractions(mockedView);
    }

    @Test
    public void onBackClicked_backPressedInView(){
        presenter.onBackClicked();
        verify(mockedView).backButtonClicked();
    }

    @Test
    public void onLinkClicked_goToWebPage(){
        String url = "url";
        item.setUrl(url);
        presenter.setNewsItem(item);
        presenter.onLinkClicked();
        verify(mockedView).goToWebPage(eq(url));
    }
}
