package com.example.ozakharc.redditclient.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.ozakharc.redditclient.R;

public class ListItemsAdapterImpl extends RecyclerView.Adapter<AbstractViewHolder> implements ListItemsContract.ListItemsAdapter{

    ListItemsContract.ListItemsPresenter presenter;

    public ListItemsAdapterImpl(ListItemsContract.ListItemsPresenter presenter) {
        this.presenter = presenter;
        presenter.setAdapter(this);
    }

    @NonNull
    @Override
    public AbstractViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view;
        if (i == 0) {
            view = inflater.inflate(R.layout.news_item_separator, viewGroup, false);
            return new SeparatorViewHolder(view);
        } else {
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
        presenter.onBindRepositoryRowViewAtPosition(i, viewHolder);
    }

    @Override
    public int getItemCount() {
        return presenter.calculateItemsCount();
    }


}
