package com.jude.emotionshow.presentation.user;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.data.BeamDataActivity;
import com.jude.emotionshow.R;
import com.jude.emotionshow.domain.entities.Account;
import com.jude.emotionshow.presentation.widget.CircleTransform;
import com.jude.emotionshow.presentation.widget.RegionView;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Mr.Jude on 2015/11/20.
 */
@RequiresPresenter(UserDetailEditPresenter.class)
public class UserDetailEditActivity extends BeamDataActivity<UserDetailEditPresenter, Account> {

    @Bind(R.id.back_img)
    ImageView backImg;
    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.avatar)
    ImageView avatar;
    @Bind(R.id.viewFace)
    RelativeLayout viewFace;
    @Bind(R.id.name)
    EditText name;
    @Bind(R.id.viewName)
    RelativeLayout viewName;
    @Bind(R.id.male)
    RadioButton male;
    @Bind(R.id.female)
    RadioButton female;
    @Bind(R.id.genderSelect)
    RadioGroup genderSelect;
    @Bind(R.id.address)
    TextView address;
    @Bind(R.id.viewAddress)
    LinearLayout viewAddress;
    @Bind(R.id.intro)
    TextView intro;
    @Bind(R.id.viewIntro)
    LinearLayout viewIntro;
    @Bind(R.id.done)
    LinearLayout done;
    @Bind(R.id.flag)
    ImageView flag;
    @Bind(R.id.name_label)
    TextView nameLabel;
    @Bind(R.id.sign_label)
    TextView signLabel;
    @Bind(R.id.sign)
    EditText sign;
    @Bind(R.id.viewSign)
    RelativeLayout viewSign;
    @Bind(R.id.address_label)
    TextView addressLabel;
    @Bind(R.id.intro_label)
    TextView introLabel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail_modify);
        ButterKnife.bind(this);
        back.setOnClickListener(v -> finish());
        done.setOnClickListener(v -> getPresenter().submit());
        avatar.setOnClickListener(v -> showSelectorDialog());
        sign.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                getPresenter().data.setSign(s.toString());
            }
        });
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                getPresenter().data.setName(s.toString());
            }
        });
        genderSelect.setOnCheckedChangeListener((group, checkedId) -> getPresenter().data.setGender(checkedId == R.id.male ? 0 : 1));
        viewAddress.setOnClickListener(v -> showCityDialog(500000));
        viewIntro.setOnClickListener(v -> {
            Intent i = new Intent(this, IntroEditActivity.class);
            i.putExtra("content", getPresenter().data.getIntro());
            startActivityForResult(i, 10086);
        });
    }

    private MaterialDialog dialog;

    public void showCityDialog(int laseRegionCode) {
        RegionView view = new RegionView(this, region -> {
            getPresenter().finishAddCity(region);
            dialog.dismiss();
        }, laseRegionCode);
        dialog = new MaterialDialog.Builder(this)
                .title("选择感兴趣的地区")
                .customView(view, false)
                .show();
    }

    @Override
    public void setData(Account data) {
        super.setData(data);
        Picasso.with(this)
                .load(data.getAvatar())
                .transform(new CircleTransform())
                .into(avatar);
        name.setText(data.getName());
        if (data.getGender() == 0) male.setChecked(true);
        else female.setChecked(true);
        address.setText(data.getAddress());
        sign.setText(data.getSign());
        intro.setText(data.getIntro());
    }

    public void showSelectorDialog() {
        new MaterialDialog.Builder(this)
                .title("选择图片来源")
                .items(new String[]{"拍照", "相册"})
                .itemsCallback((materialDialog, view, i, charSequence) -> getPresenter().editFace(i)).show();
    }

    public void setAvatar(Uri uri) {
        Picasso.with(this)
                .load(uri)
                .transform(new CircleTransform())
                .into(avatar);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10086 && resultCode == RESULT_OK) {
            getPresenter().data.setIntro(data.getStringExtra("content"));
            intro.setText(data.getStringExtra("content"));
        }
    }
}
