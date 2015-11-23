package com.jude.emotionshow.presentation.user;

import android.os.Bundle;

import com.jude.beam.expansion.data.BeamDataActivityPresenter;
import com.jude.emotionshow.data.model.UserModel;
import com.jude.emotionshow.domain.entities.PersonDetail;

/**
 * Created by Mr.Jude on 2015/11/22.
 */
public class UserPreviewPresenter extends BeamDataActivityPresenter<UserPreviewActivity,PersonDetail> {
    public int id;
    @Override
    protected void onCreate(UserPreviewActivity view, Bundle savedState) {
        super.onCreate(view, savedState);
        id = getView().getIntent().getIntExtra("id",0);
        UserModel.getInstance().getUserDetail(id).subscribe(this);
    }
}
