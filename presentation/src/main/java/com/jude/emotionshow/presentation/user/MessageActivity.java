package com.jude.emotionshow.presentation.user;

import android.os.Bundle;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.BeamBaseActivity;

/**
 * Created by zhuchenxi on 15/12/4.
 */
@RequiresPresenter(MessagePresenter.class)
public class MessageActivity extends BeamBaseActivity<MessagePresenter> {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
