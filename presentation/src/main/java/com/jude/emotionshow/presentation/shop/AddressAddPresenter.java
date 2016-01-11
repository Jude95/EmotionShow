package com.jude.emotionshow.presentation.shop;

import com.jude.beam.bijection.Presenter;
import com.jude.emotionshow.data.model.RegionModel;
import com.jude.emotionshow.data.model.ShopModel;
import com.jude.emotionshow.domain.entities.Region;
import com.jude.utils.JUtils;

/**
 * Created by mike on 2015/12/26.
 */
public class AddressAddPresenter extends Presenter<AddressAddActivity> {

    public void submit(String name, String phone, String address, String addressDetail, String postCode) {
        getView().getExpansion().showProgressDialog("提交中");
        ShopModel.getInstance().addAddress(name, phone, address, addressDetail, postCode)
                .finallyDo(() -> getView().getExpansion().dismissProgressDialog())
                .subscribe(o -> {
                    JUtils.Toast("添加成功");
                    getView().setResult(120);
                    getView().finish();
                });
    }

    public void finishAddCity(Region region) {
        String province = RegionModel.getInstance().findProvince(region.getCid()).getName();
        String city=RegionModel.getInstance().findCity(region.getCid()).getName();
        String district =RegionModel.getInstance().findRegion(region.getCid()).getName();
        String address = province;
        if (!province.equals(city)){
            address+=city;
        }
        address+=district;
        getView().setAddress(address);
    }
}
