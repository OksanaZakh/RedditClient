package com.example.ozakharc.redditclient;


import com.example.ozakharc.redditclient.api.NewsItem;
import com.example.ozakharc.redditclient.api.response.BaseResponse;
import com.example.ozakharc.redditclient.api.response.Child;
import com.example.ozakharc.redditclient.api.response.ChildData;
import com.example.ozakharc.redditclient.api.response.Data;
import com.example.ozakharc.redditclient.api.response.Image;
import com.example.ozakharc.redditclient.api.response.Preview;
import com.example.ozakharc.redditclient.api.response.Source;

import java.util.ArrayList;
import java.util.List;

public class DataGeneratorForMainPresenterTest {

    public static String after = "after";
    public static String author = "author";
    public static long createdUtc = 675776575785L;
    public static int numComments = 5;
    public static String selftext = "selftext";
    public static String thumbnail = "thumbnail";
    public static String title = "title";
    public static String url = "url";
    public static String imageUrl = "imageUrl";

    public static BaseResponse createBaseResponse(){
        BaseResponse baseResponse = new BaseResponse();
        Data data = new Data();
        data.setAfter(after);
        List<Child> children = new ArrayList<>();
        Child child = new Child();
        ChildData childData = new ChildData();
        childData.setAuthor(author);
        childData.setCreatedUtc(createdUtc);
        childData.setNumComments(numComments);
        childData.setSelftext(selftext);
        childData.setThumbnail(thumbnail);
        childData.setTitle(title);
        childData.setUrl(url);
        Preview preview = new Preview();
        List<Image> images = new ArrayList<>();
        Image image = new Image();
        Source source = new Source();
        source.setUrl(imageUrl);
        image.setSource(source);
        images.add(image);
        preview.setImages(images);
        childData.setPreview(preview);
        child.setChildDta(childData);
        children.add(child);
        data.setChildren(children);
        baseResponse.setData(data);
        return baseResponse;
    }

    public static List<NewsItem> createNewsItem(){
        NewsItem newsItem = new NewsItem();
        newsItem.setAfter(after);
        newsItem.setAuthor(author);
        newsItem.setCreatedUtc(createdUtc);
        newsItem.setNumComments(numComments);
        newsItem.setSelftext(selftext);
        newsItem.setThumbnail(thumbnail);
        newsItem.setTitle(title);
        newsItem.setUrl(url);
        newsItem.setPhotoUrl(imageUrl);
        List<NewsItem> items = new ArrayList<>();
        items.add(newsItem);
        return items;
    }

    public static BaseResponse createBaseResponseWithEmptyImageUrl(){
        BaseResponse baseResponse = new BaseResponse();
        Data data = new Data();
        data.setAfter(after);
        List<Child> children = new ArrayList<>();
        Child child = new Child();
        ChildData childData = new ChildData();
        childData.setAuthor(author);
        childData.setCreatedUtc(createdUtc);
        childData.setNumComments(numComments);
        childData.setSelftext(selftext);
        childData.setThumbnail(thumbnail);
        childData.setTitle(title);
        childData.setUrl(url);
        Preview preview = new Preview();
        List<Image> images = new ArrayList<>();
        Image image = new Image();
        image.setSource(null);
        images.add(image);
        preview.setImages(images);
        childData.setPreview(preview);
        child.setChildDta(childData);
        children.add(child);
        data.setChildren(children);
        baseResponse.setData(data);
        return baseResponse;
    }

    public static List<NewsItem> createNewsItemWithEmptyImageUrl(){
        NewsItem newsItem = new NewsItem();
        newsItem.setAfter(after);
        newsItem.setAuthor(author);
        newsItem.setCreatedUtc(createdUtc);
        newsItem.setNumComments(numComments);
        newsItem.setSelftext(selftext);
        newsItem.setThumbnail(thumbnail);
        newsItem.setTitle(title);
        newsItem.setUrl(url);
        newsItem.setPhotoUrl("");
        List<NewsItem> items = new ArrayList<>();
        items.add(newsItem);
        return items;
    }
}