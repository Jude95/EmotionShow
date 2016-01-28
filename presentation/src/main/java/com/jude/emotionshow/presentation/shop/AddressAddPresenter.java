package com.jude.emotionshow.presentation.shop;

import android.content.Intent;
import android.os.Bundle;

import com.jude.beam.bijection.Presenter;
import com.jude.emotionshow.data.model.RegionModel;
import com.jude.emotionshow.data.model.ShopModel;
import com.jude.emotionshow.data.server.ServiceResponse;
import com.jude.emotionshow.domain.entities.Address;
import com.jude.emotionshow.domain.entities.Region;
import com.jude.utils.JUtils;

/**
 * Created by mike on 2015/12/26.
 */
public class AddressAddPresenter extends Presenter<AddressAddActivity> {
    @Override
    protected void onCreate(AddressAddActivity view, Bundle savedState) {
        super.onCreate(view, savedState);
    }

    boolean isModify = false;
    int addressId;

    @Override
    protected void onCreateView(AddressAddActivity view) {
        super.onCreateView(view);
        Intent data = getView().getIntent();
        Address address = (Address) data.getSerializableExtra("address");
        if (address != null) {
            isModify = true;
            addressId = address.getId();
            getView().setUI(address);
        }
    }

    public void submit(String name, String phone, String address, String addressDetail, String postCode) {
        getView().getExpansion().showProgressDialog("提交中");
        if (isModify) {
            ShopModel.getInstance().modAddress(addressId, name, phone, address, addressDetail, postCode)
                    .finallyDo(() -> getView().getExpansion().dismissProgressDialog())
                    .subscribe(new ServiceResponse<Object>() {
                        @Override
                        public void onError(Throwable e) {
                            JUtils.Toast("修改失败，请重试");
                        }

                        @Override
                        public void onNext(Object o) {
                            JUtils.Toast("修改成功");
                            if (JUtils.getSharedPreference().getInt("default_address_id", -1) == addressId) {
                                StringBuilder sb = new StringBuilder();
                                sb
                                        .append(addressId)
                                        .append(",")
                                        .append(name)
                                        .append(",")
                                        .append(phone)
                                        .append(",")
                                        .append(address)
                                        .append(",")
                                        .append(addressDetail)
                                        .append(",")
                                        .append(postCode);
                                JUtils.getSharedPreference().edit().putString("default_address", sb.toString()).apply();
                            }
                            getView().setResult(getView().RESULT_OK);
                            getView().finish();
                        }
                    });
        } else
            ShopModel.getInstance().addAddress(name, phone, address, addressDetail, postCode)
                    .finallyDo(() -> getView().getExpansion().dismissProgressDialog())
                    .subscribe(new ServiceResponse<Object>() {
                        @Override
                        public void onError(Throwable e) {
                            JUtils.Toast("添加失败，请重试");
                        }

                        @Override
                        public void onNext(Object o) {
                            JUtils.Toast("添加成功");
                            getView().setResult(getView().RESULT_OK);
                            getView().finish();
                        }
                    });
    }

    public void finishAddCity(Region region) {
        String province = RegionModel.getInstance().findProvince(region.getCid()).getName();
        String city = RegionModel.getInstance().findCity(region.getCid()).getName();
        String district = RegionModel.getInstance().findRegion(region.getCid()).getName();
        String address = province;
        if (!province.equals(city)) {
            address += city;
        }
        address += district;
        getView().setAddress(address);
    }

    public void deleteAddress() {
        ShopModel.getInstance().delAddress(addressId)
                .subscribe(new ServiceResponse<Object>() {
                    @Override
                    public void onNext(Object o) {
                        JUtils.Toast("删除成功");
                        if (JUtils.getSharedPreference().getInt("default_address_id", -1) == addressId) {
                            JUtils.getSharedPreference().edit().remove("default_address_id").apply();
                            JUtils.getSharedPreference().edit().remove("default_address").apply();
                        }
                        getView().setResult(getView().RESULT_OK);
                        getView().finish();
                    }
                });
    }
}
