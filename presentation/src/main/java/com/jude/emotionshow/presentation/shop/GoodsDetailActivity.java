package com.jude.emotionshow.presentation.shop;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.data.BeamDataActivity;
import com.jude.emotionshow.R;
import com.jude.emotionshow.data.model.UserModel;
import com.jude.emotionshow.domain.entities.GoodsDetail;
import com.jude.emotionshow.domain.entities.Order;
import com.jude.emotionshow.presentation.user.LoginActivity;
import com.jude.emotionshow.presentation.widget.AddSubView;
import com.jude.emotionshow.presentation.widget.GoodsDetailLayout;
import com.jude.tagview.TAGView;
import com.jude.utils.JUtils;
import com.squareup.picasso.Picasso;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.SingleTagFlowLayout;
import com.zhy.view.flowlayout.TagAdapter;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mike on 2015/12/23.
 */
@RequiresPresenter(GoodsDetailPresenter.class)
public class GoodsDetailActivity extends BeamDataActivity<GoodsDetailPresenter, GoodsDetail> implements GoodsDetailLayout.OnPullListener, GoodsDetailLayout.OnPageChangedListener {
    @Bind(R.id.goods_detail_container)
    GoodsDetailLayout container;
    @Bind(R.id.header)
    ScrollView contentScrollView;
    @Bind(R.id.footer)
    WebView webView;
    @Bind(R.id.ll_content)
    LinearLayout content;
    @Bind(R.id.tg_ok)
    TAGView ok;
    @Bind(R.id.iv_goods)
    ImageView img;
    @Bind(R.id.tv_money)
    TextView money;
    //    @Bind(R.id.flow_category)
//    SingleTagFlowLayout category;
//    @Bind(R.id.flow_spec)
//    SingleTagFlowLayout spec;
    @Bind(R.id.tv_goods_name)
    TextView name;
    @Bind(R.id.add_sub)
    AddSubView addSubView;
    @Bind(R.id.ll_container)
    LinearLayout flowContainer;
    @Bind(R.id.fab_back)
    FloatingActionButton back;
    @Bind(R.id.tv_err)
    TextView err;

    Order order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);
        ButterKnife.bind(this);

        setupView();
        setWebView();

        order = new Order();
    }

    //    TagAdapter categoryAdapter;
//    TagAdapter specAdapter;
    SingleTagFlowLayout[] flowLayouts;
    Map<String, String> stringMap;

    String intro;

    @Override
    public void setData(GoodsDetail data) {
        intro = data.getIntro();
        Picasso.with(this).load(data.getPic()).into(img);
        name.setText(data.getName());
        money.setText(data.getPrice());
//        category.setAdapter(categoryAdapter = new TagAdapter<String>(data.getInfo().get(0).getValue()) {
//            @Override
//            public View getView(FlowLayout parent, int position, String s) {
//                TextView tv = (TextView) LayoutInflater.from(GoodsDetailActivity.this).inflate(R.layout.tag_shop_main, category, false);
//                tv.setText(s);
//                return tv;
//            }
//        });
//        categoryAdapter.setSelectedList(0);
//        spec.setAdapter(specAdapter = new TagAdapter<String>(data.getInfo().get(1).getValue()) {
//            @Override
//            public View getView(FlowLayout parent, int position, String s) {
//                TextView tv = (TextView) LayoutInflater.from(GoodsDetailActivity.this).inflate(R.layout.tag_shop_main, spec, false);
//                tv.setText(s);
//                return tv;
//            }
//        });
//        specAdapter.setSelectedList(0);
        order.setPic(data.getPic());
        order.setGid(data.getId());
        order.setGoodsName(data.getName());
        order.setPrice(data.getPrice());
        stringMap = new HashMap<>();
        flowLayouts = new SingleTagFlowLayout[data.getInfo().size()];
        int i = 0;
        for (GoodsDetail.InfoItem infoItem : data.getInfo()) {
            stringMap.put(infoItem.getKey(), infoItem.getValue().get(0));
            TextView tv = new TextView(GoodsDetailActivity.this);
            tv.setText(infoItem.getKey() + ":");
            flowContainer.addView(tv);
            flowLayouts[i] = new SingleTagFlowLayout(GoodsDetailActivity.this);
            flowLayouts[i].setAdapter(new TagAdapter<String>(infoItem.getValue()) {
                @Override
                public View getView(FlowLayout parent, int position, String s) {
                    TextView tv = (TextView) LayoutInflater.from(GoodsDetailActivity.this).inflate(R.layout.tag_shop_main, flowLayouts[i], false);
                    tv.setText(s);
                    return tv;
                }
            });
            flowLayouts[i].getAdapter().setSelectedList(0);
            flowLayouts[i].setOnSelectListener(new SingleTagFlowLayout.OnSelectListener() {
                @Override
                public void onSelected(int selectPosSet) {
                    stringMap.put(infoItem.getKey(), infoItem.getValue().get(selectPosSet));
                }
            });
            flowContainer.addView(flowLayouts[i]);
        }
    }

    private void setupView() {
        back.setOnClickListener(v1 -> onBackPressed());
        ok.setOnClickListener(v -> {
            if (!UserModel.getInstance().isLogin()) {
                startActivity(new Intent(this, LoginActivity.class));
                return;
            }
            Intent intent = new Intent(this, OrderConfirmActivity.class);
            order.setNum(addSubView.getNum());
            StringBuilder sb = new StringBuilder();
            for (Map.Entry entry : stringMap.entrySet()) {
                String val = (String) entry.getValue();
                sb.append(val).append(",");
            }
            //界面描述
            order.setDes(sb.toString());
            //传入后台
            order.setInfo(new Gson().toJson(stringMap));
            intent.putExtra("order", order);
            startActivity(intent);
            finish();
        });
        container.setOnContentChangeListener(this);
        container.setOnPullListener(this);
    }

    private void setWebView() {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (!url.startsWith("http")) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                    return true;
                }
                view.loadUrl(url);
                return true;
            }
        });
    }

    private boolean hasLoaded;

    @Override
    public boolean headerFootReached(MotionEvent event) {
        if (contentScrollView.getScrollY() + contentScrollView.getHeight() >= content.getHeight()) {
            if (!hasLoaded) {
                if (TextUtils.isEmpty(intro)) {
                    err.setVisibility(View.VISIBLE);
                    return true;
                }
                hasLoaded = true;
                webView.loadUrl(intro);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean footerHeadReached(MotionEvent event) {
        //下拉返回点
        if (webView.getScrollY() == 0) {
            err.setVisibility(View.GONE);
            return true;
        }
        return false;
    }

    private boolean isWebViewPage;
    @Override
    public void onPageChanged(int stub) {
        if (stub == GoodsDetailLayout.SCREEN_FOOTER) {
            isWebViewPage = true;
            ok.animate().translationY(JUtils.dip2px(48)).setDuration(500);
        } else {
            isWebViewPage = false;
            ok.animate().translationY(0).setDuration(500);
        }
    }

    @Override
    public void onBackPressed() {
        if (isWebViewPage){
            container.backToContent();
            return;
        }
        super.onBackPressed();
    }
}
