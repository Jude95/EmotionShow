package com.jude.emotionshow.presentation.setting;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.data.BeamDataActivity;
import com.jude.emotionshow.R;
import com.jude.emotionshow.domain.entities.PushSet;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zhuchenxi on 15/12/8.
 */
@RequiresPresenter(PushSettingPresenter.class)
public class PushSettingActivity extends BeamDataActivity<PushSettingPresenter, PushSet> {

    @Bind(R.id.back_img)
    ImageView backImg;
    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.switch_comment)
    Switch switchComment;
    @Bind(R.id.comment)
    LinearLayout comment;
    @Bind(R.id.switch_praise)
    Switch switchPraise;
    @Bind(R.id.praise)
    LinearLayout praise;
    @Bind(R.id.switch_invite)
    Switch switchInvite;
    @Bind(R.id.invite)
    LinearLayout invite;
    @Bind(R.id.switch_follow)
    Switch switchFollow;
    @Bind(R.id.follow)
    LinearLayout follow;

    @Override
    public void setData(PushSet data) {
        super.setData(data);
        switchComment.setChecked(data.isReceiveCommentMessage());
        switchPraise.setChecked(data.isReceivePraiseMessage());
        switchInvite.setChecked(data.isReceiveInviteMessage());
        switchFollow.setChecked(data.isReceiveInviteMessage());
    }


    PushSet getPushSet(){
        return new PushSet(switchComment.isChecked(),switchFollow.isChecked(),switchInvite.isChecked(),switchPraise.isChecked());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_push);
        ButterKnife.bind(this);
        back.setOnClickListener(v -> finish());
        switchComment.setOnCheckedChangeListener(getPresenter());
        switchPraise.setOnCheckedChangeListener(getPresenter());
        switchInvite.setOnCheckedChangeListener(getPresenter());
        switchFollow.setOnCheckedChangeListener(getPresenter());
    }
}
