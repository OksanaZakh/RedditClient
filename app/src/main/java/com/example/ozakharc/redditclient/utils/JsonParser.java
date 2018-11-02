package com.example.ozakharc.redditclient.utils;

import android.util.Log;

import com.example.ozakharc.redditclient.api.response.BaseResponse;
import com.example.ozakharc.redditclient.api.response.Child;
import com.example.ozakharc.redditclient.api.response.ChildData;
import com.example.ozakharc.redditclient.api.response.Data;
import com.example.ozakharc.redditclient.api.response.Image;
import com.example.ozakharc.redditclient.api.response.Preview;
import com.example.ozakharc.redditclient.api.response.Source;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class JsonParser {

    private static final String TAG = "JsonParser";

    public static BaseResponse parse(String s){
        BaseResponse baseResponse=new BaseResponse();
        Data data = new Data();
        try {
            String after="";
            String title="";
            String thumbnail="";
            Integer numComments=0;
            String author="";
            Long createdUtc=0L;
            String selftext="";
            String photoUrl="";
            String url="";
            String permalink="";

            JSONObject jsonObject=new JSONObject(s);
            JSONObject dataObj=jsonObject.getJSONObject("data");
            after=dataObj.getString("after");

            data.setAfter(after);
            List<Child> children = new ArrayList<>();

            JSONArray jsonArray=dataObj.getJSONArray("children");
            for(int i=0; i<jsonArray.length();i++){
                JSONObject childData=jsonArray.getJSONObject(i);
                JSONObject child=childData.getJSONObject("data");
                title=child.getString("title");
                thumbnail=child.getString("thumbnail");
                numComments=child.getInt("num_comments");
                author=child.getString("author");
                createdUtc=child.getLong("created_utc");
                selftext=child.getString("selftext");
                url=child.getString("url");
                permalink=child.getString("permalink");
//
//                JSONObject preview=child.getJSONObject("preview");
//                JSONArray imageArray=preview.getJSONArray("images");
//                JSONObject image=imageArray.getJSONObject(0);
//                JSONObject source=image.getJSONObject("source");
//                photoUrl=source.getString("url");

                Child childObj = new Child();
                ChildData childDataObj = new ChildData();
                childDataObj.setAuthor(author);
                childDataObj.setCreatedUtc(createdUtc);
                childDataObj.setNumComments(numComments);
                childDataObj.setSelftext(selftext);
                childDataObj.setThumbnail(thumbnail);
                childDataObj.setTitle(title);
                childDataObj.setUrl(url);
                childDataObj.setPermalink(permalink);
                Preview previewObj = new Preview();
                List<Image> images = new ArrayList<>();
                Image imageObj = new Image();
                Source sourceObj = new Source();
                sourceObj.setUrl(photoUrl);
                imageObj.setSource(sourceObj);
                images.add(imageObj);
                previewObj.setImages(images);
                childDataObj.setPreview(previewObj);
                childObj.setChildDta(childDataObj);
                children.add(childObj);
            }

            data.setChildren(children);
            baseResponse.setData(data);


        } catch (JSONException e) {
            Log.d(TAG, "parse: ");
            e.printStackTrace();
        }
        return baseResponse;
    }





}
