package com.jude.emotionshow.presentation.main;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.BeamBaseActivity;
import com.jude.emotionshow.R;
import com.jude.emotionshow.presentation.widget.HackyViewPager;
import com.jude.swipbackhelper.SwipeBackHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import uk.co.senab.photoview.PhotoView;


/**
* Created by Mr.Jude on 2015/2/22.
*/
@RequiresPresenter(ImageViewPresenter.class)
public class ImageViewActivity extends BeamBaseActivity<ImageViewPresenter> implements View.OnClickListener{
    public static String KEY_URIS = "uris";
    public static String KEY_INDEX = "index";

    private HackyViewPager mViewPager;
    private TextView mTv_lock;
    private ImageView mTv_return;
    private TextView mTv_title;
    private ImagePagerAdapter mAdapter;

    Handler handler = new Handler();
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imageview);
        SwipeBackHelper.getCurrentPage(this).setSwipeBackEnable(false);
        mTv_lock = (TextView) findViewById(R.id.lock);
        mTv_return = (ImageView) findViewById(R.id.home);
        mTv_title = (TextView) findViewById(R.id.title);
        mTv_title.setOnClickListener(this);
        mTv_lock.setOnClickListener(this);
        mTv_return.setOnClickListener(this);

        mViewPager = (HackyViewPager) findViewById(R.id.viewpager);
        mAdapter = new ImagePagerAdapter();
        mViewPager.setAdapter(mAdapter);
        ArrayList<Uri> urls = getIntent().getParcelableArrayListExtra(KEY_URIS);
        int index = getIntent().getIntExtra(KEY_INDEX,0);
        if (urls==null||urls.size()<=1){
            mTv_lock.setVisibility(View.GONE);
            mViewPager.toggleLock();
        }
        mAdapter.setUrls(urls);
        mViewPager.setCurrentItem(index);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.title||v.getId() == R.id.home){
            finish();
        }else if (v.getId() == R.id.lock){
            mViewPager.toggleLock();
            mTv_lock.setText((mViewPager.isLocked()) ? "解锁":"锁定");
        }
    }

    class ImagePagerAdapter extends PagerAdapter {
        private ArrayList<Uri> urls;

        public void setUrls(ArrayList<Uri> urls){
            this.urls = urls;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return urls==null?0:urls.size();
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {
            View view = LayoutInflater.from(ImageViewActivity.this).inflate(R.layout.item_imagepage, container, false);
            final PhotoView photoView = (PhotoView) view.findViewById(R.id.photoview);
            final View wheel = view.findViewById(R.id.wheel);
            Picasso.with(container.getContext())
                    .load(urls.get(position).toString())
                    .into(photoView);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

    }

}
