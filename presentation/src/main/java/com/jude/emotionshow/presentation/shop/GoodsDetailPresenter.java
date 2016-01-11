package com.jude.emotionshow.presentation.shop;

import android.os.Bundle;

import com.jude.beam.expansion.data.BeamDataActivityPresenter;
import com.jude.emotionshow.data.model.ShopModel;
import com.jude.emotionshow.domain.entities.GoodsDetail;

/**
 * Created by mike on 2015/12/23.
 */
public class GoodsDetailPresenter extends BeamDataActivityPresenter<GoodsDetailActivity, GoodsDetail> {
    @Override
    protected void onCreate(GoodsDetailActivity view, Bundle savedState) {
        super.onCreate(view, savedState);
        ShopModel.getInstance().getGoodsDetail(getView().getIntent().getIntExtra("id", 1)).subscribe(this);
    }
}
