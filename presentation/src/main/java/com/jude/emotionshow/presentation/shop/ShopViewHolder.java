package com.jude.emotionshow.presentation.shop;

import android.content.Intent;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.emotionshow.R;
import com.jude.emotionshow.domain.entities.Goods;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mike on 2015/12/28.
 */
public class ShopViewHolder extends BaseViewHolder<Goods> {
    @Bind(R.id.iv_goods)
    ImageView image;
    @Bind(R.id.tv_goods_name)
    TextView name;
    @Bind(R.id.tv_goods_price)
    TextView price;

    public ShopViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_shop_goods);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void setData(Goods data) {
        Picasso.with(getContext()).load(data.getPic()).into(image);
        name.setText(data.getName());
        price.setText(data.getPrice());
        itemView.setOnClickListener(v -> {
            Intent i = new Intent(getContext(), GoodsDetailActivity.class);
            i.putExtra("id", data.getId());
            getContext().startActivity(i);
        });
    }
}
