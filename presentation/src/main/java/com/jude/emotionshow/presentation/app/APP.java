package com.jude.emotionshow.presentation.app;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.jude.beam.Beam;
import com.jude.beam.expansion.BeamBaseActivity;
import com.jude.beam.expansion.overlay.ViewExpansionDelegate;
import com.jude.beam.expansion.overlay.ViewExpansionDelegateProvider;
import com.jude.emotionshow.BuildConfig;
import com.jude.emotionshow.data.model.RongYunModel;
import com.jude.emotionshow.data.model.UserModel;
import com.jude.emotionshow.domain.Dir;
import com.jude.emotionshow.domain.entities.Account;
import com.jude.emotionshow.presentation.main.LaunchActivity;
import com.jude.emotionshow.presentation.user.LoginActivity;
import com.jude.emotionshow.presentation.user.UserPreviewActivity;
import com.jude.utils.JActivityManager;
import com.jude.utils.JFileManager;
import com.jude.utils.JUtils;

import cn.jpush.android.api.JPushInterface;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;
import rx.functions.Action1;

/**
 * Created by Mr.Jude on 2015/11/18.
 */
public class APP extends Application {
    private static APP instance;
    public static APP getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("Emotion","init");
        /* OnCreate 会被多个进程重入，这段保护代码，确保只有您需要使用 RongIM 的进程和 Push 进程执行了 init。
         * xxx.xxx.xxx 是您的主进程或者使用了 RongIM 的其他进程 */
        if("com.jude.emotionshow".equals(getCurProcessName(getApplicationContext())) ||
                "io.rong.push".equals(getCurProcessName(getApplicationContext()))) {

            /* IMKit SDK调用第一步 初始化 */
            RongIM.init(this);
            JPushInterface.setDebugMode(BuildConfig.DEBUG); 	// 设置开启日志,发布时请关闭日志
            JPushInterface.init(this);     		// 初始化 JPush

            /* 必须在使用 RongIM 的进程注册回调、注册自定义消息等 */
            if ("com.jude.emotionshow".equals(getCurProcessName(getApplicationContext()))) {
                instance = this;
                JFileManager.getInstance().init(this, Dir.values());
                JUtils.initialize(this);
                JUtils.setDebug(BuildConfig.DEBUG, "Emotion");
                JUtils.Log("real init");
//        ShareSDK.initSDK(this);
                Beam.init(this);
                Beam.setActivityLifeCycleDelegateProvider(ActivityDelegate::new);
                Beam.setViewExpansionDelegateProvider(new ViewExpansionDelegateProvider() {
                    @Override
                    public ViewExpansionDelegate createViewExpansionDelegate(BeamBaseActivity activity) {
                        return new PaddingTopViewExpansion(activity);
                    }
                });
                UserModel.getInstance().getAccountUpdate().subscribe(new Action1<Account>() {
                    @Override
                    public void call(Account account) {
                        if (account == null
                                && JActivityManager.getInstance().currentActivity() != null
                                && !(JActivityManager.getInstance().currentActivity() instanceof LoginActivity)
                                && !(JActivityManager.getInstance().currentActivity() instanceof LaunchActivity)) {
                            JActivityManager.getInstance().currentActivity().startActivity(new Intent(JActivityManager.getInstance().currentActivity(), LoginActivity.class));
                            //JActivityManager.getInstance().closeAllActivity();
                        }
                    }
                });
                RongYunModel.getInstance().setRongYunDelegate(new RongYunModel.RongYunDelegate() {
                    @Override
                    public void onPersonClick(Context context, Conversation.ConversationType conversationType, UserInfo userInfo) {
                        Intent i = new Intent(context, UserPreviewActivity.class);
                        i.putExtra("id", Integer.parseInt(userInfo.getUserId()));
                        context.startActivity(i);
                    }
                });
            }
        }
    }

    /* 一个获得当前进程的名字的方法 */
    public static String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }

}
