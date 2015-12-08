package com.jude.emotionshow.data.model;

import android.content.Context;

import com.jude.beam.model.AbsModel;
import com.jude.emotionshow.data.di.DaggerCommonComponent;
import com.jude.emotionshow.data.server.DefaultTransform;
import com.jude.emotionshow.domain.api.ServiceAPI;
import com.jude.emotionshow.domain.entities.Banner;
import com.jude.emotionshow.domain.entities.Notify;
import com.jude.emotionshow.domain.entities.PushSet;
import com.jude.emotionshow.domain.entities.Token;

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

    public Observable<Token> getQiNiuToken(){
        return mServiceAPI.qiniuToken();
    }

    public Observable<Object> updateAddress(double lat,double lng){
        return mServiceAPI.uploadAddress(lat,lng).compose(new DefaultTransform<>());
    }

    public Observable<List<Notify>> getNotifyList(int page,String type){
        return mServiceAPI.getNotify(page,type,30).compose(new DefaultTransform<>());
    }

    public Observable<PushSet> getPushSet(){
        return mServiceAPI.getPushSet().compose(new DefaultTransform<>());
    }

    public Observable<Object> uploadPushSet(PushSet set){
        return mServiceAPI.uploadPushSet(
                set.isReceivePraiseMessage()?1:0,
                set.isReceiveCommentMessage()?1:0,
                set.isReceiveFollowMessage()?1:0,
                set.isReceiveInviteMessage()?1:0).compose(new DefaultTransform<>());
    }
}
