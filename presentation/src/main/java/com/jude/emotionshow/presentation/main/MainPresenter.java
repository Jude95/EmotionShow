package com.jude.emotionshow.presentation.main;

import android.content.Intent;

import com.jude.beam.bijection.Presenter;
import com.jude.emotionshow.data.model.UserModel;
import com.jude.emotionshow.presentation.seed.WritingActivity;
import com.jude.emotionshow.presentation.user.LoginActivity;

/**
 * Created by Mr.Jude on 2015/11/18.
 */
public class MainPresenter extends Presenter<MainActivity> {

    public void createSeed(){
        if (UserModel.getInstance().isLogin())
            getView().startActivity(new Intent(getView(), WritingActivity.class));
        else
            getView().startActivity(new Intent(getView(), LoginActivity.class));
    }
}
