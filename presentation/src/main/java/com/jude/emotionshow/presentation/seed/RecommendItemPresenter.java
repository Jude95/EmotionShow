package com.jude.emotionshow.presentation.seed;

import android.os.Bundle;

import com.jude.beam.expansion.list.BeamListFragmentPresenter;
import com.jude.emotionshow.data.model.SeedModel;
import com.jude.emotionshow.domain.entities.Seed;

/**
 * Created by Mr.Jude on 2015/11/22.
 */
public class RecommendItemPresenter extends BeamListFragmentPresenter<RecommendItemFragment,Seed> {
    private int type;
    @Override
    protected void onCreate(RecommendItemFragment view, Bundle savedState) {
        super.onCreate(view, savedState);
        type = getView().getArguments().getInt("type");
    }

    @Override
    protected void onCreateView(RecommendItemFragment view) {
        super.onCreateView(view);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        SeedModel.getInstance().getRecommendSeedList(type, getCurPage())
                .unsafeSubscribe(getRefreshSubscriber());
    }

    @Override
    public void onLoadMore() {
        SeedModel.getInstance().getRecommendSeedList(type, getCurPage()).unsafeSubscribe(getMoreSubscriber());
    }
}
