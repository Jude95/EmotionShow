package com.jude.emotionshow.presentation.user;

import android.content.Intent;
import android.os.Bundle;

import com.jude.beam.expansion.data.BeamDataFragmentPresenter;
import com.jude.emotionshow.data.model.UserModel;
import com.jude.emotionshow.data.server.DefaultTransform;
import com.jude.emotionshow.domain.entities.Account;
import com.jude.utils.JUtils;

import rx.Subscriber;

/**
 * Created by Mr.Jude on 2015/11/18.
 */
public class MinePresenter extends BeamDataFragmentPresenter<MineFragment,Account> {

    @Override
    protected void onCreate(MineFragment view, Bundle savedState) {
        super.onCreate(view, savedState);
        UserModel.getInstance().getAccountUpdate()
                .compose(new DefaultTransform<>())
                .unsafeSubscribe(new Subscriber<Account>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Account account) {
                        publishObject(account);
                    }
                });
    }

    //必须要注册了手机号才能正常使用
    @Override
    protected void onResume() {
        super.onResume();
        JUtils.Log("onResume");
        if (UserModel.getInstance().isLogin()&&UserModel.getInstance().getCurAccount().isNeedTel()){
            Intent i = new Intent(getView().getActivity(),PhoneEditActivity.class);
            getView().startActivity(i);
            JUtils.Toast("请先绑定手机号");
        }
    }
}
