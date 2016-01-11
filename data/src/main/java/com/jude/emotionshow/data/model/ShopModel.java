package com.jude.emotionshow.data.model;

import android.content.Context;

import com.jude.beam.model.AbsModel;
import com.jude.emotionshow.data.di.DaggerShopComponent;
import com.jude.emotionshow.data.server.DefaultTransform;
import com.jude.emotionshow.domain.api.ServiceAPI;
import com.jude.emotionshow.domain.entities.Address;
import com.jude.emotionshow.domain.entities.Goods;
import com.jude.emotionshow.domain.entities.GoodsDetail;
import com.jude.emotionshow.domain.entities.Order;
import com.jude.emotionshow.domain.entities.OrderDetail;
import com.jude.emotionshow.domain.entities.Pay;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by mike on 2015/12/29.
 */
public class ShopModel extends AbsModel {
    @Inject
    ServiceAPI mServiceAPI;

    public static ShopModel getInstance() {
        return getInstance(ShopModel.class);
    }

    @Override
    protected void onAppCreate(Context ctx) {
        super.onAppCreate(ctx);
        DaggerShopComponent.builder().build().inject(this);
    }

    public Observable<Object> addAddress(String name, String phone, String address, String addressDetail, String postCode) {
        return mServiceAPI.addAddress(name, phone, address, addressDetail, postCode).compose(new DefaultTransform<>());
    }

    public Observable<List<Address>> getAddress() {
        return mServiceAPI.getAddressList().compose(new DefaultTransform<>());
    }

    public Observable<Object> delAddress(int id) {
        return mServiceAPI.delAddress(id).compose(new DefaultTransform<>());
    }

    public Observable<List<Goods>> getGoodsList(int type, int page, int sort) {
        return mServiceAPI.getGoodsList(type, page, sort).compose(new DefaultTransform<>());
    }

    public Observable<GoodsDetail> getGoodsDetail(int id) {
        return mServiceAPI.getGoodsDetail(id).compose(new DefaultTransform<>());
    }

    public Observable<Object> confirmOrder(int gid, int num, String info, String address, String name, String phone, String postCode) {
        return mServiceAPI.order(gid, num, info, address, name, phone, postCode).compose(new DefaultTransform<>());
    }

    public Observable<List<Order>> getOrderList() {
        return mServiceAPI.getOrderList().compose(new DefaultTransform<>());
    }

    public Observable<OrderDetail> getOrderItem(int id) {
        return mServiceAPI.getOrderItem(id).compose(new DefaultTransform<>());
    }

    public Observable<List<Pay>> getPayList(int cost,int page){
        return mServiceAPI.getPayList(cost, page).compose(new DefaultTransform<>());
    }
}
