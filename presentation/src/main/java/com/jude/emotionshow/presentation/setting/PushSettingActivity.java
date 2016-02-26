package com.jude.emotionshow.presentation.setting;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.data.BeamDataActivity;
import com.jude.emotionshow.R;
import com.jude.emotionshow.domain.entities.PushSet;
import com.jude.utils.JUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zhuchenxi on 15/12/8.
 */
@RequiresPresenter(PushSettingPresenter.class)
public class PushSettingActivity extends BeamDataActivity<PushSettingPresenter, PushSet> implements View.OnClickListener {

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
        switchFollow.setChecked(data.isReceiveFollowMessage());
    }


    PushSet getPushSet() {
        return new PushSet(switchComment.isChecked(), switchFollow.isChecked(), switchInvite.isChecked(), switchPraise.isChecked());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_push);
        ButterKnife.bind(this);
        back.setOnClickListener(v -> finish());
//        switchComment.setOnCheckedChangeListener(getPresenter());
//        switchPraise.setOnCheckedChangeListener(getPresenter());
//        switchInvite.setOnCheckedChangeListener(getPresenter());
//        switchFollow.setOnCheckedChangeListener(getPresenter());
        comment.setOnClickListener(this);
        praise.setOnClickListener(this);
        invite.setOnClickListener(this);
        follow.setOnClickListener(this);

        SharedPreferences preferences = JUtils.getSharedPreference();
        switchComment.setChecked(preferences.getBoolean("PUSH_Comment",false));
        switchPraise.setChecked(preferences.getBoolean("PUSH_Praise",false));
        switchInvite.setChecked(preferences.getBoolean("PUSH_Invite",false));
        switchFollow.setChecked(preferences.getBoolean("PUSH_Follow",false));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.comment:
                switchComment.setChecked(!switchComment.isChecked());
                JUtils.getSharedPreference().edit().putBoolean("PUSH_Comment",switchComment.isChecked()).apply();
                break;
            case R.id.praise:
                switchPraise.setChecked(!switchPraise.isChecked());
                JUtils.getSharedPreference().edit().putBoolean("PUSH_Praise",switchPraise.isChecked()).apply();
                break;
            case R.id.invite:
                switchInvite.setChecked(!switchInvite.isChecked());
                JUtils.getSharedPreference().edit().putBoolean("PUSH_Invite",switchInvite.isChecked()).apply();
                break;
            case R.id.follow:
                switchFollow.setChecked(!switchFollow.isChecked());
                JUtils.getSharedPreference().edit().putBoolean("PUSH_Follow",switchFollow.isChecked()).apply();
                break;
        }
        JUtils.Toast("kkkk");
        getPresenter().upload();
    }
}
