package com.jude.emotionshow.presentation.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.BeamBaseActivity;
import com.jude.emotionshow.R;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.DynamicPagerAdapter;
import com.jude.swipbackhelper.SwipeBackHelper;
import com.jude.tagview.TAGView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zhuchenxi on 15/12/9.
 */
@RequiresPresenter(GuidePresenter.class)
public class GuideActivity extends BeamBaseActivity<GuidePresenter> {

    @Bind(R.id.viewpager)
    RollPagerView viewpager;
    @Bind(R.id.go)
    TAGView go;


    private static int[] res = {R.drawable.guide0,R.drawable.guide1,R.drawable.guide2};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        SwipeBackHelper.getCurrentPage(this).setSwipeBackEnable(false);
        ButterKnife.bind(this);
        go.setOnClickListener(v->{
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });
        viewpager.getViewPager().addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 2) {
                    go.setVisibility(View.VISIBLE);
                }else {
                    go.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewpager.setAdapter(new DynamicPagerAdapter() {
            @Override
            public View getView(ViewGroup container, int position) {
                ImageView imageView = new ImageView(GuideActivity.this);
                imageView.setImageResource(res[position]);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                return imageView;
            }

            @Override
            public int getCount() {
                return 3;
            }
        });
    }
}
