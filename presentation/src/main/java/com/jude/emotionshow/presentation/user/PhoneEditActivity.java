package com.jude.emotionshow.presentation.user;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.BeamBaseActivity;
import com.jude.emotionshow.R;
import com.jude.emotionshow.presentation.seed.WritingPresenter;
import com.jude.tagview.TAGView;
import com.jude.utils.JUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.smssdk.gui.TimeListener;

/**
 * Created by Mr.Jude on 2015/11/26.
 */
@RequiresPresenter(PhoneEditPresenter.class)
public class PhoneEditActivity extends BeamBaseActivity<PhoneEditPresenter>   implements TimeListener {

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
    @Bind(R.id.edit)
    TAGView edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_edit);
        ButterKnife.bind(this);
        sendCode.setOnClickListener(v -> sendCode());
        edit.setOnClickListener(v-> edit());
        back.setOnClickListener(v-> finish());
    }


    public void sendCode() {
        if (account.getText().toString().length() != 11) {
            JUtils.Toast("请输入正确手机号");
            return;
        }
        getPresenter().checkTelAndSend(account.getText().toString());
    }

    public void edit(){
        if (TextUtils.isEmpty(code.getText().toString())){
            JUtils.Toast("请输入验证码");
            return;
        }
        getPresenter().edit(account.getText().toString());
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


    public boolean requestPermission(){
        //判断当前Activity是否已经获得了该权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED
                ||ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

            //如果App的权限申请曾经被用户拒绝过，就需要在这里跟用户做出解释
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(this, "please give me the permission", Toast.LENGTH_SHORT).show();
            } else {
                //进行权限请求
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_CONTACTS,Manifest.permission.READ_PHONE_STATE},
                        WritingPresenter.EXTERNAL_STORAGE_REQ_CODE);
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case WritingPresenter.EXTERNAL_STORAGE_REQ_CODE: {
                // 如果请求被拒绝，那么通常grantResults数组为空
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    //申请成功，进行相应操作
                    getPresenter().checkTelAndSend(account.getText().toString());
                } else {
                    //申请失败，可以继续向用户解释。
                }
                return;
            }
        }
    }
}
