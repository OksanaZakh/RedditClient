package com.example.ozakharc.redditclient.helpers;

import com.example.ozakharc.redditclient.api.NewsItem;

public class NewsItemCreator {

    public static final String TITLE = "Title";
    public static final String AUTHOR = "Author";
    public static final long CREATED_UTC = 675776575785L;
    public static final int NUM_COMMENTS = 5;
    public static String SELF_TEXT = "Selftext";
    public static String THUMBNAIL = "https://b.thumbs.redditmedia.com/Ay2iHYvpQtIvwAuGPM_Y141nsmnfbT8xi1zEpStFQDY.jpg";
    public static String URL = "https://www.kickstarter.com/projects/charleswongzx/notch-the-only-notch-for-your-devices";
    public static String IMAGE_URL = "https://external-preview.redd.it/T7VGRbIA_ReQYwzDDNqholRndi-1qH_x2BwF-vGScI8.jpg?s=cf7f4bbf48d31faab2178f7527950002cb562936";

    public static NewsItem createNewsItem(){
        NewsItem newsItem = new NewsItem();
        newsItem.setTitle(TITLE);
        newsItem.setSelftext(SELF_TEXT);
        newsItem.setNumComments(NUM_COMMENTS);
        newsItem.setAuthor(AUTHOR);
        newsItem.setCreatedUtc(CREATED_UTC);
        newsItem.setPhotoUrl(IMAGE_URL);
        newsItem.setThumbnail(THUMBNAIL);
        newsItem.setUrl(URL);
        return newsItem;
    }
}
