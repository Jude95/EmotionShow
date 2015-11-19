package com.jude.emotionshow.presentation.user;

import android.os.Bundle;

import com.jude.beam.expansion.data.BeamDataFragmentPresenter;
import com.jude.emotionshow.data.model.UserModel;
import com.jude.emotionshow.domain.entities.Account;

/**
 * Created by Mr.Jude on 2015/11/18.
 */
public class MinePresenter extends BeamDataFragmentPresenter<MineFragment,Account> {

    @Override
    protected void onCreate(MineFragment view, Bundle savedState) {
        super.onCreate(view, savedState);
        UserModel.getInstance().getAccountUpdate().subscribe(this::publishObject);
    }
}
