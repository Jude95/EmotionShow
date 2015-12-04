package com.jude.emotionshow.presentation.user;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.list.BeamListActivity;
import com.jude.beam.expansion.list.ListConfig;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.emotionshow.R;
import com.jude.emotionshow.domain.entities.PersonBrief;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zhuchenxi on 15/12/2.
 */
@RequiresPresenter(FriendsPresenter.class)
public class FriendsActivity extends BeamListActivity<FriendsPresenter, PersonBrief> {

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
        back.setOnClickListener(v->finish());
    }

    @Override
    public int getLayout() {
        return R.layout.activity_friends;
    }

    @Override
    protected ListConfig getConfig() {
        return super.getConfig().setNoMoreAble(false).setRefreshAble(true);
    }

    @Override
    protected BaseViewHolder getViewHolder(ViewGroup parent, int viewType) {
        return new UserRelationViewHolder(parent);
    }
}
