package com.example.ozakharc.redditclient.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DataConverter {

    public static String getStringData(long utc){
        Date date = new Date(utc*1000);
        SimpleDateFormat format=new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        return format.format(date);
    }
}
