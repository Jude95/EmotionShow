package com.umeng.share;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeConfig;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners;
import com.umeng.socialize.media.QQShareContent;
import com.umeng.socialize.media.QZoneShareContent;
import com.umeng.socialize.media.SinaShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.sso.UMSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;
import com.umeng.socialize.weixin.media.WeiXinShareContent;

/**
 * Created by heqiang on 2015/10/21.
 */
public class ShareManager {
    private static ShareManager instance = null;
    private static final UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share");

    public static ShareManager getInstance(Context ctx) {
        if (instance == null){
            instance = new ShareManager();
            instance.configPlatforms(ctx);
            com.umeng.socialize.utils.Log.LOG = true;
        }
        return instance;
    }

    /**
     * @param context
     * @param content   分享内容
     * @param title     标题
     * @param targetUrl 点击跳转url
     * @param imageUrl  分享图片、小图片url
     */
    public void share(final Context context, String content, String title, String targetUrl, String imageUrl) {
        // 配置需要分享的相关平台
        // 设置分享的内容
        setShareContent(context, content, targetUrl, title, imageUrl);
        mController.getConfig().setPlatforms(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.SINA);
        mController.getConfig().removePlatform(SHARE_MEDIA.RENREN,SHARE_MEDIA.DOUBAN);
        mController.registerListener(new SocializeListeners.SnsPostListener() {
            @Override
            public void onStart() {
                Log.i("Share","分享开始");
            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int stCode, SocializeEntity socializeEntity) {
                if (stCode == 200) {
                    Toast.makeText(context, "分享成功", Toast.LENGTH_SHORT)
                            .show();
                } else {
                    Log.i("Share","分享失败"+stCode);
                }
            }
        });
        mController.openShare((Activity) context, false);
    }


    public void login(Context var1, SHARE_MEDIA var2, SocializeListeners. UMAuthListener var3){
        mController.doOauthVerify(var1, var2, var3);
    }

    public void getData(Context var1, SHARE_MEDIA var2, SocializeListeners.UMDataListener var3){
        mController.getPlatformInfo(var1, var2, var3);
    }

    /**
     * 配置分享平台参数</br>
     */
    private void configPlatforms(Context context) {
        SocializeConfig.getSocializeConfig().setSinaCallbackUrl("http://sns.whalecloud.com/sina2/callback");
        // 添加新浪SSO授权
        mController.getConfig().setSsoHandler(new SinaSsoHandler());

        // 添加QQ、QZone平台
        addQQQZonePlatform(context);

        // 添加微信、微信朋友圈平台
        addWXPlatform(context);
    }

    /**
     * @功能描述 : 添加QQ平台支持 QQ分享的内容， 包含四种类型， 即单纯的文字、图片、音乐、视频.
     * 参数说明 : title, summary,image url中必须至少设置一个, targetUrl必须设置,网页地址必须以"http://"开头 .
     * title : 要分享标题 summary : 要分享的文字概述 image url : 图片地址 [以上三个参数至少填写一个]
     * targetUrl : 用户点击该分享时跳转到的目标地址 [必填] ( 若不填写则默认设置为友盟主页 )
     */
    private void addQQQZonePlatform(Context context) {
//        String appId = "100424468";
        String appId = "1104846329";
//        String appKey = "c7394704798a158208a74ab60104f0ba";
        String appKey = "NDVX3ypE9uqOdRow";
        UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler((Activity) context, appId, appKey);
        qqSsoHandler.addToSocialSDK();

        // 添加QZone平台
        QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler((Activity) context, appId, appKey);
        qZoneSsoHandler.addToSocialSDK();
    }

    /**
     * @param context
     * @return
     * @功能描述 : 添加微信平台分享
     */
    private void addWXPlatform(Context context) {
        // 注意：在微信授权的时候，必须传递appSecret
        // wx967daebe835fbeac是你在微信开发平台注册应用的AppID, 这里需要替换成你注册的AppID
        String appId = "wx06119c633293ffa8";
        String appSecret = "d4624c36b6795d1d99dcf0547af5443d";
        // 添加微信平台
        UMWXHandler wxHandler = new UMWXHandler(context, appId, appSecret);
        wxHandler.addToSocialSDK();

        // 支持微信朋友圈
        UMWXHandler wxCircleHandler = new UMWXHandler(context, appId, appSecret);
        wxCircleHandler.setToCircle(true);
        wxCircleHandler.addToSocialSDK();
    }

    /**
     * 根据不同的平台设置不同的分享内容</br>
     */
    private void setShareContent(Context context, String content, String targetUrl, String title, String imageUrl) {

        // 配置SSO
        mController.getConfig().setSsoHandler(new SinaSsoHandler());

        QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler((Activity) context, "1104970950", "szdobxwEAVOosHCl");
        qZoneSsoHandler.addToSocialSDK();
        mController.setShareContent(content);

        UMImage urlImage = new UMImage(context, imageUrl);

        //微信分享内容
        WeiXinShareContent weixinContent = new WeiXinShareContent();
        weixinContent.setShareContent(content);
        weixinContent.setTitle(title);
        weixinContent.setTargetUrl(targetUrl);
        weixinContent.setShareMedia(urlImage);
        mController.setShareMedia(weixinContent);

        // 设置朋友圈分享的内容
        CircleShareContent circleMedia = new CircleShareContent();
        circleMedia.setShareContent(content);
        circleMedia.setTitle(title);
        circleMedia.setShareMedia(urlImage);
        circleMedia.setTargetUrl(targetUrl);
        mController.setShareMedia(circleMedia);

        // 设置QQ空间分享内容
        QZoneShareContent qzone = new QZoneShareContent();
        qzone.setShareContent(content);
        qzone.setTargetUrl(targetUrl);
        qzone.setTitle(title);
        qzone.setShareImage(urlImage);
        mController.setShareMedia(qzone);

        // QQ分享内容
        QQShareContent qqShareContent = new QQShareContent();
        qqShareContent.setShareContent(content);
        qqShareContent.setTitle(title);
        qqShareContent.setShareMedia(urlImage);
        qqShareContent.setTargetUrl(targetUrl);
        mController.setShareMedia(qqShareContent);

        //新浪分享内容
        SinaShareContent sinaContent = new SinaShareContent();
        sinaContent.setShareContent(content);
        sinaContent.setShareImage(urlImage);
        mController.setShareMedia(sinaContent);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        /**使用SSO授权必须添加如下代码 */
        UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(requestCode);
        if(ssoHandler != null){
            ssoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }
}
