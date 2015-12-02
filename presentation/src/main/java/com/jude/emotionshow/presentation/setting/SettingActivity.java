package com.jude.emotionshow.presentation.setting;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.BeamBaseActivity;
import com.jude.emotionshow.R;
import com.jude.emotionshow.data.model.UserModel;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Mr.Jude on 2015/11/20.
 */
@RequiresPresenter(SettingPresenter.class)
public class SettingActivity extends BeamBaseActivity<SettingPresenter> {

    @Bind(R.id.back_img)
    ImageView backImg;
    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.account)
    LinearLayout account;
    @Bind(R.id.feedback)
    LinearLayout feedback;
    @Bind(R.id.logout)
    LinearLayout logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        back.setOnClickListener(v -> finish());
        logout.setOnClickListener(v->{
            UserModel.getInstance().logout();
            finish();
        });
        feedback.setOnClickListener(v->{
            startActivity(new Intent(this, FeedbackActivity.class));
        });
        account.setOnClickListener(v->{
            startActivity(new Intent(this,AccountSettingActivity.class));
        });
    }
}
