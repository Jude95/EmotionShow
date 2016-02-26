package com.jude.emotionshow.presentation.seed;

import android.os.Bundle;
import android.view.ViewGroup;

import com.amap.api.services.core.PoiItem;
import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.list.BeamListActivity;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.emotionshow.R;

/**
 * Created by mike on 2016/2/25.
 */
@RequiresPresenter(LocationPresenter.class)
public class LocationActivity extends BeamListActivity<LocationPresenter, PoiItem> {
    @Override
    protected BaseViewHolder getViewHolder(ViewGroup parent, int viewType) {
        return new LocationViewHolder(parent,this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_location_list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        $(R.id.back).setOnClickListener(v -> finish());
    }
}
