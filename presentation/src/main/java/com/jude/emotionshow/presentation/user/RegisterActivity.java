package com.jude.emotionshow.presentation.user;

import android.os.Bundle;
import android.text.TextUtils;
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
import cn.smssdk.gui.TimeListener;

/**
 * Created by Mr.Jude on 2015/11/20.
 */
@RequiresPresenter(RegisterPresenter.class)
public class RegisterActivity extends BeamBaseActivity<RegisterPresenter> implements TimeListener{

    @Bind(R.id.back_img)
    ImageView backImg;
    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.account)
    EditText account;
    @Bind(R.id.send_code)
    TAGView sendCode;
    @Bind(R.id.code)
    EditText code;
    @Bind(R.id.password)
    EditText password;
    @Bind(R.id.password_confirm)
    EditText passwordConfirm;
    @Bind(R.id.register)
    TAGView register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        sendCode.setOnClickListener(v -> sendCode());
        register.setOnClickListener(v->register());
    }

    public void sendCode() {
        if (account.getText().toString().length() != 11) {
            JUtils.Toast("请输入正确手机号");
            return;
        }
        getPresenter().checkTelAndSend(account.getText().toString());
    }

    public void register(){
        if (password.getText().toString().length() < 6 || password.getText().toString().length() > 12) {
            JUtils.Toast("请输入6-12位密码");
            return;
        }
        if (!passwordConfirm.getText().toString().equals(password.getText().toString())){
            JUtils.Toast("2次密码不一致");
            return;
        }
        if (TextUtils.isEmpty(code.getText().toString())){
            JUtils.Toast("请输入验证码");
            return;
        }
        getPresenter().register(account.getText().toString(),password.getText().toString(),code.getText().toString());
    }

    @Override
    public void onLastTimeNotify(int lastSecond) {
        JUtils.Log("onLastTimeNotify");

        if (lastSecond > 0)
            sendCode.setText(lastSecond + "秒后重新发送");
        else
            sendCode.setText("发送验证码");
        JUtils.Log("onLastTimeNotify end");

    }

    @Override
    public void onAbleNotify(boolean valuable) {
        JUtils.Log("onAbleNotify");
        sendCode.setEnabled(valuable);
        sendCode.setBackgroundColor(getResources().getColor(valuable ? R.color.brown : R.color.gray));
        JUtils.Log("onAbleNotify end");

    }
}
