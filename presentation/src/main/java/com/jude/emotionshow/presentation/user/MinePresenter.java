package com.jude.emotionshow.presentation.user;

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
                        if (account!=null)
                        JUtils.Log("Account"+account.getName());
                        else
                        JUtils.Log("get null");
                        publishObject(account);
                    }
                });
    }
}
