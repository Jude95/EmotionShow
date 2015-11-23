package com.jude.emotionshow.presentation.user;

import android.os.Bundle;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.list.BeamListActivity;
import com.jude.beam.expansion.list.ListConfig;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.emotionshow.R;
import com.jude.emotionshow.domain.entities.Seed;
import com.jude.emotionshow.presentation.seed.SeedViewHolder;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Mr.Jude on 2015/11/22.
 */
@RequiresPresenter(CollectionPresenter.class)
public class CollectionActivity extends BeamListActivity<CollectionPresenter, Seed> {

    @Bind(R.id.back_img)
    ImageView backImg;
    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.recycler)
    EasyRecyclerView recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        getListView().setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        back.setOnClickListener(v->finish());
    }

    @Override
    public int getLayout() {
        return R.layout.activity_collection;
    }

    @Override
    protected ListConfig getConfig() {
        return super.getConfig().setNoMoreAble(false);
    }

    @Override
    protected BaseViewHolder getViewHolder(ViewGroup parent, int viewType) {
        return new SeedViewHolder(parent);
    }
}
