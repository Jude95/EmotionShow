package com.jude.emotionshow.presentation.app;

import android.app.Application;

import com.jude.beam.Beam;
import com.jude.beam.expansion.BeamBaseActivity;
import com.jude.beam.expansion.overlay.ViewExpansionDelegate;
import com.jude.beam.expansion.overlay.ViewExpansionDelegateProvider;
import com.jude.emotionshow.BuildConfig;
import com.jude.emotionshow.domain.Dir;
import com.jude.utils.JFileManager;
import com.jude.utils.JUtils;

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
        Beam.init(this);
        Beam.setActivityLifeCycleDelegateProvider(ActivityDelegate::new);
        Beam.setViewExpansionDelegateProvider(new ViewExpansionDelegateProvider() {
            @Override
            public ViewExpansionDelegate createViewExpansionDelegate(BeamBaseActivity activity) {
                return new PaddingTopViewExpansion(activity);
            }
        });
    }
}
