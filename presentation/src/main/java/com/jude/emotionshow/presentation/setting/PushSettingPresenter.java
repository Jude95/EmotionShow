package com.jude.emotionshow.presentation.setting;

import android.os.Bundle;
import android.widget.CompoundButton;

import com.jude.beam.expansion.data.BeamDataActivityPresenter;
import com.jude.emotionshow.data.model.CommonModel;
import com.jude.emotionshow.data.server.ServiceResponse;
import com.jude.emotionshow.domain.entities.PushSet;
import com.jude.utils.JUtils;

/**
 * Created by zhuchenxi on 15/12/8.
 */
public class PushSettingPresenter extends BeamDataActivityPresenter<PushSettingActivity,PushSet> implements CompoundButton.OnCheckedChangeListener{
    @Override
    protected void onCreate(PushSettingActivity view, Bundle savedState) {
        super.onCreate(view, savedState);
        CommonModel.getInstance().getPushSet().subscribe(this);
    }

    public void upload(){
        //getView().getExpansion().showProgressDialog("修改中");
        PushSet pushSet = getView().getPushSet();
        JUtils.Log(pushSet.toString());
        CommonModel.getInstance().uploadPushSet(pushSet)
                .finallyDo(()->getView().getExpansion().dismissProgressDialog())
                .subscribe(new ServiceResponse<Object>());
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        upload();
    }
}
