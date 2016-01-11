package com.jude.emotionshow.presentation.shop;

import android.os.Bundle;

import com.jude.beam.expansion.list.BeamListActivityPresenter;
import com.jude.emotionshow.data.model.ShopModel;
import com.jude.emotionshow.domain.entities.Order;

/**
 * Created by mike on 2015/12/28.
 */
public class OrderListPresenter extends BeamListActivityPresenter<OrderListActivity,Order>{
    @Override
    protected void onCreate(OrderListActivity view, Bundle savedState) {
        super.onCreate(view, savedState);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        ShopModel.getInstance().getOrderList().unsafeSubscribe(getRefreshSubscriber());
    }
}
