package com.jude.emotionshow.presentation.main;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.EditText;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.BeamBaseActivity;
import com.jude.emotionshow.R;
import com.jude.emotionshow.presentation.seed.SeedSearchFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zhuchenxi on 15/11/23.
 */
@RequiresPresenter(SearchPresenter.class)
public class SearchActivity extends BeamBaseActivity<SearchPresenter> {

    @Bind(R.id.query)
    EditText query;
    @Bind(R.id.tabs)
    TabLayout tabs;
    @Bind(R.id.viewpager)
    ViewPager viewpager;

    FragmentPagerAdapter pagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        viewpager.setAdapter(pagerAdapter = new SeedFragmentListAdapter(getSupportFragmentManager()));
        tabs.setupWithViewPager(viewpager);
        viewpager.setCurrentItem(getIntent().getIntExtra("type", 0));
        tabs.setTabTextColors(getResources().getColor(R.color.gray), getResources().getColor(R.color.orange));
    }

    class SeedFragmentListAdapter extends FragmentPagerAdapter {

        public SeedFragmentListAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0)return null;
            if (position == 1)return new SeedSearchFragment();
            return null;
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0:return "用户";
                case 1:return "印记";
                default:throw new RuntimeException("页数不存在");
            }
        }
    }
}
