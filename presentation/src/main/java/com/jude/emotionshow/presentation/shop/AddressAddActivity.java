package com.jude.emotionshow.presentation.shop;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.BeamBaseActivity;
import com.jude.emotionshow.R;
import com.jude.emotionshow.data.model.LocationModel;
import com.jude.emotionshow.presentation.widget.RegionView;
import com.jude.tagview.TAGView;
import com.jude.utils.JUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mike on 2015/12/26.
 */
@RequiresPresenter(AddressAddPresenter.class)
public class AddressAddActivity extends BeamBaseActivity<AddressAddPresenter> {
    @Bind(R.id.et_name)
    EditText name;
    @Bind(R.id.et_phone)
    EditText phone;
    @Bind(R.id.tv_address)
    TextView address;
    @Bind(R.id.et_detail_address)
    EditText detailAddress;
    @Bind(R.id.et_code)
    EditText postCode;
//    @Bind(R.id.cb)
//    CheckBox agree;
    @Bind(R.id.tg_ok)
    TAGView ok;
    @Bind(R.id.back)
    LinearLayout back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        ButterKnife.bind(this);
        ok.setOnClickListener(v -> checkInput());
        address.setOnClickListener(v -> showCityDialog(LocationModel.getInstance().getCurLocation().regionCode));
        back.setOnClickListener(v -> finish());
    }

    private void checkInput() {
        if (TextUtils.isEmpty(name.getText().toString().trim())) {
            JUtils.Toast("请填写收货人姓名");
            return;
        }
        if (TextUtils.isEmpty(phone.getText().toString().trim())) {
            JUtils.Toast("请填写联系电话");
            return;
        }
        if (TextUtils.equals(getString(R.string.select_address), address.getText().toString().trim())) {
            JUtils.Toast("请选择收获地址");
            return;
        }
        if (TextUtils.isEmpty(detailAddress.getText().toString().trim())) {
            JUtils.Toast("请填写详细地址");
            return;
        }
        if (TextUtils.isEmpty(postCode.getText().toString().trim())) {
            JUtils.Toast("请填写邮编");
            return;
        }
        getPresenter().submit(name.getText().toString().trim(),
                phone.getText().toString().trim(),
                address.getText().toString().trim(),
                detailAddress.getText().toString().trim(),
                postCode.getText().toString().trim());
    }

    private MaterialDialog dialog;

    public void showCityDialog(int laseRegionCode) {
        RegionView view = new RegionView(this, region -> {
            getPresenter().finishAddCity(region);
            dialog.dismiss();
        }, laseRegionCode);
        dialog = new MaterialDialog.Builder(this)
                .title("选择您所在的地区")
                .customView(view, false)
                .show();
    }

    public void setAddress(String addressStr) {
        address.setText(addressStr);
    }
}
