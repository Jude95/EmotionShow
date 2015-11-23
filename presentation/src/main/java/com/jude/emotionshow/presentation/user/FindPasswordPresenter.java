package com.jude.emotionshow.presentation.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.jude.beam.bijection.Presenter;
import com.jude.emotionshow.data.model.UserModel;
import com.jude.emotionshow.data.server.ServiceResponse;
import com.jude.utils.JUtils;

import cn.smssdk.gui.SMSManager;

/**
 * Created by Mr.Jude on 2015/11/23.
 */
public class FindPasswordPresenter extends Presenter<FindPasswordActivity> {
    SMSManager smsManager;


    @Override
    protected void onCreate(FindPasswordActivity view, Bundle savedState) {
        super.onCreate(view, savedState);
        smsManager = new SMSManager();
    }

    @Override
    protected void onCreateView(FindPasswordActivity view) {
        super.onCreateView(view);
        smsManager.registerTimeListener(getView());
    }

    public void checkTelAndSend(String number){
        if (!getView().requestPermission()){
            return;
        }
        smsManager.sendMessage(getView(), number);
    }

    public void register(String number,String password,String code){
        getView().getExpansion().showProgressDialog("注册中");
        UserModel.getInstance().findPassword(number, code, password)
                .finallyDo(() -> getView().getExpansion().dismissProgressDialog())
                .subscribe(new ServiceResponse<Object>() {
                    @Override
                    public void onNext(Object o) {
                        getView().finish();
                        Intent i = new Intent();
                        i.putExtra("account", number);
                        i.putExtra("password", password);
                        getView().setResult(Activity.RESULT_OK, i);
                        JUtils.Toast("注册成功");
                    }
                });
    }
}
