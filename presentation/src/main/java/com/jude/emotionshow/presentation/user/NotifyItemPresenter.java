package com.jude.emotionshow.presentation.user;

import android.os.Bundle;

import com.jude.beam.expansion.list.BeamListActivityPresenter;
import com.jude.emotionshow.data.model.CommonModel;
import com.jude.emotionshow.domain.entities.Notify;

/**
 * Created by zhuchenxi on 15/12/8.
 */
public class NotifyItemPresenter extends BeamListActivityPresenter<NotifyItemActivity,Notify> {
    public String type;

    @Override
    protected void onCreate(NotifyItemActivity view, Bundle savedState) {
        super.onCreate(view, savedState);
        type = getView().getIntent().getStringExtra("type");
        onRefresh();
    }



    @Override
    public void onLoadMore() {
        CommonModel.getInstance().getNotifyList(getCurPage(),type).unsafeSubscribe(getMoreSubscriber());
    }

    @Override
    public void onRefresh() {
        CommonModel.getInstance().getNotifyList(0,type).unsafeSubscribe(getRefreshSubscriber());
    }
}
