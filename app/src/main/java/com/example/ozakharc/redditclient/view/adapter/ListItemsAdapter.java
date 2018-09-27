package com.example.ozakharc.redditclient.view.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ozakharc.redditclient.R;
import com.example.ozakharc.redditclient.model.NewsItem;

import java.util.List;

public class ListItemsAdapter extends RecyclerView.Adapter<NewsItemViewHolder> {

    private List<NewsItem> items;
    private OnItemClickListener onItemClickListener;

    public void setData(List<NewsItem> items){
        this.items = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NewsItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater= LayoutInflater.from(viewGroup.getContext());
        View view=inflater.inflate(R.layout.news_item_view_holder, viewGroup, false);
        return new NewsItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsItemViewHolder holder, int i) {
        holder.tvAuthor.setText(items.get(i).getAuthor());
        holder.tvDate.setText(items.get(i).getCreatedUtc());
        holder.tvTitle.setText(items.get(i).getTitle());
        holder.tvThumbnail.setText(items.get(i).getThumbnail());
        holder.tvNumComments.setText(items.get(i).getNumComments().toString());

        holder.itemView.setTag(i);
        holder.itemView.setOnClickListener(v -> {
            int position1 = (int) v.getTag();
            if (onItemClickListener != null)
                onItemClickListener.onItemClick(v, position1, items.get(position1));
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

}
