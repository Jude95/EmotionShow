package com.jude.emotionshow.presentation.user;

import android.os.Bundle;

import com.jude.beam.bijection.Presenter;
import com.jude.emotionshow.data.model.UserModel;
import com.jude.emotionshow.data.server.ServiceResponse;
import com.jude.utils.JUtils;

import cn.smssdk.gui.SMSManager;

/**
 * Created by Mr.Jude on 2015/11/26.
 */
public class PhoneEditPresenter extends Presenter<PhoneEditActivity> {
    SMSManager smsManager;
    String code;

    @Override
    protected void onCreate(PhoneEditActivity view, Bundle savedState) {
        super.onCreate(view, savedState);
        smsManager = new SMSManager();
    }

    @Override
    protected void onCreateView(PhoneEditActivity view) {
        super.onCreateView(view);
        smsManager.registerTimeListener(getView());
    }

    public void checkTelAndSend(String number){
        if (!getView().requestPermission()){
            return;
        }
        getView().getExpansion().showProgressDialog("提交中");
        UserModel.getInstance().checkTel(number)
                .finallyDo(() -> getView().getExpansion().dismissProgressDialog())
                .subscribe(new ServiceResponse<Object>() {
                    @Override
                    public void onNext(Object o) {
                        smsManager.sendMessage(getView(), number);
                    }
                });
    }

    public void edit(String number){
        getView().getExpansion().showProgressDialog("注册中");
        UserModel.getInstance().telBind(number, code)
                .finallyDo(() -> getView().getExpansion().dismissProgressDialog())
                .subscribe(new ServiceResponse<Object>() {
                    @Override
                    public void onNext(Object o) {
                        getView().finish();
                        JUtils.Toast("绑定切换成功");
                    }
                });
    }
}
