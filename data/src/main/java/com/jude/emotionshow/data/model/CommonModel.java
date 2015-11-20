package com.jude.emotionshow.data.model;

import android.content.Context;

import com.jude.beam.model.AbsModel;
import com.jude.emotionshow.data.di.DaggerCommonComponent;
import com.jude.emotionshow.data.server.DefaultTransform;
import com.jude.emotionshow.domain.api.ServiceAPI;
import com.jude.emotionshow.domain.entities.Banner;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by Mr.Jude on 2015/11/18.
 */
public class CommonModel extends AbsModel {
    public static CommonModel getInstance() {
        return getInstance(CommonModel.class);
    }
    @Inject ServiceAPI mServiceAPI;

    @Override
    protected void onAppCreate(Context ctx) {
        super.onAppCreate(ctx);
        DaggerCommonComponent.builder().build().inject(this);
    }

    public Observable<List<Banner>> getBanner(){
        return mServiceAPI.getBanner().compose(new DefaultTransform<>());
    }

    public Observable<Object> feedBack(String content){
        return mServiceAPI.feedback(content).compose(new DefaultTransform<>());
    }
}
