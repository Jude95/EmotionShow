package com.jude.emotionshow.presentation.seed;

import android.os.Bundle;

import com.jude.beam.bijection.Presenter;
import com.jude.emotionshow.data.model.CommonModel;
import com.jude.emotionshow.data.model.SeedModel;
import com.jude.emotionshow.data.server.ServiceResponse;
import com.jude.emotionshow.domain.entities.Banner;
import com.jude.emotionshow.domain.entities.CategoryPreview;
import com.jude.emotionshow.domain.entities.Topic;
import java.util.List;

import rx.subjects.BehaviorSubject;

/**
 * Created by Mr.Jude on 2015/11/18.
 */
public class SeedMainPresenter extends Presenter<SeedMainFragment> {

    private BehaviorSubject<List<Banner>> mBannerSubject = BehaviorSubject.create();
    private BehaviorSubject<List<Topic>> mTopicSubject = BehaviorSubject.create();
    private BehaviorSubject<List<CategoryPreview>> mCategoryProcessSubject = BehaviorSubject.create();
    private BehaviorSubject<List<CategoryPreview>> mCategorySceneSubject = BehaviorSubject.create();
    private BehaviorSubject<CategoryPreview> mActivitiesSubject = BehaviorSubject.create();


    @Override
    protected void onCreate(SeedMainFragment view, Bundle savedState) {
        super.onCreate(view, savedState);
        CommonModel.getInstance().getBanner().subscribe(new ServiceResponse<List<Banner>>() {
            @Override
            public void onNext(List<Banner> banners) {
                mBannerSubject.onNext(banners);
            }
        });
        SeedModel.getInstance().getTopic().subscribe(new ServiceResponse<List<Topic>>() {
            @Override
            public void onNext(List<Topic> topics) {
                mTopicSubject.onNext(topics);
                getView().setTopic(topics);
            }
        });
        getActivities();
        getCategoryProcess();
        getCategoryScene();
    }

    @Override
    protected void onCreateView(SeedMainFragment view) {
        super.onCreateView(view);
        mBannerSubject.subscribe(data -> getView().setBanner(data));
        mTopicSubject.subscribe(data -> getView().setTopic(data));
        mCategoryProcessSubject.subscribe(data -> getView().setCategoryProcess(data));
        mCategorySceneSubject.subscribe(data -> getView().setCategoryScene(data));
        mActivitiesSubject.subscribe(data->getView().setActivities(data));
    }

    public void getCategoryProcess(){
        SeedModel.getInstance().getProcess().subscribe(new ServiceResponse<List<CategoryPreview>>() {
            @Override
            public void onNext(List<CategoryPreview> categories) {
                mCategoryProcessSubject.onNext(categories);
            }
        });
    }
    public void getCategoryScene(){
        SeedModel.getInstance().getScene().subscribe(new ServiceResponse<List<CategoryPreview>>() {
            @Override
            public void onNext(List<CategoryPreview> categories) {
                mCategorySceneSubject.onNext(categories);
            }
        });
    }

    public void getActivities(){
        SeedModel.getInstance().getActivityList().subscribe(new ServiceResponse<CategoryPreview>(){
            @Override
            public void onNext(CategoryPreview seeds) {
                mActivitiesSubject.onNext(seeds);
            }
        });
    }
}
