package com.jude.emotionshow.presentation.main;

import android.os.Bundle;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.BeamBaseActivity;

/**
 * Created by Mr.Jude on 2015/11/18.
 * 启动界面，背景由theme确定，在presenter中进行Activity路由
 */
@RequiresPresenter(LaunchPresenter.class)
public class LaunchActivity extends BeamBaseActivity<LaunchPresenter>{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
    }
}
