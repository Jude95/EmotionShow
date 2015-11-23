package com.jude.emotionshow.presentation.user;

import android.os.Bundle;

import com.jude.beam.expansion.list.BeamListActivityPresenter;
import com.jude.emotionshow.data.model.UserModel;
import com.jude.emotionshow.domain.entities.Seed;

/**
 * Created by Mr.Jude on 2015/11/22.
 */
public class CollectionPresenter extends BeamListActivityPresenter<CollectionActivity,Seed> {

    @Override
    protected void onCreate(CollectionActivity view, Bundle savedState) {
        super.onCreate(view, savedState);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        UserModel.getInstance().getCollections().unsafeSubscribe(getRefreshSubscriber());
    }
}
