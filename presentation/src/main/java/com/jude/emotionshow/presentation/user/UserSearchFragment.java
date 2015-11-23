package com.jude.emotionshow.presentation.user;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.ViewGroup;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.list.BeamListFragment;
import com.jude.beam.expansion.list.ListConfig;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.emotionshow.domain.entities.PersonBrief;
import com.jude.emotionshow.presentation.main.SearchPresenter;

/**
 * Created by zhuchenxi on 15/11/23.
 */
@RequiresPresenter(UserSearchPresenter.class)
public class UserSearchFragment  extends BeamListFragment<UserSearchPresenter,PersonBrief> implements SearchPresenter.OnSearchAsked{
    @Override
    protected BaseViewHolder getViewHolder(ViewGroup parent, int viewType) {
        return new UserViewHolder(parent);
    }

    @Override
    protected ListConfig getConfig() {
        return super.getConfig().setLoadmoreAble(false).setRefreshAble(false).setNoMoreAble(false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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
