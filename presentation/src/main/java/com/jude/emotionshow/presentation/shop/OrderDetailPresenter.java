package com.jude.emotionshow.presentation.shop;

import android.os.Bundle;

import com.jude.beam.expansion.data.BeamDataActivityPresenter;
import com.jude.emotionshow.data.model.ShopModel;
import com.jude.emotionshow.domain.entities.OrderDetail;
import com.jude.utils.JUtils;

/**
 * Created by mike on 2015/12/28.
 */
public class OrderDetailPresenter extends BeamDataActivityPresenter<OrderDetailActivity, OrderDetail> {
    int id;

    @Override
    protected void onCreate(OrderDetailActivity view, Bundle savedState) {
        super.onCreate(view, savedState);
        id = getView().getIntent().getIntExtra("id", -1);
        if (id == -1) {
            JUtils.Toast("订单不存在");
            getView().finish();
        }
        ShopModel.getInstance().getOrderItem(id).subscribe(getDataSubJect());
    }
}
