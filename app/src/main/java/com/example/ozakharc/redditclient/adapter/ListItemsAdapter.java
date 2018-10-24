package com.example.ozakharc.redditclient.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;

import com.example.ozakharc.redditclient.App;
import com.example.ozakharc.redditclient.R;
import com.example.ozakharc.redditclient.api.NewsItem;
import com.example.ozakharc.redditclient.utils.DateConverter;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ListItemsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    AdapterPresenter presenter=new AdapterPresenter(this);

    int numItemsInPage=11;
    private List<NewsItem> items;
    private OnItemClickListener onItemClickListener;

    public void setData(List<NewsItem> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
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
        return position % numItemsInPage * numItemsInPage;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder.getItemViewType() == 0) {
            SeparatorViewHolder holder = (SeparatorViewHolder) viewHolder;
            String pageNumber=(i/numItemsInPage+1)+"";
            holder.tvPageNumber.setText(pageNumber);
        } else {
            int itemPos=i-i/numItemsInPage-1;
            NewsItemViewHolder holder = (NewsItemViewHolder) viewHolder;
            holder.tvAuthor.setText(items.get(itemPos).getAuthor());
            holder.tvDate.setText(DateConverter.getStringDate(items.get(itemPos).getCreatedUtc()));
            holder.tvTitle.setText(items.get(itemPos).getTitle());

            if (URLUtil.isValidUrl(items.get(itemPos).getThumbnail())) {
                holder.tvThumbnail.setVisibility(View.VISIBLE);
                Picasso.with(App.getInstance()).load(items.get(itemPos).getThumbnail()).into(holder.tvThumbnail);
            } else {
                holder.tvThumbnail.setVisibility(View.GONE);
            }
            holder.tvNumComments.setText(items.get(itemPos).getNumComments().toString());

            holder.itemView.setTag(itemPos);
            holder.itemView.setOnClickListener(v -> {
                int position1 = (int) v.getTag();
                if (onItemClickListener != null)
                    onItemClickListener.onItemClick(items.get(position1));
            });
        }
    }

    @Override
    public int getItemCount() {
        if (items != null) {
            return items.size()+items.size()/numItemsInPage;
        } else return 0;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
