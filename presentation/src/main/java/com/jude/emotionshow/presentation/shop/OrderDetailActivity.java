package com.jude.emotionshow.presentation.shop;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.data.BeamDataActivity;
import com.jude.emotionshow.R;
import com.jude.emotionshow.domain.entities.OrderDetail;
import com.jude.utils.JUtils;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by mike on 2015/12/25.
 */
@RequiresPresenter(OrderDetailPresenter.class)
public class OrderDetailActivity extends BeamDataActivity<OrderDetailPresenter, OrderDetail> {
    @Bind(R.id.iv_goods)
    ImageView img;
    @Bind(R.id.tv_goods_name)
    TextView goodsName;
    @Bind(R.id.tv_goods_des)
    TextView des;
    @Bind(R.id.tv_money)
    TextView money;
    @Bind(R.id.tv_num)
    TextView num;
    @Bind(R.id.tv_time)
    TextView time;
    @Bind(R.id.tv_name)
    TextView name;
    @Bind(R.id.tv_phone)
    TextView phone;
    @Bind(R.id.tv_address)
    TextView address;
    @Bind(R.id.tv_post_code)
    TextView postCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        ButterKnife.bind(this);
        $(R.id.back).setOnClickListener(v -> finish());
    }

    @OnClick(R.id.tv_copy)
    void copy() {
        if (!TextUtils.isEmpty(postCode.getText().toString())) {
            JUtils.Toast("已复制");
            JUtils.copyToClipboard(postCode.getText().toString().trim());
        }
    }

    String postCompany;
    String postId;

    @OnClick(R.id.tv_query)
    void query() {
//        http://m.kuaidi100.com/index_all.html?type=%E5%85%A8%E5%B3%B0&postid=123456
        if (!TextUtils.isEmpty(postCompany)) {
            StringBuilder sb = new StringBuilder();
            sb.append("http://m.kuaidi100.com/index_all.html?type=").append(postCompany).append("&postid=").append(postId);
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri content_url = Uri.parse(sb.toString());
            intent.setData(content_url);
            startActivity(intent);
        }
    }

    @Override
    public void setData(OrderDetail data) {
        postCompany = data.getPost_company();
        postId = data.getPost_code();
        if (TextUtils.isEmpty(data.getPic())){
            Picasso.with(OrderDetailActivity.this).load(R.mipmap.ic_launcher).into(img);
        }else {
            Picasso.with(OrderDetailActivity.this).load(data.getPic()).into(img);
        }
        goodsName.setText(data.getGoodsName());
        des.setText(data.getInfo());
        money.setText(data.getPrice());
        num.setText(data.getNum() + "");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        JUtils.Log("time:"+data.getTime()*1000+" "+new Date().getTime());
        time.setText(sdf.format(new Date(data.getTime() * 1000)));
        name.setText(data.getName());
        phone.setText(data.getPhone());
        address.setText(data.getAddress());
        postCode.setText(data.getPost_code());
    }
}
