package com.example.ozakharc.redditclient;


import com.example.ozakharc.redditclient.utils.DateConverter;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(value = Parameterized.class)
public class DateConverterTests {

    private long utcParameter;
    private String expectedResult;

    public DateConverterTests(long utcParameter, String expectedResult) {
        this.utcParameter = utcParameter;
        this.expectedResult = expectedResult;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data(){
        return Arrays.asList(new Object[][]{
                {1538552156L, "03 Oct 2018"},
                {601976156L, "28 Jan 1989"},
                {946712156L, "01 Jan 2000"},
                {1595662556L, "25 Jul 2020"}
        });
    }

    @Test
    public void getStringDate_CorrectDays_ReturnEquals() {
        String actualResult = DateConverter.getStringDate(utcParameter);
        Assert.assertEquals(expectedResult, actualResult);
    }
}
