package com.jude.emotionshow.presentation.seed;

import android.os.Bundle;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.ViewGroup;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.list.BeamListFragment;
import com.jude.beam.expansion.list.ListConfig;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.emotionshow.domain.entities.Seed;
import com.jude.emotionshow.presentation.main.SearchPresenter;

/**
 * Created by zhuchenxi on 15/11/23.
 */
@RequiresPresenter(SeedSearchPresenter.class)
public class SeedSearchFragment extends BeamListFragment<SeedSearchPresenter,Seed> implements SearchPresenter.OnSearchAsked{
    @Override
    protected BaseViewHolder getViewHolder(ViewGroup parent, int viewType) {
        return new SeedViewHolder(parent);
    }

    @Override
    protected ListConfig getConfig() {
        return super.getConfig().setLoadmoreAble(false).setRefreshAble(false).setNoMoreAble(false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getListView().setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        getListView().showRecycler();
    }

    @Override
    public void search(String query) {
        if (!TextUtils.isEmpty(query))
        getPresenter().search(query);
        else
            getPresenter().getAdapter().clear();
    }
}
