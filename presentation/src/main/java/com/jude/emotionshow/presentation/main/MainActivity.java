package com.jude.emotionshow.presentation.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.BeamBaseActivity;
import com.jude.emotionshow.R;
import com.jude.emotionshow.data.model.UserModel;
import com.jude.emotionshow.presentation.seed.SeedMainFragment;
import com.jude.emotionshow.presentation.shop.ShopMainFragment;
import com.jude.emotionshow.presentation.user.LoginActivity;
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
    @Bind(R.id.ll_shop)
    LinearLayout shop;
    @Bind(R.id.kiss)
    LinearLayout kiss;
    @Bind(R.id.img_find)
    ImageView imgFind;
    @Bind(R.id.tv_find)
    TextView tvFind;
    @Bind(R.id.img_mine)
    ImageView imgMine;
    @Bind(R.id.tv_mine)
    TextView tvMine;
    @Bind(R.id.iv_shop)
    ImageView ivShop;
    @Bind(R.id.tv_shop)
    TextView tvShop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SwipeBackHelper.getCurrentPage(this).setSwipeBackEnable(false);
        ButterKnife.bind(this);
        find.setOnClickListener(v -> {
            focusFind(true);
            focusMine(false);
            focusShop(false);
            showFind();
        });
        mine.setOnClickListener(v -> {
            if (!UserModel.getInstance().isLogin()){
                startActivity(new Intent(this, LoginActivity.class));
                return;
            }
            focusFind(false);
            focusMine(true);
            focusShop(false);
            showMine();
        });
        shop.setOnClickListener(v -> {
            focusFind(false);
            focusMine(false);
            focusShop(true);
            showShop();
        });
        kiss.setOnClickListener(v-> getPresenter().createSeed());
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.container, MineFragment.getInstance());
        transaction.add(R.id.container, SeedMainFragment.getInstance());
        transaction.add(R.id.container,ShopMainFragment.getInstance());
        transaction.commit();
        showFind();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        if (!UserModel.getInstance().isLogin()){
//            focusFind(true);
//            focusMine(false);
//            new Handler().post(new Runnable() {
//                @Override
//                public void run() {
//                    showFind();
//                }
//            });
//        }
    }

    //显示发现
    void showFind(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.show(SeedMainFragment.getInstance());
        transaction.hide(MineFragment.getInstance());
        transaction.hide(ShopMainFragment.getInstance());
        transaction.commit();
    }

    //显示我
    void showMine(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(SeedMainFragment.getInstance());
        transaction.show(MineFragment.getInstance());
        transaction.hide(ShopMainFragment.getInstance());
        transaction.commit();
    }

    private void showShop(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.show(ShopMainFragment.getInstance());
        transaction.hide(MineFragment.getInstance());
        transaction.hide(SeedMainFragment.getInstance());
        transaction.commit();
    }

    void focusFind(boolean focus) {
        imgFind.setImageResource(focus ? R.drawable.find_red : R.drawable.find_gray);
        tvFind.setTextColor(getResources().getColor(focus ? R.color.orange_deep : R.color.gray));
    }

    void focusMine(boolean focus) {
        imgMine.setImageResource(focus ? R.drawable.mine_red : R.drawable.mine_gray);
        tvMine.setTextColor(getResources().getColor(focus ? R.color.orange_deep : R.color.gray));
    }

    private void focusShop(boolean focus){
        ivShop.setImageResource(focus ? R.drawable.ic_shop_red : R.drawable.ic_shop_gray);
        tvShop.setTextColor(getResources().getColor(focus ? R.color.orange_deep : R.color.gray));
    }
}
