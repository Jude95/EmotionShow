package com.jude.emotionshow.presentation.setting;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.BeamBaseActivity;
import com.jude.emotionshow.R;
import com.jude.emotionshow.presentation.user.PasswordEditActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Mr.Jude on 2015/11/20.
 */
@RequiresPresenter(AccountSettingPresenter.class)
public class AccountSettingActivity extends BeamBaseActivity<AccountSettingPresenter> {

    @Bind(R.id.back_img)
    ImageView backImg;
    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.password)
    LinearLayout password;
    @Bind(R.id.phone)
    LinearLayout phone;
    @Bind(R.id.wx)
    LinearLayout wx;
    @Bind(R.id.qq)
    LinearLayout qq;
    @Bind(R.id.weibo)
    LinearLayout weibo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_setting);
        ButterKnife.bind(this);
        back.setOnClickListener(v -> finish());
        password.setOnClickListener(v->startActivity(new Intent(this, PasswordEditActivity.class)));
    }
}
