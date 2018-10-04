package com.example.ozakharc.redditclient.detailed;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ozakharc.redditclient.R;
import com.example.ozakharc.redditclient.api.NewsItem;
import com.example.ozakharc.redditclient.utils.Constants;
import com.example.ozakharc.redditclient.utils.DateConverter;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailedActivity extends AppCompatActivity implements DetailedActivityContract.View {

    @BindView(R.id.ivPhoto)
    ImageView ivThumbnail;

    @BindView(R.id.tvTitle)
    TextView tvTitle;

    @BindView(R.id.tvDate)
    TextView tvDate;

    @BindView(R.id.tvDescription)
    TextView tvDescription;

    @BindView(R.id.tvAuthor)
    TextView tvAuthor;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tvLink)
    TextView tvLink;

    DetailedActivityContract.Presenter presenter;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        ButterKnife.bind(this);

        NewsItem item = (NewsItem) getIntent().getExtras().get(Constants.NEWS_ITEM);
        presenter = new DetailedPresenter();
        presenter.attachView(this);
        presenter.setNewsItem(item);

    }

    @Override
    public void populateView(NewsItem item) {
        Picasso.with(this).load(item.getThumbnail()).into(ivThumbnail);
        ivThumbnail.setOnClickListener(v -> presenter.onImageClicked());

        tvAuthor.setText(item.getAuthor());
        tvDate.setText(DateConverter.getStringDate(item.getCreatedUtc()));
        tvTitle.setText(item.getTitle());
        tvDescription.setText(item.getSelftext());
        tvLink.setText(item.getUrl());
        tvLink.setOnClickListener(v -> presenter.onLinkClicked());

        toolbar.setTitle(R.string.app_name);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setNavigationIcon(R.drawable.ic_left_arrow);
        toolbar.setNavigationOnClickListener(v -> presenter.onBackClicked());
    }

    @Override
    public void showDialog(String imageUrl) {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.image_dialog);
        ImageView image = dialog.findViewById(R.id.image);
        Picasso.with(this).load(imageUrl).into(image);
        image.setOnClickListener(v -> presenter.onDialogImageClicked());
        dialog.show();

    }

    @Override
    public void goToWebPage(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
        if (isFinishing()) {
            presenter = null;
        }
    }

    @Override
    public void backButtonClicked() {
        onBackPressed();
    }

    @Override
    public void dismissDialog() {
        dialog.dismiss();
        dialog = null;
    }


    @Override
    public boolean isDialogVisible() {
        return dialog != null;
    }
}
