package com.example.ozakharc.redditclient.adapter;

import com.example.ozakharc.redditclient.MainActivityContract;
import com.example.ozakharc.redditclient.api.NewsItem;

import java.util.List;

public interface NewsItemsContract {

    interface View {

        void update();
    }

    interface Presenter {

        int getItemCount();

        void onBindViewAtPosition(int i, RowView viewHolder);

        int getItemType(int position);

        void attachView(View adapter);

        void setData(List<NewsItem> newsItems);

        void setClickListener(OnItemClickListener clickListener);

        void loadNewData();

        void attacheMainPresenter(MainActivityContract.Presenter presenter);
    }
}
