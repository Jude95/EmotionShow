package com.jude.emotionshow.presentation.setting;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.data.BeamDataActivity;
import com.jude.emotionshow.R;
import com.jude.emotionshow.domain.entities.ThirdInfo;
import com.jude.emotionshow.presentation.user.PasswordEditActivity;
import com.jude.emotionshow.presentation.user.PhoneEditActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Mr.Jude on 2015/11/20.
 */
@RequiresPresenter(AccountSettingPresenter.class)
public class AccountSettingActivity extends BeamDataActivity<AccountSettingPresenter, ThirdInfo> {

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
    @Bind(R.id.wx_name)
    TextView wxName;
    @Bind(R.id.qq_name)
    TextView qqName;
    @Bind(R.id.sina_name)
    TextView sinaName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_setting);
        ButterKnife.bind(this);
        back.setOnClickListener(v -> finish());
        password.setOnClickListener(v -> startActivity(new Intent(this, PasswordEditActivity.class)));
        phone.setOnClickListener(v->startActivity(new Intent(this, PhoneEditActivity.class)));
        qq.setOnClickListener(v -> getPresenter().bindQQ());
        wx.setOnClickListener(v -> getPresenter().bindWX());
        weibo.setOnClickListener(v -> getPresenter().bindSina());
    }

    @Override
    public void setData(ThirdInfo data) {
        super.setData(data);
        wxName.setText(data.getWx());
        qqName.setText(data.getQq());
        sinaName.setText(data.getSina());
    }
}
