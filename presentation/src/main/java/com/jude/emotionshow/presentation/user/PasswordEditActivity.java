package com.jude.emotionshow.presentation.user;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.BeamBaseActivity;
import com.jude.emotionshow.R;
import com.jude.tagview.TAGView;
import com.jude.utils.JUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Mr.Jude on 2015/11/20.
 */
@RequiresPresenter(PasswordEditPresenter.class)
public class PasswordEditActivity extends BeamBaseActivity<PasswordEditPresenter> {

    @Bind(R.id.back_img)
    ImageView backImg;
    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.password_original)
    EditText passwordOriginal;
    @Bind(R.id.password_new)
    EditText passwordNew;
    @Bind(R.id.password_confirm)
    EditText passwordConfirm;
    @Bind(R.id.edit)
    TAGView edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_edit);
        back.setOnClickListener(v->finish());
        ButterKnife.bind(this);
        edit.setOnClickListener(v->check());
    }

    private void check(){
        if (passwordOriginal.getText().toString().length() < 6 || passwordOriginal.getText().toString().length() > 12) {
            JUtils.Toast("请输入6-12位旧密码");
            return;
        }
        if (passwordNew.getText().toString().length() < 6 || passwordNew.getText().toString().length() > 12) {
            JUtils.Toast("请输入6-12位密码");
            return;
        }
        if (!passwordConfirm.getText().toString().equals(passwordNew.getText().toString())){
            JUtils.Toast("2次密码不一致");
            return;
        }
        getPresenter().edit(passwordOriginal.getText().toString(),passwordNew.getText().toString());
    }
}
