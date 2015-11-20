package com.jude.emotionshow.presentation.setting;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.BeamBaseActivity;
import com.jude.emotionshow.R;
import com.jude.utils.JUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Mr.Jude on 2015/11/20.
 */
@RequiresPresenter(FeedBackPresenter.class)
public class FeedbackActivity extends BeamBaseActivity<FeedBackPresenter> {

    @Bind(R.id.back_img)
    ImageView backImg;
    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.done)
    LinearLayout done;
    @Bind(R.id.content)
    EditText content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_edit);
        ButterKnife.bind(this);
        back.setOnClickListener(v->finish());
        done.setOnClickListener(v -> {
            if (TextUtils.isEmpty(content.getText().toString())){
                JUtils.Toast("请填写内容");
                return;
            }
            getPresenter().feedBack(content.getText().toString());
        });
    }
}
