package com.example.ozakharc.redditclient.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.example.ozakharc.redditclient.R;

public class NewsItemsAdapter extends RecyclerView.Adapter<AbstractViewHolder> implements NewsItemsContract.View {

    NewsItemsContract.Presenter presenter;

    public NewsItemsAdapter(NewsItemsContract.Presenter presenter) {
        this.presenter = presenter;
        presenter.attachView(this);
    }

    @Override
    public void update() {
        notifyDataSetChanged();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1)) {
                    presenter.loadNewData();
                }
            }
        });
    }

    @NonNull
    @Override
    public AbstractViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        android.view.View view;
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
        return presenter.getItemType(position);
    }

    @Override
    public void onBindViewHolder(@NonNull AbstractViewHolder viewHolder, int i) {
        presenter.onBindViewAtPosition(i, viewHolder);
    }

    @Override
    public int getItemCount() {
        return presenter.getItemCount();
    }


}
