package com.jude.emotionshow.presentation.user;

import android.os.Bundle;

import com.jude.beam.bijection.Presenter;
import com.jude.emotionshow.data.model.UserModel;
import com.jude.emotionshow.data.server.ServiceResponse;
import com.jude.emotionshow.domain.entities.Account;
import com.jude.smssdk_mob.SMSManager;
import com.jude.utils.JUtils;


/**
 * Created by Mr.Jude on 2015/11/26.
 */
public class PhoneEditPresenter extends Presenter<PhoneEditActivity> {

    @Override
    protected void onCreate(PhoneEditActivity view, Bundle savedState) {
        super.onCreate(view, savedState);
    }

    @Override
    protected void onCreateView(PhoneEditActivity view) {
        super.onCreateView(view);
        SMSManager.getInstance().registerTimeListener(getView());
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
                        SMSManager.getInstance().sendMessage(getView(), "86",number);
                    }
                });
    }

    public void edit(String number,String code){
        getView().getExpansion().showProgressDialog("注册中");
        UserModel.getInstance().telBind(number, code)
                .finallyDo(() -> getView().getExpansion().dismissProgressDialog())
                .subscribe(new ServiceResponse<Object>() {
                    @Override
                    public void onNext(Object o) {
                        getView().finish();
                        UserModel.getInstance().getCurAccount().setNeedTel(false);
                        UserModel.getInstance().updateMyInfo().subscribe(new ServiceResponse<Account>());
                        JUtils.Toast("绑定切换成功");
                    }
                });
    }
}
