package com.jude.emotionshow.presentation.shop;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.jude.beam.bijection.Presenter;
import com.jude.emotionshow.data.model.ShopModel;
import com.jude.emotionshow.data.server.ServiceResponse;
import com.jude.emotionshow.domain.entities.Address;

import java.util.List;

/**
 * Created by mike on 2015/12/24.
 */
public class AddressListPresenter extends Presenter<AddressListActivity> {
    @Override
    protected void onCreate(AddressListActivity view, Bundle savedState) {
        super.onCreate(view, savedState);
        getAddress();
    }

    public void getAddress() {
        ShopModel.getInstance().getAddress()
                .subscribe(new ServiceResponse<List<Address>>() {
                    @Override
                    public void onNext(List<Address> addresses) {
                        getView().setAddressList(addresses);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }

    @Override
    protected void onResult(int requestCode, int resultCode, Intent data) {
        super.onResult(requestCode, resultCode, data);
        if (requestCode == 120 && resultCode == Activity.RESULT_OK) {
            getAddress();
        }
    }
}
