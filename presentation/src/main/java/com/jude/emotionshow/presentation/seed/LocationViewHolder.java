package com.jude.emotionshow.presentation.seed;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amap.api.services.core.PoiItem;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.emotionshow.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ske on 2016/2/25.
 */
public class LocationViewHolder extends BaseViewHolder<PoiItem> {

    @Bind(R.id.tv_address)
    TextView tvAddress;
    LocationActivity activity;

    public LocationViewHolder(ViewGroup parent, LocationActivity activity) {
        super(parent, R.layout.item_location);
        ButterKnife.bind(this, itemView);
        this.activity = activity;
    }

    @Override
    public void setData(PoiItem data) {
        tvAddress.setText(data.getSnippet() + data.getTitle());
        tvAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data.getSnippet().equals("默认："))
                    activity.setResult(Activity.RESULT_OK, new Intent().putExtra("location", data.getTitle()));
                else
                    activity.setResult(Activity.RESULT_OK, new Intent().putExtra("location", tvAddress.getText().toString()));
                activity.finish();
            }
        });
    }
}
