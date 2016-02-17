package com.jude.emotionshow.data.model;

import android.content.Context;

import com.google.gson.Gson;
import com.jude.beam.model.AbsModel;
import com.jude.emotionshow.data.di.DaggerSeedComponent;
import com.jude.emotionshow.data.server.DefaultTransform;
import com.jude.emotionshow.data.server.ServiceResponse;
import com.jude.emotionshow.domain.api.ServiceAPI;
import com.jude.emotionshow.domain.entities.Category;
import com.jude.emotionshow.domain.entities.CategoryDetail;
import com.jude.emotionshow.domain.entities.CategoryPreview;
import com.jude.emotionshow.domain.entities.Seed;
import com.jude.emotionshow.domain.entities.SeedDetail;
import com.jude.emotionshow.domain.entities.SeedEditable;
import com.jude.emotionshow.domain.entities.Topic;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by Mr.Jude on 2015/11/19.
 * 印记操作部分，基本都是网络请求操作。找到对应API即可，ctrl＋左键点函数名找到在哪用的。
 */
public class SeedModel extends AbsModel {
    @Inject
    ServiceAPI mServiceAPI;
    @Inject
    Gson mGson;

    public static SeedModel getInstance() {
        return getInstance(SeedModel.class);
    }

    @Override
    protected void onAppCreate(Context ctx) {
        super.onAppCreate(ctx);
        //模块注入，主要是网络模块
        DaggerSeedComponent.builder().build().inject(this);
    }


    /**
     * 取topic数据
     * @return
     */
    public Observable<List<Topic>> getTopic(){
        return mServiceAPI.getTopic().compose(new DefaultTransform<>());
    }
    /**
     * 取Category数据的Process
     * @return
     */
    public Observable<List<CategoryPreview>> getProcess(){
        return mServiceAPI.getProcess().compose(new DefaultTransform<>());
    }
    /**
     * 取Category数据的Scene
     * @return
     */
    public Observable<List<CategoryPreview>> getScene(){
        return mServiceAPI.getScene().compose(new DefaultTransform<>());
    }

    /**
     * 发布印记
     * @param data
     * @return
     */
    public Observable<Object> publishSeed(SeedEditable data){
        return mServiceAPI.addSeed(data.getContent(), data.getScene(), data.getProcess(), data.getAddress(), data.getScope(), "", mGson.toJson(data.getPictures()))
                .doOnNext(o -> UserModel.getInstance().updateMyInfo().subscribe(new ServiceResponse<>()))
                .compose(new DefaultTransform<>());
    }

    public Observable<SeedDetail> getSeedDetail(int id){
        return mServiceAPI.getSeedDetail(id).compose(new DefaultTransform<>());
    }

    public Observable<Object> comment(int seedId,int commentId,String content){
        return mServiceAPI.comment(seedId, commentId, content).compose(new DefaultTransform<>());
    }

    public Observable<Object> praise(int id){
        return mServiceAPI.praiseSeed(id)
                .doOnNext(o -> UserModel.getInstance().updateMyInfo().subscribe(new ServiceResponse<>()))
                .compose(new DefaultTransform<>());
    }

    public Observable<Object> collect(int id){
        return mServiceAPI.collect(id).compose(new DefaultTransform<>());
    }

    public Observable<Object> report(int id){
        return mServiceAPI.report(id).compose(new DefaultTransform<>());
    }

    public Observable<List<SeedDetail>> getUserSeedList(int id){
        return mServiceAPI.getUserSeedList(-1, id, 1).compose(new DefaultTransform<>());
    }

    public Observable<CategoryDetail> getCategoryDetail(String id,String type){
        return mServiceAPI.getCategoryDetail(id,type).compose(new DefaultTransform<>());

    }

    public Observable<List<Seed>> getCategorySeedList(int id,int page,int type){
        if (type == 0){
            return mServiceAPI.getSceneSeedList(page,id,1).compose(new DefaultTransform<>());
        }else{
            return mServiceAPI.getProcessSeedList(page, id,1).compose(new DefaultTransform<>());
        }

    }

    public Observable<List<Seed>> getRecommendSeedList(int type, int page){
        return mServiceAPI.getRecommendSeedList(type,page).compose(new DefaultTransform<>());
    }

    public Observable<List<Seed>> searchSeed(String text){
        return mServiceAPI.searchSeed(text).compose(new DefaultTransform<>());
    }

    public Observable<CategoryPreview> getActivityList(){
        return mServiceAPI.getActivityList().compose(new DefaultTransform<>());
    }

    public Observable<List<Category>> getCategoryList(){
        return mServiceAPI.getCategoryList().compose(new DefaultTransform<>());
    }

    public Observable<Object> deleteSeed(int id){
        return mServiceAPI.deleteSeed(id).compose(new DefaultTransform<>());
    }

    public Observable<Object> delComment(int id){
        return mServiceAPI.delComment(id).compose(new DefaultTransform<>());
    }

    public Observable<Object> unCollect(int id) {
        return mServiceAPI.unCollect(id).compose(new DefaultTransform<>());
    }
}
