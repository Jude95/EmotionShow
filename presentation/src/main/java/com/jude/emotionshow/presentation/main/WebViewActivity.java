package com.jude.emotionshow.presentation.main;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.BeamBaseActivity;
import com.jude.emotionshow.R;
import com.jude.emotionshow.data.server.HeaderInterceptors;
import com.jude.swipbackhelper.SwipeBackHelper;
import com.umeng.share.ShareManager;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zhuchenxi on 15/12/8.
 */
@RequiresPresenter(WebViewPresenter.class)
public class WebViewActivity extends BeamBaseActivity<WebViewPresenter> {
    @Bind(R.id.back_img)
    ImageView backImg;
    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.webview)
    WebView webview;

    String mTitle;
    String mShare;
    @Bind(R.id.share)
    ImageView share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        SwipeBackHelper.getCurrentPage(this).setSwipeBackEnable(false);
        ButterKnife.bind(this);
        back.setOnClickListener(v -> finish());
        mShare = getIntent().getStringExtra("share");
        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadUrl(getIntent().getStringExtra("url") + "?id=" + HeaderInterceptors.UID + "&token=" + HeaderInterceptors.TOKEN);
        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) { // 获取到Title
                mTitle = title;
                WebViewActivity.this.title.setText(mTitle);
                super.onReceivedTitle(view, title);
            }

            @Override
            public void onReceivedIcon(WebView view, Bitmap icon) { // 获取到图标
                super.onReceivedIcon(view, icon);
            }
        });
        share.setOnClickListener(v->{
            ShareManager.getInstance(this).share(this, " ", mTitle, mShare, "http://7xnrrg.com2.z0.glb.qiniucdn.com/logo72.png");
        });
        if (TextUtils.isEmpty(mShare)){
            share.setVisibility(View.GONE);
        }
    }
}
