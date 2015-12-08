package com.jude.emotionshow.presentation.user;

import android.view.ViewGroup;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.list.BeamListActivity;
import com.jude.beam.expansion.list.ListConfig;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.emotionshow.R;
import com.jude.emotionshow.domain.entities.Notify;

/**
 * Created by zhuchenxi on 15/12/8.
 */
@RequiresPresenter(NotifyItemPresenter.class)
public class NotifyItemActivity extends BeamListActivity<NotifyItemPresenter,Notify> {

    @Override
    protected ListConfig getConfig() {
        return super.getConfig().setRefreshAble(true).setLoadmoreAble(true);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_notify_item;
    }

    @Override
    protected BaseViewHolder getViewHolder(ViewGroup parent, int viewType) {
        return new NotifyViewHolder(parent);
    }
}
