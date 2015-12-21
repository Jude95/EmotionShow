package com.jude.emotionshow.presentation.main;

import android.content.Intent;
import android.os.Bundle;

import com.jude.beam.bijection.Presenter;
import com.jude.emotionshow.presentation.user.LoginActivity;
import com.jude.utils.JUtils;

/**
 * Created by Mr.Jude on 2015/11/18.
 */
public class LaunchPresenter extends Presenter<LaunchActivity> {

    @Override
    protected void onCreate(LaunchActivity view, Bundle savedState) {
        super.onCreate(view, savedState);
        //是否第一次启动，做相应跳转
        if (JUtils.getSharedPreference().getBoolean("FIRST_LAUNCH",true)){
            getView().startActivity(new Intent(getView(), GuideActivity.class));
            JUtils.getSharedPreference().edit().putBoolean("FIRST_LAUNCH",false).apply();
            getView().finish();
            return;
        }
        //选择需求是先不管登录，所以改成这样。
        if (true)
            getView().startActivity(new Intent(getView(), MainActivity.class));
        else
            getView().startActivity(new Intent(getView(), LoginActivity.class));
        getView().finish();
    }
}
