package com.jude.emotionshow.presentation.main;

import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.BeamBaseActivity;
import com.jude.emotionshow.R;
import com.jude.emotionshow.data.server.HeaderInterceptors;
import com.jude.swipbackhelper.SwipeBackHelper;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        SwipeBackHelper.getCurrentPage(this).setSwipeBackEnable(false);
        ButterKnife.bind(this);
        back.setOnClickListener(v->finish());
        title.setText(getIntent().getStringExtra("title"));
        webview.loadUrl(getIntent().getStringExtra("url")+"?id="+ HeaderInterceptors.UID+"&token="+HeaderInterceptors.TOKEN);
    }
}
