package com.jude.emotionshow.data.server;


import android.text.TextUtils;

import retrofit.RequestInterceptor;

/**
 * Created by zhuchenxi on 15/10/11.
 * 添加Header的过滤器，服务器通过header中的信息进行用户识别
 */
public class HeaderInterceptors implements RequestInterceptor {
    public static String TOKEN = "";
    public static String UID = "";

    @Override
    public void intercept(RequestFacade request) {
        if (TextUtils.isEmpty(TOKEN)||TextUtils.isEmpty(UID))return;
        request.addHeader("uid", UID);
        request.addHeader("token", TOKEN);
    }
}
