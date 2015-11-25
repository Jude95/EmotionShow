package com.jude.emotionshow.presentation.user;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.jude.beam.bijection.Presenter;
import com.jude.emotionshow.data.model.UserModel;
import com.jude.emotionshow.data.server.ServiceResponse;
import com.jude.emotionshow.domain.entities.Account;
import com.jude.emotionshow.presentation.main.MainActivity;
import com.jude.utils.JUtils;
import com.umeng.share.ShareManager;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.listener.SocializeListeners;
import com.umeng.socialize.exception.SocializeException;

import java.util.Map;

/**
 * Created by Mr.Jude on 2015/11/19.
 */
public class LoginPresenter extends Presenter<LoginActivity> {


    public void login(String account,String password){
        getView().getExpansion().showProgressDialog("登录中");
        UserModel.getInstance().login(account,password)
                .finallyDo(() -> getView().getExpansion().dismissProgressDialog())
                .subscribe(new ServiceResponse<Account>() {
                    @Override
                    public void onNext(Account account) {
                        getView().finish();
                        getView().startActivity(new Intent(getView(), MainActivity.class));
                    }
                });
    }

    public void loginQQ(){
        ShareManager.getInstance(getView()).login(getView(), SHARE_MEDIA.QQ, new SocializeListeners.UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {

            }

            @Override
            public void onComplete(Bundle value, SHARE_MEDIA share_media) {
                if (value != null && !TextUtils.isEmpty(value.getString("uid"))) {
                    Toast.makeText(getView(), "授权成功.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getView(), "授权失败",  Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(SocializeException e, SHARE_MEDIA share_media) {

            }

            @Override
            public void onCancel(SHARE_MEDIA share_media) {

            }
        });
    }
    public void loginWX(){
        ShareManager.getInstance(getView()).login(getView(), SHARE_MEDIA.WEIXIN, new SocializeListeners.UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {

            }

            @Override
            public void onComplete(Bundle value, SHARE_MEDIA share_media) {
                if (value != null && !TextUtils.isEmpty(value.getString("uid"))) {
                    Toast.makeText(getView(), "授权成功.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getView(), "授权失败",  Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(SocializeException e, SHARE_MEDIA share_media) {

            }

            @Override
            public void onCancel(SHARE_MEDIA share_media) {

            }
        });
    }
    public void loginSina(){
        ShareManager.getInstance(getView()).login(getView(), SHARE_MEDIA.SINA, new SocializeListeners.UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {
            }

            @Override
            public void onComplete(Bundle value, SHARE_MEDIA share_media) {
                if (value != null && !TextUtils.isEmpty(value.getString("uid"))) {
                    ShareManager.getInstance(getView()).getData(getView(), SHARE_MEDIA.SINA, new SocializeListeners.UMDataListener() {
                        @Override
                        public void onStart() {
                            JUtils.Log("获取信息开始");
                        }

                        @Override
                        public void onComplete(int status, Map<String, Object> info) {
                            JUtils.Log("已获取到信息:"+info.keySet().size());
                            if(status == 200 && info != null){
                                getView().getExpansion().showProgressDialog("登陆中");
                                UserModel.getInstance().loginByThird(
                                        info.get("uid").toString(),
                                        info.get("profile_image_url").toString(),
                                        info.get("screen_name").toString(),
                                        1)
                                        .finallyDo(() -> getView().getExpansion().dismissProgressDialog())
                                        .subscribe(new ServiceResponse<Account>() {
                                            @Override
                                            public void onNext(Account account) {
                                                getView().finish();
                                                getView().startActivity(new Intent(getView(), MainActivity.class));
                                            }
                                        });
                            }else{
                                JUtils.Toast( "授权发生错误：" + status);
                            }
                        }

                    });
                } else {
                    Toast.makeText(getView(), "授权失败",  Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(SocializeException e, SHARE_MEDIA share_media) {
                JUtils.Log(e.getErrorCode()+":"+e.getMessage());
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media) {

            }
        });
    }

    @Override
    protected void onResult(int requestCode, int resultCode, Intent data) {
        super.onResult(requestCode, resultCode, data);
        ShareManager.getInstance(getView()).onActivityResult(requestCode, resultCode, data);
    }
}
