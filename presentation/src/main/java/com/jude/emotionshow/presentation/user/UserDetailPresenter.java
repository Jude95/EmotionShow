package com.jude.emotionshow.presentation.user;

import android.os.Bundle;

import com.jude.beam.expansion.data.BeamDataActivityPresenter;
import com.jude.emotionshow.data.model.SeedModel;
import com.jude.emotionshow.data.model.UserModel;
import com.jude.emotionshow.data.server.ServiceResponse;
import com.jude.emotionshow.domain.entities.PersonDetail;
import com.jude.emotionshow.domain.entities.Seed;
import com.jude.utils.JUtils;

import java.util.List;

/**
 * Created by Mr.Jude on 2015/11/22.
 */
public class UserDetailPresenter extends BeamDataActivityPresenter<UserDetailActivity,PersonDetail> {
    private int id;
    private PersonDetail data;
    @Override
    protected void onCreate(UserDetailActivity view, Bundle savedState) {
        super.onCreate(view, savedState);
        id = getView().getIntent().getIntExtra("id",0);
        refresh();
    }

    public void refresh(){
        SeedModel.getInstance().getUserSeedList(id)
                .unsafeSubscribe(new ServiceResponse<List<Seed>>(){
                    @Override
                    public void onNext(List<Seed> seeds) {
                        getView().setSeedList(seeds);
                    }
                });
        UserModel.getInstance().getUserDetail(id)
                .doOnNext(personDetail -> data = personDetail)
                .unsafeSubscribe(new ServiceResponse<PersonDetail>(){
                    @Override
                    public void onNext(PersonDetail personDetail) {
                        publishObject(personDetail);
                    }
                });
    }

    public void follow(){
        getView().getExpansion().showProgressDialog("关注中");
        if (data.getFollowed()==0)
            UserModel.getInstance().follow(id)
                    .finallyDo(()->getView().getExpansion().dismissProgressDialog())
                    .subscribe(new ServiceResponse<Object>(){
                        @Override
                        public void onNext(Object o) {
                            JUtils.Toast("您已关注");
                            refresh();
                        }
                    });
        else
            UserModel.getInstance().unFollow(id)
                    .finallyDo(() -> getView().getExpansion().dismissProgressDialog())
                    .subscribe(new ServiceResponse<Object>() {
                        @Override
                        public void onNext(Object o) {
                            JUtils.Toast("您已取消关注");
                    refresh();
                }
            });
    }
}
