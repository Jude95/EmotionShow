package com.jude.emotionshow.presentation.shop;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jude.beam.bijection.BeamFragment;
import com.jude.beam.bijection.RequiresPresenter;
import com.jude.emotionshow.R;
import com.jude.emotionshow.data.model.UserModel;
import com.jude.emotionshow.domain.entities.Banner;
import com.jude.emotionshow.presentation.main.WebViewActivity;
import com.jude.emotionshow.presentation.user.LoginActivity;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.jude.utils.JUtils;
import com.squareup.picasso.Picasso;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.SingleTagFlowLayout;
import com.zhy.view.flowlayout.TagAdapter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mike on 2015/12/23.
 */
@RequiresPresenter(ShopMainPresenter.class)
public class ShopMainFragment extends BeamFragment<ShopMainPresenter> {
    public static ShopMainFragment instance;

    private String[] tags = new String[]{"全部", "玫瑰花", "巧克力", "项链", "爱心"};

    @Bind(R.id.banner)
    RollPagerView banner;
    @Bind(R.id.viewpager)
    ViewPager viewPager;
    @Bind(R.id.tag_flow_layout)
    SingleTagFlowLayout tagFlowLayout;
    @Bind(R.id.iv_menu)
    ImageView menu;
    @Bind(R.id.tv_all)
    TextView menuAll;
    @Bind(R.id.tv_exchange)
    TextView menuExchange;
    @Bind(R.id.ll_price)
    LinearLayout menuPriceContainer;
    @Bind(R.id.tv_price)
    TextView menuPrice;
    @Bind(R.id.iv_price)
    ImageView ivMenuPrice;
    @Bind(R.id.iv_exchange)
    ImageView ivExchange;
//    ListPopupWindow popupWindow;

    ShopPagerAdapter pagerAdapter;

    public static ShopMainFragment getInstance() {
        if (instance == null) instance = new ShopMainFragment();
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop_main, container, false);
        ButterKnife.bind(this, view);

        setupView();
        setupTagLayout(inflater);
        setupViewPager();

        return view;
    }

    private void setupView() {
//        popupWindow = new ListPopupWindow(getActivity());
//        popupWindow.setAdapter(new PriceAdapter(getActivity()));
//        popupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                popupWindow.dismiss();
//            }
//        });

        menu.setOnClickListener(v -> {
            if (tagFlowLayout.getVisibility() == View.VISIBLE) {
                tagFlowLayout.setVisibility(View.GONE);
            } else {
                tagFlowLayout.setVisibility(View.VISIBLE);
            }
        });

        menuAll.setOnClickListener(v -> {
            viewPager.setCurrentItem(0, true);
        });

        menuExchange.setOnClickListener(v -> {
            if (viewPager.getCurrentItem() == 1) {
                pagerAdapter.currentFragment.sortByAscOrDesc();
                ivExchange.setImageResource(R.drawable.ic_menu_up);
                return;
            }
            viewPager.setCurrentItem(1, true);
        });

        menuPriceContainer.setOnClickListener(v -> {
            if (viewPager.getCurrentItem() == 2) {
                pagerAdapter.currentFragment.sortByAscOrDesc();
                ivMenuPrice.setImageResource(R.drawable.ic_menu_up);
                return;
            }
            viewPager.setCurrentItem(2, true);
//            popupWindow.setAnchorView(v);
//            popupWindow.show();
        });
    }

    private int lastTagPosition = 0;
    private TagAdapter tagAdapter;

    private void setupTagLayout(LayoutInflater inflater) {
        tagFlowLayout.setAdapter(tagAdapter = new TagAdapter<String>(tags) {

            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) inflater.inflate(R.layout.tag_shop_main, tagFlowLayout, false);
                tv.setText(s);
                return tv;
            }
        });
        tagAdapter.setSelectedList(0);

        tagFlowLayout.setOnTagClickListener(new SingleTagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                if (lastTagPosition == position) {
                    return false;
                }
                lastTagPosition = position;
                return true;
            }
        });

        tagFlowLayout.setOnSelectListener(new SingleTagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(int selectPos) {
                JUtils.Toast(selectPos + " " + tags[selectPos]);
            }
        });
    }

    private void setupViewPager() {
        viewPager.setAdapter(pagerAdapter = new ShopPagerAdapter(getChildFragmentManager()));
        viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    setMenuAll(true);
                    setMenuExchange(false);
                    setMenuPrice(false);
                } else if (position == 1) {
                    setMenuAll(false);
                    setMenuExchange(true);
                    setMenuPrice(false);
                } else {
                    setMenuAll(false);
                    setMenuExchange(false);
                    setMenuPrice(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setMenuAll(boolean focus) {
        menuAll.setTextColor(getResources().getColor(focus ? R.color.orange : R.color.dark_gray));
    }

    private void setMenuExchange(boolean focus) {
        menuExchange.setTextColor(getResources().getColor(focus ? R.color.orange : R.color.dark_gray));
        ivExchange.setImageResource(focus ? R.drawable.ic_menu_down_red : R.drawable.ic_menu_down_grey);
    }

    private void setMenuPrice(boolean focus) {
        menuPrice.setTextColor(getResources().getColor(focus ? R.color.orange : R.color.dark_gray));
        ivMenuPrice.setImageResource(focus ? R.drawable.ic_menu_down_red : R.drawable.ic_menu_down_grey);
    }

    public void setBanner(List<Banner> banners) {
        if (banners == null || banners.size() == 0) {
            return;
        }
        banner.setAdapter(new StaticPagerAdapter() {
            @Override
            public View getView(ViewGroup container, int position) {
                ImageView imageView = new ImageView(getContext());
                imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setOnClickListener(v -> {
                    if (!UserModel.getInstance().isLogin()) {
                        startActivity(new Intent(getActivity(), LoginActivity.class));
                        JUtils.Log("请先登录");
                        return;
                    }
                    if (banners.get(position).getType() == 1) {
                        Intent i = new Intent(getActivity(), WebViewActivity.class);
                        i.putExtra("url", banners.get(position).getAction());
                        i.putExtra("share", banners.get(position).getShare());
                        startActivity(i);
                    }
                });
                Picasso.with(container.getContext())
                        .load(banners.get(position).getImg())
                        .into(imageView);
                return imageView;
            }

            @Override
            public int getCount() {
                return banners.size();
            }
        });
    }

    class ShopPagerAdapter extends FragmentPagerAdapter {
        GoodsListFragment currentFragment;

        public ShopPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            currentFragment = (GoodsListFragment) object;
            super.setPrimaryItem(container, position, object);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment f = new GoodsListFragment();
            Bundle b = new Bundle();
            b.putInt("type", position);
            f.setArguments(b);
            return f;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

    class PriceAdapter extends BaseAdapter {
        private Context mContext;

        public PriceAdapter(Context context) {
            this.mContext = context;
        }

        @Override
        public int getCount() {
            return d.length;
        }

        @Override
        public Object getItem(int position) {
            return d[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView view = (TextView) LayoutInflater.from(mContext).inflate(R.layout.item_spinner, null);
            view.setText(d[position]);
            return view;
        }
    }

    String[] d = new String[]{"0-100", "100-200", "500", "600"};
}
