package com.jude.emotionshow.presentation.user;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.BeamBaseActivity;
import com.jude.emotionshow.R;
import com.jude.swipbackhelper.SwipeBackHelper;
import com.jude.tagview.TAGView;
import com.jude.utils.JUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Mr.Jude on 2015/11/19.
 */
@RequiresPresenter(LoginPresenter.class)
public class LoginActivity extends BeamBaseActivity<LoginPresenter> {

    @Bind(R.id.account)
    EditText account;
    @Bind(R.id.password)
    EditText password;
    @Bind(R.id.login)
    TAGView login;
    @Bind(R.id.find_password)
    TextView findPassword;
    @Bind(R.id.register)
    TextView register;
    @Bind(R.id.qq)
    FrameLayout qq;
    @Bind(R.id.weibo)
    FrameLayout weibo;
    @Bind(R.id.wx)
    FrameLayout wx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        SwipeBackHelper.getCurrentPage(this).setSwipeBackEnable(false);
        login.setOnClickListener(v -> {
            checkSubmit();
        });

        register.setOnClickListener(v -> {
            startActivityForResult(new Intent(this, RegisterActivity.class), 10086);
        });

        findPassword.setOnClickListener(v -> {
            startActivityForResult(new Intent(this, FindPasswordActivity.class), 10086);
        });
        qq.setOnClickListener(v->getPresenter().loginQQ());
        weibo.setOnClickListener(v->getPresenter().loginSina());
        wx.setOnClickListener(v->getPresenter().loginWX());
    }

    private void checkSubmit(){
        String accountText = account.getText().toString();
        String passwordText = password.getText().toString();
        if (TextUtils.isEmpty(accountText)){
            JUtils.Toast("账号不能为空");
            return;
        }
        if (TextUtils.isEmpty(passwordText)){
            JUtils.Toast("密码不能为空");
            return;
        }
        getPresenter().login(accountText,passwordText);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10086&&resultCode == RESULT_OK){
            account.setText(data.getStringExtra("account"));
            password.setText(data.getStringExtra("password"));
        }
    }
}
