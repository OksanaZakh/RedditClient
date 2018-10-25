package com.example.ozakharc.redditclient.adapter;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.example.ozakharc.redditclient.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SeparatorViewHolder extends AbstractViewHolder implements SeparatorRowView {

    @BindView(R.id.pageNumber)
    public TextView tvPageNumber;


    public SeparatorViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void setPageNumber(String pageNumber) {
        tvPageNumber.setText(pageNumber);
    }
}
