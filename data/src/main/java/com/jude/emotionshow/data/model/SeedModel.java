package com.jude.emotionshow.data.model;

import android.content.Context;

import com.jude.beam.model.AbsModel;
import com.jude.emotionshow.data.di.DaggerSeedComponent;
import com.jude.emotionshow.data.server.DefaultTransform;
import com.jude.emotionshow.domain.api.ServiceAPI;
import com.jude.emotionshow.domain.entities.Category;
import com.jude.emotionshow.domain.entities.Topic;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by Mr.Jude on 2015/11/19.
 */
public class SeedModel extends AbsModel {
    @Inject
    ServiceAPI mServiceAPI;

    public static SeedModel getInstance() {
        return getInstance(SeedModel.class);
    }

    @Override
    protected void onAppCreate(Context ctx) {
        super.onAppCreate(ctx);
        DaggerSeedComponent.builder().build().inject(this);
    }

    public Observable<List<Topic>> getTopic(){
        return mServiceAPI.getTopic().compose(new DefaultTransform<>());
    }

    public Observable<List<Category>> getProcess(){
        return mServiceAPI.getProcess().compose(new DefaultTransform<>());
    }
    public Observable<List<Category>> getScence(){
        return mServiceAPI.getScence().compose(new DefaultTransform<>());
    }
}
