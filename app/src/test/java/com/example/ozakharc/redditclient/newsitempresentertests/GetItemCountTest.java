package com.example.ozakharc.redditclient.newsitempresentertests;


import com.example.ozakharc.redditclient.adapter.NewsItemsContract;
import com.example.ozakharc.redditclient.adapter.NewsItemsPresenterImpl;
import com.example.ozakharc.redditclient.api.NewsItem;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.mockito.Mockito.mock;

@RunWith(value = Parameterized.class)
public class GetItemCountTest {

    private NewsItemsPresenterImpl presenter;
    private int expectedResult;

    public GetItemCountTest(int listSize, int expectedResult) {
        presenter=new NewsItemsPresenterImpl();
        List<NewsItem> items=new ArrayList<>();
        for (int i=0; i<listSize; i++){
            items.add(new NewsItem());
        }
        NewsItemsContract.View adapter=mock(NewsItemsContract.View.class);
        presenter.attachView(adapter);
        presenter.setData(items);
        this.expectedResult = expectedResult;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data(){
        return Arrays.asList(new Object[][]{
                {12, 14},
                {25, 28},
                {50, 55},
                {2, 3}
        });
    }

    @Test
    public void getStringDate_CorrectDays_ReturnEquals() {
        int actualResult=presenter.getItemCount();
        Assert.assertEquals(expectedResult, actualResult);
    }

}
