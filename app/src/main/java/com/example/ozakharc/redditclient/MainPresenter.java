package com.example.ozakharc.redditclient;

import android.support.annotation.VisibleForTesting;

import com.example.ozakharc.redditclient.adapter.NewsItemsContract;
import com.example.ozakharc.redditclient.api.NewsItem;
import com.example.ozakharc.redditclient.api.response.BaseResponse;
import com.example.ozakharc.redditclient.api.response.Child;
import com.example.ozakharc.redditclient.model.ItemComment;
import com.example.ozakharc.redditclient.model.ItemCommentDao;
import com.example.ozakharc.redditclient.networkmanager.NetworkManager;
import com.example.ozakharc.redditclient.networkmanager.NetworkManagerListener;
import com.example.ozakharc.redditclient.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class MainPresenter extends PresenterBase<MainActivityContract.View>
        implements MainActivityContract.Presenter, NetworkManagerListener {

    private static final String TAG = "MainPresenter";

    private List<NewsItem> newsItems;
    private NetworkManager networkManager;
    private int limit = 20;
    private String after = "";
    private NewsItemsContract.Presenter adapterPresenter;
    private String permalink = "";

    @Override
    public void setAdapterPresenter(NewsItemsContract.Presenter adapterPresenter) {
        this.adapterPresenter = adapterPresenter;
        this.adapterPresenter.setClickListener(this);
        adapterPresenter.attacheMainPresenter(this);
    }

    public MainPresenter(NetworkManager networkManager) {
        newsItems = new ArrayList<>();
        this.networkManager = networkManager;
        this.networkManager.setListener(this);
    }

    @Override
    public void loadData() {
        view.showProgressBar();
        if (newsItems.size() >= limit) {
            setAfter(newsItems.get(newsItems.size() - 1).getAfter());
        }
        networkManager.getDataFromReddit(after, limit);
    }

    @Override
    public void onItemClick(int item) {
        ItemCommentDao dao = App.Companion.getInstance().getAppDb().itemCommentDao();
        List<ItemComment> comments = dao.searchSavedComments(newsItems.get(item).getPermalink());
        permalink = newsItems.get(item).getPermalink();
        if (comments.isEmpty()) {
            networkManager.getComments(newsItems.get(item).getPermalink());
            view.showAlert("Downloading comments");
        }
        view.startNewActivity(newsItems.get(item));
    }

    @Override
    public void addNewsItem(NewsItem item) {
        if (isViewAttached()) {
            view.hideProgressBar();
            newsItems.add(item);
            adapterPresenter.setData(newsItems);
        }
    }

    @Override
    public void onSuccessResponse(BaseResponse baseResponse) {
        String after = baseResponse.getData().getAfter();
        List<Child> children = baseResponse.getData().getChildren();
        for (Child child : children) {
            NewsItem newsItem = new NewsItem();
            newsItem.setAuthor(child.getChildDta().getAuthor());
            newsItem.setCreatedUtc(child.getChildDta().getCreatedUtc());
            newsItem.setNumComments(child.getChildDta().getNumComments());
            newsItem.setSelftext(child.getChildDta().getSelftext());
            newsItem.setThumbnail(child.getChildDta().getThumbnail());
            newsItem.setTitle(child.getChildDta().getTitle());
            newsItem.setPhotoUrl(getPhotoUrl(child));
            newsItem.setAfter(after);
            newsItem.setUrl(child.getChildDta().getUrl());
            newsItem.setPermalink(child.getChildDta().getPermalink());
            newsItem.setId(child.getChildDta().getId());
            addNewsItem(newsItem);
        }
    }

    @Override
    public void onResponseFailure() {
        if (isViewAttached()) {
            view.hideProgressBar();
            view.showAlert(Constants.ERROR_MESSAGE);
        }
    }

    @Override
    public void onNetworkIsUnavailable() {
        if (isViewAttached()) {
            view.hideProgressBar();
            view.showAlert(Constants.NO_INTERNET_MESSAGE);
        }
    }

    private String getPhotoUrl(Child child) {
        String imageUrl = "";
        try {
            imageUrl = child.getChildDta().getPreview().getImages().get(0).getSource().getUrl();
        } catch (Exception e) {
            return imageUrl;
        }
        return imageUrl;
    }

    @Override
    public void onSuccessCommentsResponse(BaseResponse baseResponse) {
        List<Child> children = baseResponse.getData().getChildren();
        if (children.size() > 0) {
            for (Child child : children) {
                ItemComment comment = new ItemComment();
                comment.setComment(child.getChildDta().getBody());
                comment.setPermalink(permalink);
                comment.setId(child.getChildDta().getId());
                App.Companion.getInstance().getAppDb().itemCommentDao().insert(comment);
            }
        }
    }

    @Override
    public void cleanUp() {
        networkManager.cleanUp();
    }

    public void setAfter(String after) {
        this.after = after;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    @VisibleForTesting
    @Override
    public List<NewsItem> getNewsItems() {
        return newsItems;
    }
}
