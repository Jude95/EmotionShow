package com.jude.emotionshow.presentation.main;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.BeamBaseActivity;
import com.jude.emotionshow.R;
import com.jude.emotionshow.presentation.seed.SeedSearchFragment;
import com.jude.emotionshow.presentation.user.UserSearchFragment;

import java.util.ArrayList;

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

    SeedFragmentListAdapter pagerAdapter;
    @Bind(R.id.cancel)
    TextView cancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        cancel.setOnClickListener(v->finish());
        viewpager.setAdapter(pagerAdapter = new SeedFragmentListAdapter(getSupportFragmentManager()));
        tabs.setupWithViewPager(viewpager);
        viewpager.setCurrentItem(getIntent().getIntExtra("type", 0));
        tabs.setTabTextColors(getResources().getColor(R.color.gray), getResources().getColor(R.color.orange));
        query.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ((SearchPresenter.OnSearchAsked) pagerAdapter.getFragmentByIndex(0)).search(s.toString());
                ((SearchPresenter.OnSearchAsked) pagerAdapter.getFragmentByIndex(1)).search(s.toString());
            }
        });
    }

    class SeedFragmentListAdapter extends FragmentPagerAdapter {
        ArrayList<Fragment> list = new ArrayList<>();

        public SeedFragmentListAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment f = null;
            if (position == 0)
                f = new UserSearchFragment();
            if (position == 1)
                f = new SeedSearchFragment();
            list.add(position, f);
            return f;
        }

        public Fragment getFragmentByIndex(int index) {
            return list.get(index);
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "用户";
                case 1:
                    return "印记";
                default:
                    throw new RuntimeException("页数不存在");
            }
        }
    }
}
