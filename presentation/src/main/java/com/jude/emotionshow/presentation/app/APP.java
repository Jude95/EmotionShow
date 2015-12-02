package com.jude.emotionshow.presentation.app;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.view.View;

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

import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
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
                        && JActivityManager.getInstance().currentActivity() != null
                        && !(JActivityManager.getInstance().currentActivity() instanceof LoginActivity)
                        && !(JActivityManager.getInstance().currentActivity() instanceof LaunchActivity)) {
                    JActivityManager.getInstance().currentActivity().startActivity(new Intent(JActivityManager.getInstance().currentActivity(), LoginActivity.class));
                    //JActivityManager.getInstance().closeAllActivity();
                }
            }
        });
        RongYunModel.getInstance().getRongIMInstance().setConversationBehaviorListener(new RongIM.ConversationBehaviorListener() {
            @Override
            public boolean onUserPortraitClick(Context context, Conversation.ConversationType conversationType, UserInfo userInfo) {
                Intent i = new Intent(context,UserPreviewActivity.class);
                i.putExtra("id", Integer.parseInt(userInfo.getUserId()));
                context.startActivity(i);
                return true;
            }

            @Override
            public boolean onUserPortraitLongClick(Context context, Conversation.ConversationType conversationType, UserInfo userInfo) {
                return false;
            }

            @Override
            public boolean onMessageClick(Context context, View view, Message message) {
                return false;
            }

            @Override
            public boolean onMessageLinkClick(Context context, String s) {
                return false;
            }

            @Override
            public boolean onMessageLongClick(Context context, View view, Message message) {
                return false;
            }
        });
    }
}
