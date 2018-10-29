package com.example.ozakharc.redditclient.newsitempresentertests;


import com.example.ozakharc.redditclient.adapter.NewsItemsPresenterImpl;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class GetItemTypeTest {

    private int position;
    private int expectedResult;
    private NewsItemsPresenterImpl presenter;

    public GetItemTypeTest(int position, int expectedResult) {
        this.position = position;
        this.expectedResult = expectedResult;
        this.presenter=new NewsItemsPresenterImpl();

    }

    @Parameterized.Parameters
    public static Collection<Object[]> data(){
        return Arrays.asList(new Object[][]{
                {0, 0},
                {11, 0},
                {27, 5},
                {34, 1}
        });
    }

    @Test
    public void getStringDate_CorrectDays_ReturnEquals() {
        int actualResult=presenter.getItemType(position);
        Assert.assertEquals(expectedResult, actualResult);
    }
}
