package com.jude.emotionshow.presentation.shop;

import android.os.Bundle;

import com.google.gson.Gson;
import com.jude.beam.expansion.data.BeamDataActivityPresenter;
import com.jude.emotionshow.data.model.ShopModel;
import com.jude.emotionshow.domain.entities.GoodsDetail;
import com.jude.emotionshow.domain.entities.GoodsNum;

import java.util.Map;

import rx.Subscriber;

/**
 * Created by mike on 2015/12/23.
 */
public class GoodsDetailPresenter extends BeamDataActivityPresenter<GoodsDetailActivity, GoodsDetail> {
    @Override
    protected void onCreate(GoodsDetailActivity view, Bundle savedState) {
        super.onCreate(view, savedState);
        ShopModel.getInstance().getGoodsDetail(getView().getIntent().getIntExtra("id", 1)).subscribe(this);
    }

    public void getNum(int id, Map<String, String> stringMap) {
        String[] buffer = new String[stringMap.size()];
        int i = 0;
        for (Map.Entry entry : stringMap.entrySet()) {
            String val = (String) entry.getValue();
            buffer[i] = val;
            i++;
        }
        ShopModel.getInstance()
                .getGoodsNum(id, new Gson().toJson(buffer)).subscribe(new Subscriber<GoodsNum>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(GoodsNum num) {
                getView().updateGoodsNum(num.getNum());
            }
        });
    }
}
