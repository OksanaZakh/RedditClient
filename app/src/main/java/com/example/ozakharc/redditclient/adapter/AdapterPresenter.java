package com.example.ozakharc.redditclient.adapter;

import android.util.Log;

import com.example.ozakharc.redditclient.api.NewsItem;
import com.example.ozakharc.redditclient.utils.DateConverter;

import java.util.List;

public class AdapterPresenter {

    private static final String TAG = "AdapterPresenter";

    public AdapterPresenter(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    ListItemsAdapter adapter;
    int numItemsInPage = 11;
    private List<NewsItem> items;
    private OnItemClickListener onItemClickListener;


    public void setAdapter(ListItemsAdapter adapter) {
        this.adapter = adapter;
    }

    public int calculateItemType(int position) {
        return position % numItemsInPage * numItemsInPage;
    }

    public void setData(List<NewsItem> items) {
        this.items = items;
        adapter.notifyDataSetChanged();
    }

    public int calculateItemsCount() {
        if (items != null) {
            return items.size() + items.size() / numItemsInPage;
        } else return 0;
    }

    public void onBindRepositoryRowViewAtPosition(int position, RowView viewHolder) {
        if (calculateItemType(position) == 0) {
            Log.d(TAG, "onBindRepositoryRowViewAtPosition: separator"+calculateItemType(position));
            SeparatorRowView holder = (SeparatorRowView) viewHolder;
            String pageNumber = (position / numItemsInPage + 1) + "";
            holder.setPageNumber(pageNumber);
            return;
        } else {
            Log.d(TAG, "onBindRepositoryRowViewAtPosition: item"+calculateItemType(position));
            int itemPos = position - position / numItemsInPage - 1;
            ItemRowView itemHolder = (ItemRowView) viewHolder;
            itemHolder.setAuthor(items.get(itemPos).getAuthor());
            itemHolder.setDate(DateConverter.getStringDate(items.get(itemPos).getCreatedUtc()));
            itemHolder.setTitle(items.get(itemPos).getTitle());
            itemHolder.setThumbnail(items.get(itemPos).getThumbnail());
            itemHolder.setComments(items.get(itemPos).getNumComments().toString());
            itemHolder.setClickListener(itemPos, onItemClickListener);
            return;
        }
    }
}
