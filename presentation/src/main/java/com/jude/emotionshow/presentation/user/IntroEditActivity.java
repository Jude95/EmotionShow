package com.jude.emotionshow.presentation.user;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.data.BeamDataActivity;
import com.jude.emotionshow.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Mr.Jude on 2015/11/20.
 */
@RequiresPresenter(IntroEditPresenter.class)
public class IntroEditActivity extends BeamDataActivity<IntroEditPresenter,String> {
    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.done)
    LinearLayout done;
    @Bind(R.id.content)
    EditText content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_edit);
        ButterKnife.bind(this);
        back.setOnClickListener(v -> finish());
        done.setOnClickListener(v->{
            Intent i = new Intent();
            i.putExtra("content",content.getText().toString());
            setResult(RESULT_OK,i);
            finish();
        });
    }

    @Override
    public void setData(String data) {
        super.setData(data);
        content.setText(data);
    }
}
