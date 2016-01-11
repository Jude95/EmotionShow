package com.jude.emotionshow.presentation.shop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.BeamBaseActivity;
import com.jude.emotionshow.R;
import com.jude.emotionshow.domain.entities.Address;
import com.jude.utils.JUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mike on 2015/12/24.
 */
@RequiresPresenter(AddressListPresenter.class)
public class AddressListActivity extends BeamBaseActivity<AddressListPresenter> implements AddressAdapter.OnSelectedListener {

    @Bind(R.id.recycler)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_list);
        ButterKnife.bind(this);

        setupView();
    }

    private List<Address> addressList;
    private AddressAdapter addressAdapter;

    private void setupView() {
        addressList = new ArrayList<>();
        $(R.id.tg_add).setOnClickListener(v -> startActivityForResult(new Intent(AddressListActivity.this, AddressAddActivity.class), 120));
        $(R.id.back).setOnClickListener(v -> finish());
        recyclerView.setLayoutManager(new LinearLayoutManager(AddressListActivity.this));
        recyclerView.setAdapter(addressAdapter = new AddressAdapter(AddressListActivity.this, addressList));
        addressAdapter.setOnSelectedListener(this);
    }

    public void setAddressList(List<Address> addresses) {
        addressList.clear();
        addressList.addAll(addresses);
        addressAdapter.notifyDataSetChanged();
    }

    int addressId;

    @Override
    public void onSelectedChanged(int id) {
        Address address = addressList.get(id);
        addressId = address.getId();
        JUtils.getSharedPreference().edit().putInt("default_address_id", addressId).apply();
        StringBuilder sb = new StringBuilder();
        sb
                .append(addressId)
                .append(",")
                .append(address.getName())
                .append(",")
                .append(address.getPhone())
                .append(",")
                .append(address.getCity())
                .append(",")
                .append(address.getAddress())
                .append(",")
                .append(address.getAddcode());
        JUtils.getSharedPreference().edit().putString("default_address", sb.toString()).apply();
    }
}
