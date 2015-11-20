package com.jude.emotionshow.presentation.setting;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.BeamBaseActivity;
import com.jude.emotionshow.R;
import com.jude.utils.JUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Mr.Jude on 2015/11/20.
 */
@RequiresPresenter(AboutPresenter.class)
public class AboutActivity extends BeamBaseActivity<AboutPresenter> {

    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.version)
    TextView version;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        back.setOnClickListener(v -> finish());
        version.setText(JUtils.getAppVersionName());
    }
}
