package com.jude.emotionshow.presentation.shop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.BeamBaseActivity;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.emotionshow.R;
import com.jude.emotionshow.domain.entities.Address;
import com.jude.emotionshow.domain.entities.Order;
import com.jude.tagview.TAGView;
import com.jude.utils.JUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mike on 2015/12/24.
 */
@RequiresPresenter(OrderConfirmPresenter.class)
public class OrderConfirmActivity extends BeamBaseActivity<OrderConfirmPresenter> implements AddressAdapter.OnSelectedListener {
    @Bind(R.id.tg_ok)
    TAGView ok;
    @Bind(R.id.iv_goods)
    ImageView img;
    @Bind(R.id.tv_goods_name)
    TextView name;
    @Bind(R.id.tv_goods_des)
    TextView des;
    @Bind(R.id.tv_money)
    TextView money;
    @Bind(R.id.tv_num)
    TextView num;
    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.recycler)
    EasyRecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirm);
        ButterKnife.bind(this);

        setData();
        setupView();
    }

    public void setData() {
        String addr = JUtils.getSharedPreference().getString("default_address", "");
        if (!TextUtils.isEmpty(addr)) {
            selected = new Address();
            String[] addrs = addr.split(",");
            selected.setId(Integer.valueOf(addrs[0]));
            selected.setName(addrs[1]);
            selected.setPhone(addrs[2]);
            selected.setCity(addrs[3]);
            selected.setAddress(addrs[4]);
            selected.setAddcode(addrs[5]);
        }
    }

    private List<Address> addressList;
    private AddressAdapter addressAdapter;

    private void setupView() {
        addressList = new ArrayList<>();
        ok.setOnClickListener(v -> checkData());
        back.setOnClickListener(v -> finish());
        recyclerView.setLayoutManager(new LinearLayoutManager(OrderConfirmActivity.this));
        recyclerView.setAdapter(addressAdapter = new AddressAdapter(OrderConfirmActivity.this, addressList));
        addressAdapter.setOnSelectedListener(this);
        recyclerView.setEmptyView(R.layout.item_address_empty);
        recyclerView.getEmptyView().findViewById(R.id.ll_address).setOnClickListener(v -> {
            startActivityForResult(new Intent(OrderConfirmActivity.this, AddressAddActivity.class), 120);
        });
    }

    public void setAddressList(List<Address> addresses) {
        int addrId = JUtils.getSharedPreference().getInt("default_address_id", -1);
        if (addrId != -1) {

        }
        addressList.clear();
        addressList.addAll(addresses);
        addressAdapter.notifyDataSetChanged();
    }

    private void checkData() {
        if (selected == null) {
            JUtils.Toast("请选择收获地址");
            return;
        }
        getPresenter().confirmOrder(selected);
    }

    public void setView(Order order) {
        if (TextUtils.isEmpty(order.getPic())) {
            Picasso.with(OrderConfirmActivity.this).load(R.mipmap.ic_launcher).into(img);
        } else {
            Picasso.with(OrderConfirmActivity.this).load(order.getPic()).into(img);
        }
        name.setText(order.getGoodsName());
        des.setText(order.getDes());
        money.setText(Integer.valueOf(order.getPrice()) * order.getNum() + "");
        num.setText(order.getNum() + "");
    }

    private Address selected = null;

    public Address getSelected() {
        return selected;
    }

    @Override
    public void onSelectedChanged(int position) {
        selected = addressList.get(position);
        int addressId = selected.getId();
        JUtils.getSharedPreference().edit().putInt("default_address_id", addressId).apply();
        StringBuilder sb = new StringBuilder();
        sb
                .append(addressId)
                .append(",")
                .append(selected.getName())
                .append(",")
                .append(selected.getPhone())
                .append(",")
                .append(selected.getCity())
                .append(",")
                .append(selected.getAddress())
                .append(",")
                .append(selected.getAddcode());
        JUtils.getSharedPreference().edit().putString("default_address", sb.toString()).apply();
    }
}