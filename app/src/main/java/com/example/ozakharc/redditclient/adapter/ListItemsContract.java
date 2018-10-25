package com.example.ozakharc.redditclient.adapter;

import com.example.ozakharc.redditclient.api.NewsItem;

import java.util.List;

public interface ListItemsContract {

    interface ListItemsAdapter {

        void notifyDataSetChanged();
    }

    interface ListItemsPresenter {

        int calculateItemsCount();

        void onBindRepositoryRowViewAtPosition(int i, RowView viewHolder);

        int calculateItemType(int position);

        void setAdapter(ListItemsContract.ListItemsAdapter adapter);

        void setData(List<NewsItem> newsItems);

        void setClickListener(OnItemClickListener clickListener);
    }
}
