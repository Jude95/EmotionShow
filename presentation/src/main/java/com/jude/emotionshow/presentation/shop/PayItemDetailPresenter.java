package com.jude.emotionshow.presentation.shop;

import android.os.Bundle;

import com.jude.beam.expansion.list.BeamListFragmentPresenter;
import com.jude.emotionshow.data.model.ShopModel;
import com.jude.emotionshow.domain.entities.Pay;

/**
 * Created by mike on 2015/12/29.
 */
public class PayItemDetailPresenter extends BeamListFragmentPresenter<PayItemDetailFragment, Pay> {
    private int type;

    @Override
    protected void onCreate(PayItemDetailFragment view, Bundle savedState) {
        super.onCreate(view, savedState);
        type = getView().getArguments().getInt("type");
        onRefresh();
    }

    @Override
    public void onRefresh() {
        ShopModel.getInstance().getPayList(type, 0).unsafeSubscribe(getRefreshSubscriber());
    }

    @Override
    public void onLoadMore() {
        ShopModel.getInstance().getPayList(type, getCurPage()).unsafeSubscribe(getMoreSubscriber());
    }
}
