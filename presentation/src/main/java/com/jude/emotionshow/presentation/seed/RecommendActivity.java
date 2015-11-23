package com.jude.emotionshow.presentation.seed;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.BeamBaseActivity;
import com.jude.emotionshow.R;
import com.jude.swipbackhelper.SwipeBackHelper;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Mr.Jude on 2015/11/22.
 */
@RequiresPresenter(RecommendPresenter.class)
public class RecommendActivity extends BeamBaseActivity<RecommendPresenter> {

    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.tabs)
    TabLayout tabs;
    @Bind(R.id.viewpager)
    ViewPager viewpager;

    FragmentPagerAdapter pagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);
        SwipeBackHelper.getCurrentPage(this).setSwipeBackEnable(false);
        ButterKnife.bind(this);
        back.setOnClickListener(v -> finish());
        viewpager.setAdapter(pagerAdapter = new SeedFragmentListAdapter(getSupportFragmentManager()));
        tabs.setupWithViewPager(viewpager);
        viewpager.setCurrentItem(getIntent().getIntExtra("type",0));
        tabs.setTabTextColors(getResources().getColor(R.color.gray),getResources().getColor(R.color.orange));
        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                title.setText(tab.getText());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    class SeedFragmentListAdapter extends FragmentPagerAdapter {

        public SeedFragmentListAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment f =  new RecommendItemFragment();
            Bundle b = new Bundle();
            b.putInt("type",position);
            f.setArguments(b);
            return f;
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0:return "精选秀";
                case 1:return "好友秀";
                case 2:return "附近秀";
                case 3:return "最新秀";
                default:throw new RuntimeException("页数不存在");
            }
        }
    }
}
