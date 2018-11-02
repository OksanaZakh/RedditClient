package com.example.ozakharc.redditclient.adapter;

import com.example.ozakharc.redditclient.MainActivityContract;
import com.example.ozakharc.redditclient.api.NewsItem;
import com.example.ozakharc.redditclient.utils.DateConverter;

import java.util.List;

public class NewsItemsPresenterImpl implements NewsItemsContract.Presenter {

    private NewsItemsContract.View adapter;
    private final int itemsPerPage = 10;
    private List<NewsItem> items;
    private OnItemClickListener onItemClickListener;
    private MainActivityContract.Presenter mainPresenter;

    @Override
    public void attacheMainPresenter(MainActivityContract.Presenter presenter) {
        this.mainPresenter = presenter;
    }

    @Override
    public void loadNewData() {
        mainPresenter.loadData();
    }

    @Override
    public void setClickListener(OnItemClickListener clickListener) {
        this.onItemClickListener = clickListener;
    }

    @Override
    public void attachView(NewsItemsContract.View adapter) {
        this.adapter = adapter;
    }

    @Override
    public int getItemType(int position) {
        return position % (itemsPerPage + 1);
    }

    @Override
    public void setData(List<NewsItem> items) {
        this.items = items;
        adapter.update();
    }

    @Override
    public int getItemCount() {
        if (items != null) {
            return items.size() + items.size() / (itemsPerPage + 1) + 1;
        } else return 0;
    }

    @Override
    public void onBindViewAtPosition(int position, RowView viewHolder) {
        if (getItemType(position) == 0) {
            SeparatorRowView holder = (SeparatorRowView) viewHolder;
            String pageNumber = (position / itemsPerPage + 1) + "";
            holder.setPageNumber(pageNumber);
        } else {
            int itemPos = position - position / (itemsPerPage + 1) - 1;
            ItemRowView itemHolder = (ItemRowView) viewHolder;
            itemHolder.setAuthor(items.get(itemPos).getAuthor());
            itemHolder.setDate(DateConverter.getStringDate(items.get(itemPos).getCreatedUtc()));
            itemHolder.setTitle(items.get(itemPos).getTitle());
            itemHolder.setThumbnail(items.get(itemPos).getThumbnail());
            itemHolder.setComments(items.get(itemPos).getNumComments()+"");
            itemHolder.setClickListener(itemPos, onItemClickListener);
        }
    }
}
