package com.jude.emotionshow.presentation.user;

import android.os.Bundle;

import com.jude.beam.expansion.data.BeamDataActivityPresenter;

/**
 * Created by Mr.Jude on 2015/11/20.
 */
public class IntroEditPresenter extends BeamDataActivityPresenter<IntroEditActivity,String> {
    @Override
    protected void onCreate(IntroEditActivity view, Bundle savedState) {
        super.onCreate(view, savedState);
        publishObject(getView().getIntent().getStringExtra("content"));
    }
}
