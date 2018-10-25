package com.example.ozakharc.redditclient.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;

import com.example.ozakharc.redditclient.App;
import com.example.ozakharc.redditclient.MainActivityContract;
import com.example.ozakharc.redditclient.R;
import com.example.ozakharc.redditclient.api.NewsItem;
import com.example.ozakharc.redditclient.utils.DateConverter;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ListItemsAdapter extends RecyclerView.Adapter<AbstractViewHolder> {

    private static final String TAG = "ListItemsAdapter";
    AdapterPresenter presenter;

    public ListItemsAdapter(AdapterPresenter presenter) {
        this.presenter = presenter;
        presenter.setAdapter(this);
    }

    @NonNull
    @Override
    public AbstractViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view;
        if (getItemViewType(i) == 0) {
            Log.d(TAG, "onCreateViewHolder Separator: type"+getItemViewType(i)+"position "+i);
            view = inflater.inflate(R.layout.news_item_separator, viewGroup, false);
            return new SeparatorViewHolder(view);
        } else {
            Log.d(TAG, "onCreateViewHolder Item: type"+getItemViewType(i)+"position "+i);
            view = inflater.inflate(R.layout.news_item_view_holder, viewGroup, false);
            return new NewsItemViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return presenter.calculateItemType(position);
    }

    @Override
    public void onBindViewHolder(@NonNull AbstractViewHolder viewHolder, int i) {
        Log.d(TAG, "onBindViewHolder: " + i);
        presenter.onBindRepositoryRowViewAtPosition(i, viewHolder);
    }

    @Override
    public int getItemCount() {
        return presenter.calculateItemsCount();
    }


}
