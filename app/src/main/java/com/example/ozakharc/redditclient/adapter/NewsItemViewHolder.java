package com.example.ozakharc.redditclient.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ozakharc.redditclient.App;
import com.example.ozakharc.redditclient.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsItemViewHolder extends AbstractViewHolder implements ItemRowView {

    @BindView(R.id.tvAuthor)
    public TextView tvAuthor;

    @BindView(R.id.tvDate)
    public TextView tvDate;

    @BindView(R.id.tvTitle)
    public TextView tvTitle;

    @BindView(R.id.ivThumbnail)
    public ImageView tvThumbnail;

    @BindView(R.id.tvNumComments)
    public TextView tvNumComments;

    public NewsItemViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

    }

    @Override
    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    @Override
    public void setAuthor(String author) {
        tvAuthor.setText(author);
    }

    @Override
    public void setThumbnail(String thumbnail) {
        if (URLUtil.isValidUrl(thumbnail)){
                tvThumbnail.setVisibility(View.VISIBLE);
                Picasso.with(App.Companion.getInstance()).load(thumbnail).into(tvThumbnail);
            } else {
                tvThumbnail.setVisibility(View.GONE);
            }
    }

    @Override
    public void setComments(String comments) {
        tvNumComments.setText(comments);
    }

    @Override
    public void setDate(String date) {
        tvDate.setText(date);
    }

    @Override
    public void setClickListener(int pos, OnItemClickListener clickListener) {
        itemView.setTag(pos);
      itemView.setOnClickListener(v -> {
            int position1 = (int) v.getTag();
            if (clickListener != null)
                clickListener.onItemClick(position1);
        });
    }
}
