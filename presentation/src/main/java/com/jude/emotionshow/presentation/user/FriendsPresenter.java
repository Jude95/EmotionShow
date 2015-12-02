package com.jude.emotionshow.presentation.user;

import android.os.Bundle;

import com.jude.beam.expansion.list.BeamListActivityPresenter;
import com.jude.emotionshow.data.model.UserModel;
import com.jude.emotionshow.domain.entities.PersonBrief;

/**
 * Created by zhuchenxi on 15/12/2.
 */
public class FriendsPresenter extends BeamListActivityPresenter<FriendsActivity,PersonBrief> {

    @Override
    protected void onCreate(FriendsActivity view, Bundle savedState) {
        super.onCreate(view, savedState);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        UserModel.getInstance().getFriends().subscribe(getRefreshSubscriber());
    }
}
