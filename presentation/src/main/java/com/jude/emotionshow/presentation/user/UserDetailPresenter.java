package com.jude.emotionshow.presentation.user;

import android.os.Bundle;

import com.afollestad.materialdialogs.MaterialDialog;
import com.jude.beam.expansion.data.BeamDataActivityPresenter;
import com.jude.emotionshow.data.model.RongYunModel;
import com.jude.emotionshow.data.model.SeedModel;
import com.jude.emotionshow.data.model.UserModel;
import com.jude.emotionshow.data.server.ServiceResponse;
import com.jude.emotionshow.domain.entities.PersonDetail;
import com.jude.emotionshow.domain.entities.SeedDetail;
import com.jude.utils.JUtils;

import java.util.List;

/**
 * Created by Mr.Jude on 2015/11/22.
 */
public class UserDetailPresenter extends BeamDataActivityPresenter<UserDetailActivity,PersonDetail> {
    private int id;
    public PersonDetail data;

    @Override
    protected void onCreate(UserDetailActivity view, Bundle savedState) {
        super.onCreate(view, savedState);
        JUtils.Log("onCreate");
        id = getView().getIntent().getIntExtra("id",0);
        refreshUser();
    }

    @Override
    protected void onCreateView(UserDetailActivity view) {
        super.onCreateView(view);
        refreshSeed();
    }

    public void refreshUser() {
        UserModel.getInstance().getUserDetail(id)
                .doOnNext(personDetail -> data = personDetail)
                .unsafeSubscribe(new ServiceResponse<PersonDetail>() {
                    @Override
                    public void onNext(PersonDetail personDetail) {
                        JUtils.Log("Get onNext");
                        publishObject(personDetail);
                    }
                });

    }

    public void refreshSeed(){
        SeedModel.getInstance().getUserSeedList(id)
                .unsafeSubscribe(new ServiceResponse<List<SeedDetail>>() {
                    @Override
                    public void onNext(List<SeedDetail> o) {
                        getView().addSeed(o);
                    }
                });
    }

    public void chat(){
        if (data.getFollowed() == 0||data.getFollowed() == 2){
            new MaterialDialog.Builder(getView())
                    .title("私信提醒")
                    .content("需先关注后才能发私信")
                    .positiveText("关注")
                    .negativeText("取消")
                    .onPositive((dialog, which) -> follow())
                    .show();
            return;
        }
        if (data.getFollowed() == 1){
            new MaterialDialog.Builder(getView())
                    .title("私信提醒")
                    .content("需先邀请对方关注你后才能发私信")
                    .positiveText("邀请")
                    .negativeText("取消")
                    .onPositive((dialog, which) -> UserModel.getInstance().invite(data.getId()).subscribe(new ServiceResponse<Object>(){
                        @Override
                        public void onNext(Object o) {
                            JUtils.Toast("邀请已发送");
                        }
                    }))
                    .show();
            return;
        }
        RongYunModel.getInstance().chatPerson(getView(),data.getId()+"",data.getName());
    }

    public void follow(){
        getView().getExpansion().showProgressDialog("关注中");
        if (data.getFollowed()==0||data.getFollowed()==2)
            UserModel.getInstance().follow(id)
                    .finallyDo(()->getView().getExpansion().dismissProgressDialog())
                    .subscribe(new ServiceResponse<Object>(){
                        @Override
                        public void onNext(Object o) {
                            JUtils.Toast("您已关注");
                            refreshUser();
                        }
                    });
        else
            UserModel.getInstance().unFollow(id)
                    .finallyDo(() -> getView().getExpansion().dismissProgressDialog())
                    .subscribe(new ServiceResponse<Object>() {
                        @Override
                        public void onNext(Object o) {
                            JUtils.Toast("您已取消关注");
                            refreshUser();
                }
            });
    }
}
