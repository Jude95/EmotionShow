package com.jude.emotionshow.presentation.app;

import android.app.Activity;
import android.os.Bundle;

import com.jude.beam.bijection.ActivityLifeCycleDelegate;
import com.jude.swipbackhelper.SwipeBackHelper;
import com.jude.utils.JActivityManager;
import com.umeng.analytics.MobclickAgent;
/**
 * Created by Mr.Jude on 2015/9/9.
 * Activity的生命周期代理，会注入到每个Activity当中。相当于在每个Activity对应毁掉中中执行下面的回调。
 */
public class ActivityDelegate extends ActivityLifeCycleDelegate {

    public ActivityDelegate(Activity act) {
        super(act);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //滑动关闭
        SwipeBackHelper.onCreate(getActivity());
        //Activity管理
        JActivityManager.getInstance().pushActivity(getActivity());
    }

    @Override
    protected void onResume() {
        super.onResume();
        //友盟统计
        MobclickAgent.onResume(getActivity());
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(getActivity());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SwipeBackHelper.onDestroy(getActivity());
        JActivityManager.getInstance().popActivity(getActivity());
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        SwipeBackHelper.onPostCreate(getActivity());
    }
}
