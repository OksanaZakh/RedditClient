package com.example.ozakharc.redditclient.adapter;

import com.example.ozakharc.redditclient.api.NewsItem;
import com.example.ozakharc.redditclient.utils.DateConverter;

import java.util.List;

public class ListItemsPresenterImpl implements ListItemsContract.ListItemsPresenter {

    private ListItemsContract.ListItemsAdapter adapter;
    private final int numItemsInPage = 11;
    private List<NewsItem> items;
    private OnItemClickListener onItemClickListener;


    @Override
    public void setClickListener(OnItemClickListener clickListener) {
        this.onItemClickListener = clickListener;
    }

    @Override
    public void setAdapter(ListItemsContract.ListItemsAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public int calculateItemType(int position) {
        return position % numItemsInPage * numItemsInPage;
    }

    @Override
    public void setData(List<NewsItem> items) {
        this.items = items;
        adapter.notifyDataSetChanged();
    }

    @Override
    public int calculateItemsCount() {
        if (items != null) {
            return items.size() + items.size() / numItemsInPage;
        } else return 0;
    }

    @Override
    public void onBindRepositoryRowViewAtPosition(int position, RowView viewHolder) {
        if (calculateItemType(position) == 0) {
            SeparatorRowView holder = (SeparatorRowView) viewHolder;
            String pageNumber = (position / numItemsInPage + 1) + "";
            holder.setPageNumber(pageNumber);
        } else {
            int itemPos = position - position / numItemsInPage - 1;
            ItemRowView itemHolder = (ItemRowView) viewHolder;
            itemHolder.setAuthor(items.get(itemPos).getAuthor());
            itemHolder.setDate(DateConverter.getStringDate(items.get(itemPos).getCreatedUtc()));
            itemHolder.setTitle(items.get(itemPos).getTitle());
            itemHolder.setThumbnail(items.get(itemPos).getThumbnail());
            itemHolder.setComments(items.get(itemPos).getNumComments().toString());
            itemHolder.setClickListener(itemPos, onItemClickListener);
        }
    }
}
