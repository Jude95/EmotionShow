package com.jude.emotionshow.presentation.shop;

import android.content.Intent;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.emotionshow.R;
import com.jude.emotionshow.domain.entities.Order;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mike on 2015/12/30.
 */
public class OrderViewHolder extends BaseViewHolder<Order> {
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
    public OrderViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_order);
        ButterKnife.bind(this,itemView);
    }

    @Override
    public void setData(Order data) {
        if (TextUtils.isEmpty(data.getPic())){
            Picasso.with(getContext()).load(R.mipmap.ic_launcher).into(img);
        }else {
            Picasso.with(getContext()).load(data.getPic()).into(img);
        }
        name.setText(data.getGoodsName());
        des.setText(data.getInfo());
        money.setText(data.getPrice());
        num.setText(data.getNum()+"");
        itemView.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(),OrderDetailActivity.class);
            intent.putExtra("id",data.getId());
            getContext().startActivity(intent);
        });
    }
}
