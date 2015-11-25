package com.jude.emotionshow.presentation.app;

import android.app.Application;
import android.content.Intent;

import com.jude.beam.Beam;
import com.jude.beam.expansion.BeamBaseActivity;
import com.jude.beam.expansion.overlay.ViewExpansionDelegate;
import com.jude.beam.expansion.overlay.ViewExpansionDelegateProvider;
import com.jude.emotionshow.BuildConfig;
import com.jude.emotionshow.data.model.UserModel;
import com.jude.emotionshow.domain.Dir;
import com.jude.emotionshow.domain.entities.Account;
import com.jude.emotionshow.presentation.main.LaunchActivity;
import com.jude.emotionshow.presentation.user.LoginActivity;
import com.jude.utils.JActivityManager;
import com.jude.utils.JFileManager;
import com.jude.utils.JUtils;

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
        instance = this;
        JFileManager.getInstance().init(this, Dir.values());
        JUtils.initialize(this);
        JUtils.setDebug(BuildConfig.DEBUG, "Emotion");
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
                        && JActivityManager.getInstance().currentActivity()!=null
                        &&!(JActivityManager.getInstance().currentActivity() instanceof LoginActivity)
                        &&!(JActivityManager.getInstance().currentActivity() instanceof LaunchActivity)){
                    JActivityManager.getInstance().currentActivity().startActivity(new Intent(JActivityManager.getInstance().currentActivity(), LoginActivity.class));
                    JActivityManager.getInstance().closeAllActivity();
                }
            }
        });

    }
}
