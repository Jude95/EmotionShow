package com.jude.emotionshow.presentation.main;

import android.content.Intent;
import android.os.Bundle;

import com.jude.beam.bijection.Presenter;
import com.jude.emotionshow.data.model.UserModel;
import com.jude.emotionshow.presentation.user.LoginActivity;

/**
 * Created by Mr.Jude on 2015/11/18.
 */
public class LaunchPresenter extends Presenter<LaunchActivity> {

    @Override
    protected void onCreate(LaunchActivity view, Bundle savedState) {
        super.onCreate(view, savedState);
        if (UserModel.getInstance().isLogin())
            getView().startActivity(new Intent(getView(), MainActivity.class));
        else
            getView().startActivity(new Intent(getView(), LoginActivity.class));
        getView().finish();
    }
}
