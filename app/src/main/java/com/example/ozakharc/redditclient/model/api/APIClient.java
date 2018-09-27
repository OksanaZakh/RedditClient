package com.example.ozakharc.redditclient.model.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class APIClient {

    public static final String BASE_URL = "https://www.reddit.com/";


    private static Retrofit getRetrofitInstance() {
        return new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
    }

    public static APIService getApiService() {
        return getRetrofitInstance().create(APIService.class);
    }


//    public void getAndroidNews() {
//        if (initConnection()) {
//            Call<BaseResponse> call = getApiService().getLatestNews(after);
//            call.enqueue(new Callback<BaseResponse>() {
//
//                @Override
//                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
//                    if (response.isSuccessful()) {
//                        BaseResponse baseResponse=response.body();
//                        setAfter(baseResponse.getData().getAfter());
//                        List<Child> children=baseResponse.getData().getChildren();
//                        for (Child child:children){
//                            NewsItem newsItem =new NewsItem();
//                            newsItem.setAuthor(child.getChildDta().getAuthor());
//                            newsItem.setCreatedUtc(child.getChildDta().getCreatedUtc());
//                            newsItem.setNumComments(child.getChildDta().getNumComments());
//                            newsItem.setSelftext(child.getChildDta().getSelftext());
//                            newsItem.setThumbnail(child.getChildDta().getThumbnail());
//                            newsItem.setTitle(child.getChildDta().getTitle());
//                            newsItem.setPhotoUrl(child.getChildDta().getPreview().getImages().get(0).getSource().getUrl());
//                            newsItems.add(newsItem);
//                        }
//
//                    } else {
//                        //TODO
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<BaseResponse> call, Throwable t) {
//                    //TODO
//                }
//            });
//        } else {
//                //TODO
//        }
//    }
//
//    public boolean initConnection() {
//        return ((ConnectivityManager) Objects.requireNonNull(App.getInstance().getSystemService
//                (Context.CONNECTIVITY_SERVICE))).getActiveNetworkInfo() != null;
//    }
//
//    public void setAfter(String after) {
//        this.after = after;
//    }

}
