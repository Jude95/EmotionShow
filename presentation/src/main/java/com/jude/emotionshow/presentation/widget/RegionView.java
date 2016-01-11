package com.jude.emotionshow.presentation.widget;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.emotionshow.R;
import com.jude.emotionshow.data.model.RegionModel;
import com.jude.emotionshow.domain.entities.Region;
import com.jude.utils.JUtils;

import java.util.ArrayList;

/**
 * 没什么用的省市区三级联动选择。
 */
public class RegionView extends LinearLayout {
        private RecyclerView province;
        private RegionAdapter provinceAdapter;
        private RegionHeaderView provinceHeader;
        private RecyclerView city;
        private RegionAdapter cityAdapter;
        private RegionHeaderView cityHeader;
        private RecyclerView region;
        private RegionAdapter regionAdapter;
        private RegionHeaderView regionHeader;
        private RegionSelectCallback callback;
    
    public interface RegionSelectCallback {
        void selected(Region region);
    }
        public RegionView(Context context, RegionSelectCallback callback, int initRegion) {
            super(context);
            this.callback = callback;
            initView();
            initData(initRegion);

        }

        private void initView(){
            setOrientation(HORIZONTAL);
            setBackgroundColor(Color.WHITE);
            province = new RecyclerView(getContext());
            province.setLayoutParams(new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1));
            province.setLayoutManager(new LinearLayoutManager(getContext()));
            province.setAdapter((provinceAdapter = new RegionAdapter(getContext(), new RegionSelectCallback() {
                @Override
                public void selected(Region region) {
                    cityAdapter.clear();
                    cityAdapter.addAll(RegionModel.getInstance().getCityList(region.getCid()));
                    cityHeader.setRegion(region);
                    regionAdapter.clear();
                    regionAdapter.addAll(RegionModel.getInstance().getRegionList(cityAdapter.getItem(0).getCid()));
                }
            })));
            addView(province);


            View divider1 = new View(getContext());
            divider1.setLayoutParams(new LayoutParams(JUtils.dip2px(0.3f), ViewGroup.LayoutParams.MATCH_PARENT));
            divider1.setBackgroundColor(Color.GRAY);
            addView(divider1);


            city = new RecyclerView(getContext());
            city.setLayoutParams(new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1));
            city.setLayoutManager(new LinearLayoutManager(getContext()));
            city.setAdapter((cityAdapter = new RegionAdapter(getContext(), new RegionSelectCallback() {
                @Override
                public void selected(Region region) {
                    regionAdapter.clear();
                    regionAdapter.addAll(RegionModel.getInstance().getRegionList(region.getCid()));
                    regionHeader.setRegion(region);

                }
            })));
            addView(city);

            View divider2 = new View(getContext());
            divider2.setLayoutParams(new LayoutParams(JUtils.dip2px(0.3f), ViewGroup.LayoutParams.MATCH_PARENT));
            divider2.setBackgroundColor(Color.GRAY);
            addView(divider2);

            region = new RecyclerView(getContext());
            region.setLayoutParams(new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1));
            region.setLayoutManager(new LinearLayoutManager(getContext()));
            region.setAdapter((regionAdapter = new RegionAdapter(getContext(), new RegionSelectCallback() {
                @Override
                public void selected(Region region) {
                    callback.selected(region);
                }
            })));
            addView(region);
        }

        public void initData(int regionCode){
            //一定能选中省
            provinceAdapter.addHeader((provinceHeader = new RegionHeaderView(callback, new Region(0, 1, 100000, 100000, "不限"), "不限")));
            ArrayList<Region> arrProvince = RegionModel.getInstance().getProvinceList();
            provinceAdapter.addAll(arrProvince);
            Region provinceRegion = RegionModel.getInstance().findProvince(regionCode);
            if(provinceRegion == null){
                provinceRegion = arrProvince.get(0);
            }
            province.scrollToPosition(arrProvince.indexOf(provinceRegion)+1);


            //一定要选中市
            cityAdapter.addHeader((cityHeader = new RegionHeaderView(callback, provinceRegion, "全部")));
            ArrayList<Region> arrCity =RegionModel.getInstance().getCityList(provinceRegion.getCid());
            cityAdapter.addAll(arrCity);
            Region cityRegion = RegionModel.getInstance().findCity(regionCode);
            if (cityRegion == null){
                cityRegion = arrCity.get(0);
            }
            city.scrollToPosition(arrCity.indexOf(cityRegion)+1);


            //一定要选中区
            regionAdapter.addHeader((regionHeader = new RegionHeaderView(callback, cityRegion, "全部")));
            ArrayList<Region> arrRegion = RegionModel.getInstance().getRegionList(cityRegion.getCid());
            regionAdapter.addAll(arrRegion);
            Region regionRegion =  RegionModel.getInstance().findRegion(regionCode);
            if (regionRegion == null){
                regionRegion = arrRegion.get(0);
            }
            region.scrollToPosition(arrRegion.indexOf(regionRegion)+1);
        }


        private class RegionAdapter extends RecyclerArrayAdapter<Region> {
            private RegionSelectCallback callback;
            public RegionAdapter(Context context,RegionSelectCallback callback) {
                super(context);
                this.callback = callback;
            }

            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new RegionVH(parent);
            }

            @Override
            public void OnBindViewHolder(BaseViewHolder holder, int position) {
                holder.setData(getItem(position));
            }

            private class RegionVH extends BaseViewHolder<Region> {
                private TextView tv;
                private Region rg;
                public RegionVH(View view) {
                    super(new MaterialRippleLayout(view.getContext()));
                    tv = new TextView(getContext());
                    tv.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, JUtils.dip2px(48)));
                    tv.setGravity(Gravity.CENTER);
                    ((MaterialRippleLayout)itemView).addView(tv);
                    ((MaterialRippleLayout)itemView).setRippleColor(getContext().getResources().getColor(R.color.gray));
                    itemView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, JUtils.dip2px(48)));
                }

                @Override
                public void setData(Region data) {
                    rg = data;
                    tv.setText(data.getName());
                    itemView.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            JUtils.Log("onClick");
                            callback.selected(rg);
                        }
                    });
                }
            }

        }

        private class RegionHeaderView implements RecyclerArrayAdapter.ItemView{
            private RegionSelectCallback callback;
            private Region region;
            private String title;

            public void setRegion(Region region) {
                this.region = region;
            }


            RegionHeaderView(RegionSelectCallback callback,Region region,String title){
                this.callback = callback;
                this.region = region;
                this.title = title;
            }

            @Override
            public View onCreateView(ViewGroup parent) {
                MaterialRippleLayout layout = new MaterialRippleLayout(getContext());
                TextView tv = new TextView(parent.getContext());
                tv.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, JUtils.dip2px(48)));
                tv.setGravity(Gravity.CENTER);
                tv.setText(title);
                layout.addView(tv);
                layout.setRippleColor(getContext().getResources().getColor(R.color.gray));
                layout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, JUtils.dip2px(48)));

                return layout;
            }

            @Override
            public void onBindView(View headerView) {
                headerView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        JUtils.Log("onClick");
                        callback.selected(region);
                    }
                });
            }
        }


    }