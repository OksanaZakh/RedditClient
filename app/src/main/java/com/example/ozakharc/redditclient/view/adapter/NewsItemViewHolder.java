package com.example.ozakharc.redditclient.view.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ozakharc.redditclient.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsItemViewHolder extends RecyclerView.ViewHolder {

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
}
