package com.jude.emotionshow.presentation.main;

import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.BeamBaseActivity;
import com.jude.emotionshow.R;
import com.jude.emotionshow.presentation.seed.SeedMainFragment;
import com.jude.emotionshow.presentation.user.MineFragment;
import com.jude.swipbackhelper.SwipeBackHelper;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Mr.Jude on 2015/11/18.
 */
@RequiresPresenter(MainPresenter.class)
public class MainActivity extends BeamBaseActivity<MainPresenter> {

    @Bind(R.id.container)
    FrameLayout container;
    @Bind(R.id.find)
    LinearLayout find;
    @Bind(R.id.mine)
    LinearLayout mine;
    @Bind(R.id.kiss)
    ImageView kiss;
    @Bind(R.id.img_find)
    ImageView imgFind;
    @Bind(R.id.tv_find)
    TextView tvFind;
    @Bind(R.id.img_mine)
    ImageView imgMine;
    @Bind(R.id.tv_mine)
    TextView tvMine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SwipeBackHelper.getCurrentPage(this).setSwipeBackEnable(false);
        ButterKnife.bind(this);
        find.setOnClickListener(v -> {
            focusFind(true);
            focusMine(false);
            showFind();
        });
        mine.setOnClickListener(v -> {
            focusFind(false);
            focusMine(true);
            showMine();
        });
        kiss.setOnClickListener(v-> getPresenter().createSeed());
        showFind();
    }

    void showFind(){
        getSupportFragmentManager().beginTransaction().replace(R.id.container, SeedMainFragment.getInstance()).commit();
    }

    void showMine(){
        getSupportFragmentManager().beginTransaction().replace(R.id.container, MineFragment.getInstance()).commit();
    }

    void focusFind(boolean focus) {
        imgFind.setImageResource(focus ? R.drawable.find_red : R.drawable.find_gray);
        tvFind.setTextColor(getResources().getColor(focus ? R.color.orange_deep : R.color.gray));
    }

    void focusMine(boolean focus) {
        imgMine.setImageResource(focus ? R.drawable.mine_red : R.drawable.mine_gray);
        tvMine.setTextColor(getResources().getColor(focus ? R.color.orange_deep : R.color.gray));
    }
}
