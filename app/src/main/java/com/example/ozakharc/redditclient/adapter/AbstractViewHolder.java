package com.example.ozakharc.redditclient.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

abstract class AbstractViewHolder extends RecyclerView.ViewHolder implements RowView {

    AbstractViewHolder(@NonNull View itemView) {
        super(itemView);
    }
}
