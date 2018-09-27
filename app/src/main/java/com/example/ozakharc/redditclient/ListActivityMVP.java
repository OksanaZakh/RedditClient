package com.example.ozakharc.redditclient;

import com.example.ozakharc.redditclient.model.NewsItem;

public interface ListActivityMVP {
    interface View{
        void loadData();
        void showError();
        void itemClicked();
        void showDetailedFragment();
    }

    interface Presenter{
        void SetView(ListActivityMVP.View view);
        void LoadData();
        void showNewItem(NewsItem newsItem);
        void showFailRequest();
        void showNoInternetConnection();
    }

    interface Model{
        void getDataFromReddit();
        void setPresenter(ListActivityMVP.Presenter presenter);
    }

}
