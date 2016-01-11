package com.jude.emotionshow.presentation.shop;

import android.os.Bundle;
import android.view.ViewGroup;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.list.BeamListActivity;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.emotionshow.R;
import com.jude.emotionshow.domain.entities.Order;

/**
 * Created by mike on 2015/12/28.
 */
@RequiresPresenter(OrderListPresenter.class)
public class OrderListActivity extends BeamListActivity<OrderListPresenter,Order>{
    @Override
    protected BaseViewHolder getViewHolder(ViewGroup parent, int viewType) {
        return new OrderViewHolder(parent);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_order_list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        $(R.id.back).setOnClickListener(v -> finish());
    }
}
