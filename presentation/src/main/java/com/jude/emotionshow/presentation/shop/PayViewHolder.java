package com.jude.emotionshow.presentation.shop;

import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.emotionshow.R;
import com.jude.emotionshow.domain.entities.Pay;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mike on 2015/12/29.
 */
public class PayViewHolder extends BaseViewHolder<Pay> {
    @Bind(R.id.tv_detail)
    TextView detail;
    @Bind(R.id.tv_money)
    TextView price;
    @Bind(R.id.tv_time)
    TextView time;
    @Bind(R.id.tv_balance)
    TextView balance;

    public PayViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_pay);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void setData(Pay data) {
        detail.setText(data.getInfo());
        price.setText(data.getCoins());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        time.setText(sdf.format(new Date(data.getTime() * 1000)));
    }
}
