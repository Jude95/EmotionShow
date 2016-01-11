package com.jude.emotionshow.presentation.shop;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.BeamBaseActivity;
import com.jude.emotionshow.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mike on 2015/12/28.
 */
@RequiresPresenter(PayDetailPresenter.class)
public class PayDetailActivity extends BeamBaseActivity<PayDetailPresenter> {
    @Bind(R.id.tabs)
    TabLayout tabs;
    @Bind(R.id.viewpager)
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_detail);
        ButterKnife.bind(this);

        setupViewPager();
    }

    private void setupViewPager() {
        $(R.id.back).setOnClickListener(v -> finish());
        viewPager.setAdapter(new PayFragmentListAdapter(getSupportFragmentManager()));
        tabs.setupWithViewPager(viewPager);
        tabs.setTabTextColors(getResources().getColor(R.color.gray),getResources().getColor(R.color.orange));
        viewPager.setCurrentItem(0);
    }

    class PayFragmentListAdapter extends FragmentPagerAdapter {

        public PayFragmentListAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment f =  new PayItemDetailFragment();
            Bundle b = new Bundle();
            b.putInt("type",position);
            f.setArguments(b);
            return f;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0:return "收入";
                case 1:return "支出";
//                case 2:return "";
                default:throw new RuntimeException("页数不存在");
            }
        }
    }
}
