package com.jude.emotionshow.presentation.main;

import android.content.Intent;
import android.os.Bundle;

import com.jude.beam.bijection.Presenter;
import com.jude.emotionshow.data.model.UserModel;
import com.jude.emotionshow.data.server.ServiceResponse;
import com.jude.emotionshow.domain.entities.Account;
import com.jude.emotionshow.presentation.seed.WritingActivity;
import com.jude.emotionshow.presentation.user.LoginActivity;

/**
 * Created by Mr.Jude on 2015/11/18.
 */
public class MainPresenter extends Presenter<MainActivity> {

    @Override
    protected void onCreate(MainActivity view, Bundle savedState) {
        super.onCreate(view, savedState);
        UserModel.getInstance().updateMyInfo().subscribe(new ServiceResponse<Account>());
    }

    public void createSeed(){
        if (UserModel.getInstance().isLogin())
            getView().startActivity(new Intent(getView(), WritingActivity.class));
        else
            getView().startActivity(new Intent(getView(), LoginActivity.class));

    }
}
