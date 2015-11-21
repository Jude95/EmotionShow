package com.jude.emotionshow.presentation.main;

import android.content.Intent;

import com.jude.beam.bijection.Presenter;
import com.jude.emotionshow.presentation.seed.WritingActivity;

/**
 * Created by Mr.Jude on 2015/11/18.
 */
public class MainPresenter extends Presenter<MainActivity> {

    public void createSeed(){
        getView().startActivity(new Intent(getView(), WritingActivity.class));
    }
}
