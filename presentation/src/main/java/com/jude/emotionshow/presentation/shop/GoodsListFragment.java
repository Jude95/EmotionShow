package com.jude.emotionshow.presentation.shop;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.list.BeamListFragment;
import com.jude.beam.expansion.list.ListConfig;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.emotionshow.domain.entities.Goods;

/**
 * Created by mike on 2015/12/23.
 */
@RequiresPresenter(GoodsListPresenter.class)
public class GoodsListFragment extends BeamListFragment<GoodsListPresenter,Goods>{
    @Override
    protected BaseViewHolder getViewHolder(ViewGroup parent, int viewType) {
        return new ShopViewHolder(parent);
    }

    @Override
    protected ListConfig getConfig() {
        return super.getConfig().setLoadmoreAble(true).setRefreshAble(false).setNoMoreAble(false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getListView().addItemDecoration(new DividerGridItemDecoration(getActivity()));
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getListView().setLayoutManager(new GridLayoutManager(getActivity(),2));
    }

    //降序1
    public void sortByDesc(){
//        getPresenter().sortByDesc();
    }

    //升序0
    public void sortByAscOrDesc(){
        getPresenter().sortByAscOrDesc();
    }
}
