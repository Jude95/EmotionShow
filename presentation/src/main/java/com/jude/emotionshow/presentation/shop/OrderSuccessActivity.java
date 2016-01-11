package com.jude.emotionshow.presentation.shop;

import android.content.Intent;
import android.os.Bundle;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.BeamBaseActivity;
import com.jude.emotionshow.R;

/**
 * Created by mike on 2015/12/25.
 */
@RequiresPresenter(OrderSuccessPresenter.class)
public class OrderSuccessActivity extends BeamBaseActivity<OrderSuccessPresenter> {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_success);

        Intent intent = new Intent(OrderSuccessActivity.this, OrderDetailActivity.class);
        intent.putExtra("id", getIntent().getIntExtra("id", -1));
        $(R.id.tg_order_detail).setOnClickListener(v -> {
            startActivity(intent);
            finish();
        });
        $(R.id.back).setOnClickListener(v -> finish());
        $(R.id.tg_back).setOnClickListener(v -> finish());
    }
}
