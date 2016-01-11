package com.jude.emotionshow.presentation.shop;

import android.os.Bundle;

import com.jude.beam.bijection.Presenter;
import com.jude.emotionshow.data.model.CommonModel;
import com.jude.emotionshow.data.server.ServiceResponse;
import com.jude.emotionshow.domain.entities.Banner;

import java.util.List;

import rx.subjects.BehaviorSubject;

/**
 * Created by mike on 2015/12/23.
 */
public class ShopMainPresenter extends Presenter<ShopMainFragment>{
    private BehaviorSubject<List<Banner>> mBannerSubject = BehaviorSubject.create();

    @Override
    protected void onCreate(ShopMainFragment view, Bundle savedState) {
        super.onCreate(view, savedState);
        CommonModel.getInstance().getBanner().subscribe(new ServiceResponse<List<Banner>>() {
            @Override
            public void onNext(List<Banner> banners) {
                mBannerSubject.onNext(banners);
            }
        });
    }

    @Override
    protected void onCreateView(ShopMainFragment view) {
        super.onCreateView(view);
        mBannerSubject.subscribe(data -> getView().setBanner(data));
    }
}
