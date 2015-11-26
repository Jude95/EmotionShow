package com.jude.emotionshow.presentation.setting;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.jude.beam.expansion.data.BeamDataActivityPresenter;
import com.jude.emotionshow.data.model.UserModel;
import com.jude.emotionshow.data.server.ServiceResponse;
import com.jude.emotionshow.domain.entities.ThirdInfo;
import com.jude.utils.JUtils;
import com.umeng.share.ShareManager;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.listener.SocializeListeners;
import com.umeng.socialize.exception.SocializeException;

import java.util.Map;

/**
 * Created by Mr.Jude on 2015/11/20.
 */
public class AccountSettingPresenter extends BeamDataActivityPresenter<AccountSettingActivity, ThirdInfo> {

    @Override
    protected void onCreate(AccountSettingActivity view, Bundle savedState) {
        super.onCreate(view, savedState);
        UserModel.getInstance().getThirdInfo().subscribe(this);
    }

    public void bindQQ() {
        ShareManager.getInstance(getView()).login(getView(), SHARE_MEDIA.QQ, new SocializeListeners.UMAuthListener() {
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
                            JUtils.Log("已获取到信息:" + info.keySet().size());
                            if (status == 200 && info != null) {
                                getView().getExpansion().showProgressDialog("登陆中");

                                UserModel.getInstance().thirdBind(3, info.get("uid").toString(), info.get("screen_name").toString())
                                        .finallyDo(() -> getView().getExpansion().dismissProgressDialog())
                                        .subscribe(new ServiceResponse<ThirdInfo>() {
                                            @Override
                                            public void onNext(ThirdInfo account) {
                                                publishObject(account);
                                            }
                                        });
                            } else {
                                JUtils.Toast("授权发生错误：" + status);
                            }
                        }

                    });
                } else {
                    Toast.makeText(getView(), "授权失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(SocializeException e, SHARE_MEDIA share_media) {
                JUtils.Log(e.getErrorCode() + ":" + e.getMessage());
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media) {


            }
        });
    }

    public void bindWX() {
        ShareManager.getInstance(getView()).login(getView(), SHARE_MEDIA.WEIXIN, new SocializeListeners.UMAuthListener() {
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
                            JUtils.Log("已获取到信息:" + info.keySet().size());
                            if (status == 200 && info != null) {
                                getView().getExpansion().showProgressDialog("登陆中");

                                UserModel.getInstance().thirdBind(2, info.get("uid").toString(), info.get("screen_name").toString())
                                        .finallyDo(() -> getView().getExpansion().dismissProgressDialog())
                                        .subscribe(new ServiceResponse<ThirdInfo>() {
                                            @Override
                                            public void onNext(ThirdInfo account) {
                                                publishObject(account);
                                            }
                                        });
                            } else {
                                JUtils.Toast("授权发生错误：" + status);
                            }
                        }

                    });
                } else {
                    Toast.makeText(getView(), "授权失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(SocializeException e, SHARE_MEDIA share_media) {
                JUtils.Log(e.getErrorCode() + ":" + e.getMessage());
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media) {


            }

        });
    }

    public void bindSina() {
        ShareManager.getInstance(getView()).login(getView(), SHARE_MEDIA.SINA, new SocializeListeners.UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {
                JUtils.Log("onStart");
            }

            @Override
            public void onComplete(Bundle value, SHARE_MEDIA share_media) {
                JUtils.Log("onComplete");
                if (value != null && !TextUtils.isEmpty(value.getString("uid"))) {
                    ShareManager.getInstance(getView()).getData(getView(), SHARE_MEDIA.SINA, new SocializeListeners.UMDataListener() {
                        @Override
                        public void onStart() {
                            JUtils.Log("获取信息开始");
                        }

                        @Override
                        public void onComplete(int status, Map<String, Object> info) {
                            JUtils.Log("已获取到信息:" + info.keySet().size());
                            if (status == 200 && info != null) {
                                getView().getExpansion().showProgressDialog("登陆中");

                                UserModel.getInstance().thirdBind(1, info.get("uid").toString(), info.get("screen_name").toString())
                                        .finallyDo(() -> getView().getExpansion().dismissProgressDialog())
                                        .subscribe(new ServiceResponse<ThirdInfo>() {
                                            @Override
                                            public void onNext(ThirdInfo account) {
                                                publishObject(account);
                                            }
                                        });
                            } else {
                                JUtils.Toast("授权发生错误：" + status);
                            }
                        }

                    });
                } else {
                    Toast.makeText(getView(), "授权失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(SocializeException e, SHARE_MEDIA share_media) {
                JUtils.Log(e.getErrorCode() + ":" + e.getMessage());
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media) {

            }
        });
    }
}
