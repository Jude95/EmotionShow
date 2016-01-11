package com.jude.emotionshow.presentation.shop;

import android.content.Intent;
import android.os.Bundle;

import com.jude.beam.bijection.Presenter;
import com.jude.emotionshow.data.model.ShopModel;
import com.jude.emotionshow.data.server.ServiceResponse;
import com.jude.emotionshow.domain.entities.Address;
import com.jude.emotionshow.domain.entities.Order;
import com.jude.utils.JUtils;

import java.util.List;

/**
 * Created by mike on 2015/12/24.
 */
public class OrderConfirmPresenter extends Presenter<OrderConfirmActivity> {
    private Order order;

    @Override
    protected void onCreate(OrderConfirmActivity view, Bundle savedState) {
        super.onCreate(view, savedState);
        order = (Order) getView().getIntent().getSerializableExtra("order");

        ShopModel.getInstance().getAddress().subscribe(new ServiceResponse<List<Address>>(){
            @Override
            public void onNext(List<Address> addresses) {
                getView().setAddressList(addresses);
            }
        });
    }

    @Override
    protected void onCreateView(OrderConfirmActivity view) {
        super.onCreateView(view);
        getView().setView(order);
    }

    //确认订单，成功后返回订单
    public void confirmOrder(Address address) {
        ShopModel.getInstance().confirmOrder(order.getGid(), order.getNum(), order.getInfo(),
                address.getCity() + address.getAddress(), address.getName(),
                address.getPhone(), address.getAddcode())
                .subscribe(new ServiceResponse<Order>() {
                    @Override
                    public void onNext(Order o) {
                        JUtils.Toast("提交成功");
                        Intent intent = new Intent(getView(),OrderSuccessActivity.class);
                        intent.putExtra("id",o.getId());
                        getView().startActivity(intent);
                        getView().finish();
                    }
                });
    }
}
