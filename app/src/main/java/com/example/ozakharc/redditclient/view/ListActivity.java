package com.example.ozakharc.redditclient.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.ozakharc.redditclient.ListActivityMvp;
import com.example.ozakharc.redditclient.R;
import com.example.ozakharc.redditclient.presenter.ListPresenter;

public class ListActivity extends AppCompatActivity implements ListActivityMvp.View{

    private ListPresenter presenter=new ListPresenter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter.attachView(this);
        presenter.viewIsReady();

    }

    @Override
    public void loadData() {

    }

    @Override
    public void showError() {

    }

    @Override
    public void itemClicked() {

    }

    @Override
    public void showDetailedFragment() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
        if (isFinishing()) {
            presenter.destroy();
        }
    }
}
